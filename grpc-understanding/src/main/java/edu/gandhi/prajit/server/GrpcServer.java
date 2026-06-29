package edu.gandhi.prajit.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

    static void main() throws Exception {
        final Server serverGrpc = ServerBuilder.forPort(50501)
                .addService(UnaryStreamingServiceGrpcImplementation.class.getDeclaredConstructor().newInstance())
                .build();
        serverGrpc.start();
        System.out.println("gRpc server started@50501.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("initiating shutdown request received.");
            serverGrpc.shutdown();
            System.out.println("gRpc server stopped.");
        }));
        serverGrpc.awaitTermination();

    }
}
