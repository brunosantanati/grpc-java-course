package greeting.client;

import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc;
import io.grpc.*;

import java.io.File;
import java.io.IOException;

public class GreetingClientTls {

    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Need one argument to work");
            return;
        }

        ChannelCredentials creds = TlsChannelCredentials.newBuilder().trustManager(
                new File("ssl/ca.crt")
        ).build();

        ManagedChannel channel =
                Grpc.newChannelBuilderForAddress("localhost", 50051, creds).build();

        switch (args[0]){
            case "greet": doGreet(channel); break;
            default:
                System.out.println("Invalid Keyword: " + args[0]);
        }

        System.out.println("Shutting down");
        channel.shutdown();
    }

    private static void doGreet(ManagedChannel channel) {
        System.out.println("Enter doGreet (Tls)");
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        GreetingResponse response = stub.greet(GreetingRequest.newBuilder().setFirstName("Bruno").build());

        System.out.println("Greeting: " + response.getResult());
    }
}
