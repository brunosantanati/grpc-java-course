package calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

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
 */

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50052;

        Server server = ServerBuilder.forPort(port)
                .addService(new CalculatorServerImpl())
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
