package edu.gandhi.prajit.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.File;

public class GrpcServerLocalTls {

    static void main() throws Exception {

        final Server serverGrpc = ServerBuilder.forPort(50502)
                .useTransportSecurity(new File("tls/server.pem"),new File("tls/server.key.pem"))
                .addService(UnaryStreamingServiceGrpcImplementation.class.getDeclaredConstructor().newInstance())
                .build();
        serverGrpc.start();
        System.out.println("gRpc server started@50502.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("initiating shutdown request received.");
            serverGrpc.shutdown();
            System.out.println("gRpc server stopped.");
        }));
        serverGrpc.awaitTermination();
    }
}
