package calculator.server;

import com.proto.calculator.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class CalculatorServerImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver){
        responseObserver.onNext(SumResponse.newBuilder().setResult(
                request.getFirstNumber() + request.getSecondNumber()
        ).build());
        responseObserver.onCompleted();
    }

    @Override
    public void primes(PrimeRequest request, StreamObserver<PrimeResponse> responseObserver){
        int number = request.getNumber();
        int divisor = 2;

        while (number > 1) {
            if (number % divisor == 0) {
                number = number / divisor;
                responseObserver.onNext(PrimeResponse.newBuilder().setPrimeFactor(divisor).build());
            } else {
                ++divisor;
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<AvgRequest> avg(StreamObserver<AvgResponse> responseObserver){
        return new StreamObserver<AvgRequest>() {
            int sum = 0;
            int count = 0;
            @Override
            public void onNext(AvgRequest value) {
                sum += value.getNumber();
                ++count;
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(AvgResponse.newBuilder().setResult(
                        (double) sum / count
                ).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<MaxRequest> max(StreamObserver<MaxResponse> responseObserver) {
        return new StreamObserver<MaxRequest>() {

            int currentMax = 0;

            @Override
            public void onNext(MaxRequest value) {
                int currentNumber = value.getNumber();

                if (currentNumber > currentMax) {
                    currentMax = currentNumber;
                    responseObserver.onNext(
                            MaxResponse.newBuilder()
                                    .setMax(currentMax)
                                    .build()
                    );
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

    }

    @Override
    public void sqrt(SqrtRequest request, StreamObserver<SqrtResponse> responseObserver) {
        int number = request.getNumber();

        if(number < 0) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("The number being sent cannot be negative")
                    .augmentDescription("Number: " + number)
                    .asRuntimeException());
            return;
        }

        responseObserver.onNext(
                SqrtResponse.newBuilder().setResult(Math.sqrt(number)).build()
        );
        responseObserver.onCompleted();
    }
}
