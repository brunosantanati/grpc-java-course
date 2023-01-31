package calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

import java.io.IOException;

/*
[Exercise] Sum API
Now it is your turn to write code!

In this exercise, your goal is to implement a Sum RPC Unary API in a CalculatorService:

The function takes a Request message that has two integers, and returns a Response that represents the sum of them.
Remember to first implement the service definition in a .proto file, alongside the RPC messages
Implement the Server code first
Test the server code by implementing the Client
Example:

The client will send two numbers (3 and 10) and the server will respond with (13)

Good luck!

======================================================================================

[Exercise] Primes API
Now it is your turn to write code!

In this exercise, your goal is to implement a Primes RPC Server Streaming API in a CalculatorService:

The function takes a Request message that has one integer, and returns a stream of Responses that represent the prime number decomposition of that number (see below for the algorithm).

Remember to first implement the service definition in a .proto file, alongside the RPC messages

Implement the Server code first

Test the server code by implementing the Client

Example:

The client will send one number (120) and the server will respond with a stream of (2,2,2,3,5), because 120=2*2*2*3*5

Algorithm (pseudo code):

k = 2
N = 210
while N > 1:
    if N % k == 0:   // if k evenly divides into N
        print k      // this is a factor
        N = N / k    // divide N by k so that we have the rest of the number left.
    else:
        k = k + 1


Good luck!

======================================================================================

[Exercise] Avg API
Now it is your turn to write code!

In this exercise, your goal is to implement a Avg RPC Client Streaming API in a CalculatorService:

The function takes a stream of Request message that has one integer, and returns a Response with a double that represents the computed average

Remember to first implement the service definition in a .proto file, alongside the RPC messages

Implement the Server code first

Test the server code by implementing the Client

Example:

The client will send a stream of number (1,2,3,4) and the server will respond with (2.5), because (1+2+3+4)/4 = 2.5

Good luck!

======================================================================================

[Exercise] Max API

Now it is your turn to write code!

In this exercise, your goal is to implement a Max RPC Bi-Directional Streaming API in a CalculatorService:

    The function takes a stream of Request message that has one integer, and returns a stream of Responses that represent the current maximum between all these integers

    Remember to first implement the service definition in a .proto file, alongside the RPC messages

    Implement the Server code first

    Test the server code by implementing the Client

Example:

The client will send a stream of number (1,5,3,6,2,20) and the server will respond with a stream of (1,5,6,20)

Good luck!
 */

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50052;

        Server server = ServerBuilder.forPort(port)
                .addService(new CalculatorServerImpl())
                .addService(ProtoReflectionService.newInstance())
                .build();

        server.start();
        System.out.println("Server Started");
        System.out.println("Listening on port: " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Server Stopped");
        }));

        server.awaitTermination();
    }

}
