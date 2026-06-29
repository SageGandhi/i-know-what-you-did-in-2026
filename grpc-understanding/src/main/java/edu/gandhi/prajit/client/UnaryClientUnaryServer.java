package edu.gandhi.prajit.client;

import edu.gandhi.proto.model.Request;
import edu.gandhi.proto.model.Response;
import edu.gandhi.proto.model.UnaryStreamingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.text.MessageFormat;

public class UnaryClientUnaryServer {
    static void main() {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50501)
                .usePlaintext()
                .build();

        UnaryStreamingServiceGrpc.UnaryStreamingServiceBlockingStub blockingStubGreeting =
                UnaryStreamingServiceGrpc.newBlockingStub(managedChannel);
        Response responseGreeting = blockingStubGreeting.unaryClientUnaryServer(
                Request.newBuilder().setRequest("Gandhi").build());
        System.out.println(MessageFormat.format("unary response received:{0}",
                responseGreeting.getResponse()));

        System.out.println("shutting down client channel.");
        managedChannel.shutdown();
        System.out.println("client channel closed now.");
    }

}
