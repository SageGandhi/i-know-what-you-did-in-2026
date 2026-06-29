package edu.gandhi.prajit.server;

import edu.gandhi.proto.model.Request;
import edu.gandhi.proto.model.Response;
import edu.gandhi.proto.model.UnaryStreamingServiceGrpc;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.IntStream;

public class UnaryStreamingServiceGrpcImplementation extends UnaryStreamingServiceGrpc.UnaryStreamingServiceImplBase {
    @Override
    public void unaryClientUnaryServer(Request request, StreamObserver<Response> observer) {
        observer.onNext(Response.newBuilder().setResponse(MessageFormat.format("welcome {0}", request.getRequest())).build());
        observer.onCompleted();
    }

    @Override
    public void unaryClientStreamingServer(Request request, StreamObserver<Response> observer) {
        Response greetingResponse = Response.newBuilder().setResponse(request.getRequest() +
                " have a new uuid " + UUID.randomUUID()).build();
        IntStream.range(0, 5).forEach((index) -> observer.onNext(greetingResponse));
        observer.onCompleted();
    }

    @Override
    public StreamObserver<Request> streamingClientUnaryServer(StreamObserver<Response> observer) {
        final StringBuilder concatMessages = new StringBuilder();
        concatMessages.append("------------------Start------------------------------\n");
        return new StreamObserver<>() {
            @Override
            public void onNext(Request request) {
                System.out.println("receiving request:" + request.getRequest());
                concatMessages.append("[").append(request.getRequest()).append(" received @")
                        .append(Instant.now()).append("]\n");
            }

            @Override
            public void onError(Throwable t) {
                observer.onError(t);
            }

            @Override
            public void onCompleted() {
                concatMessages.append("-------------------End-----------------------------");
                observer.onNext(Response.newBuilder().setResponse(concatMessages.toString()).build());
                observer.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Request> streamingClientStreamingServer(StreamObserver<Response> response) {
        Context context = Context.current();
        return new StreamObserver<>() {
            @Override
            public void onNext(Request value) {
                System.out.println("good to get streaming value:" + value.getRequest());
                response.onNext(Response.newBuilder()
                        .setResponse("good to get streaming value:" + value.getRequest())
                        .build());
            }

            @Override
            public void onError(Throwable t) {
                response.onError(t);
            }

            @Override
            public void onCompleted() {
                response.onCompleted();
            }
        };
    }

    @Override
    public void grpcWithDeadline(Request request, StreamObserver<Response> streamObserver) {
        final Context context = Context.current();
        try {
            for (int index = 0; index < 5; index++) {
                if (context.isCancelled())
                    return;
                Thread.sleep(Duration.ofMillis(1000));
            }
            streamObserver.onNext(Response.newBuilder()
                    .setResponse("got request:" + request.getRequest())
                    .build());
            streamObserver.onCompleted();
        } catch (InterruptedException e) {
            streamObserver.onError(e);
        }
    }
}
