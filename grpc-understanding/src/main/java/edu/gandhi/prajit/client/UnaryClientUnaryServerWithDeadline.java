package edu.gandhi.prajit.client;

import edu.gandhi.proto.model.Request;
import edu.gandhi.proto.model.Response;
import edu.gandhi.proto.model.UnaryStreamingServiceGrpc;
import io.grpc.*;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class UnaryClientUnaryServerWithDeadline {
    static void main() {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50501)
                .usePlaintext()
                .build();

        UnaryStreamingServiceGrpc.UnaryStreamingServiceBlockingStub blockingStubGreeting =
                UnaryStreamingServiceGrpc.newBlockingStub(managedChannel);
        Response responseGreeting = blockingStubGreeting
                .withDeadline(Deadline.after(10, TimeUnit.SECONDS))
                .grpcWithDeadline(Request.newBuilder().setRequest("Gandhi").build());
        System.out.println(MessageFormat.format("unary response received:{0}",
                responseGreeting.getResponse()));

        try {
            responseGreeting = blockingStubGreeting
                    .withDeadline(Deadline.after(3, TimeUnit.SECONDS))
                    .grpcWithDeadline(Request.newBuilder().setRequest("Gandhi").build());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
                System.out.println("deadline has been exceeded");
            } else {
                System.out.println("getting exception:" + e.getMessage());
            }
        }

        System.out.println("shutting down client channel.");
        managedChannel.shutdown();
        System.out.println("client channel closed now.");
    }

}
