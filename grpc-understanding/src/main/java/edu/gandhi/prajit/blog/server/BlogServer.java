package edu.gandhi.prajit.blog.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public final class BlogServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        MongoClient client = MongoClients.create("mongodb://root:root@localhost:27017/");

        Server server = ServerBuilder.forPort(50053)
                .addService(new BlogServiceImpl(client))
                .build();

        server.start();
        System.out.println("server started and listening on port: " + 50053);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("received shutdown request");
            server.shutdown();
            client.close();
            System.out.println("server stopped");
        }));

        server.awaitTermination();
    }
}