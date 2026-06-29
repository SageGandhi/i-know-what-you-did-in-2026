package edu.gandhi.prajit.client;

import edu.gandhi.proto.model.Request;
import edu.gandhi.proto.model.Response;
import edu.gandhi.proto.model.UnaryStreamingServiceGrpc;
import io.grpc.*;

import java.io.File;
import java.text.MessageFormat;

public class UnaryClientUnaryServerTls {
    static void main() throws Exception {
        final ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
                .trustManager(new File("tls/certificate.authority.pem"))
                .build();
        final ManagedChannel managedChannel = Grpc.newChannelBuilderForAddress("localhost", 50502, credentials)
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
