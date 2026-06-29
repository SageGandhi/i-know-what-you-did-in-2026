package edu.gandhi.prajit.client;

import edu.gandhi.proto.model.Request;
import edu.gandhi.proto.model.Response;
import edu.gandhi.proto.model.UnaryStreamingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class StreamingClientUnaryServer {
    static void main() throws Exception {
        final ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 50501).usePlaintext().build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final UnaryStreamingServiceGrpc.UnaryStreamingServiceStub blockingStubGreeting = UnaryStreamingServiceGrpc.newStub(managedChannel);
        final StreamObserver<Request> request = blockingStubGreeting.streamingClientUnaryServer(new StreamObserver<>() {
            @Override
            public void onNext(Response response) {
                System.out.println("response received from server:\n" + response.getResponse());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        IntStream.range(0, 5).forEach(index -> {
            request.onNext(Request.newBuilder().setRequest(index + ":[" + UUID.randomUUID() + "]").build());
        });
        request.onCompleted();
        countDownLatch.await();

        System.out.println("shutting down client channel.");
        managedChannel.shutdown();
        System.out.println("client channel closed now.");
    }

}
