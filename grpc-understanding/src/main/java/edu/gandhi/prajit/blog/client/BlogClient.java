package edu.gandhi.prajit.blog.client;

import com.google.protobuf.Empty;
import edu.gandhi.proto.blog.Blog;
import edu.gandhi.proto.blog.BlogId;
import edu.gandhi.proto.blog.BlogServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.Nonnull;
import java.io.PrintStream;

public final class BlogClient {
    static edu.gandhi.proto.blog.BlogId createBlog(edu.gandhi.proto.blog.BlogServiceGrpc.BlogServiceBlockingStub stub) {
        edu.gandhi.proto.blog.BlogId createResponse = stub.createBlog(edu.gandhi.proto.blog.Blog.newBuilder()
                .setAuthor("gandhi").setTitle("new blog")
                .setContent("first blog").build());
        System.out.println("blog created: " + createResponse.getId());
        return createResponse;
    }

    static void readBlog(BlogServiceGrpc.BlogServiceBlockingStub stub, @Nonnull BlogId blogId) {
        Blog readResponse = stub.readBlog(blogId);
        System.out.println("blog read:" + readResponse);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void updateBlog(BlogServiceGrpc.BlogServiceBlockingStub stub, @Nonnull BlogId blogId) {
        Blog newBlog = Blog.newBuilder().setId(blogId.getId()).setAuthor("changed Author")
                .setTitle("new blog (updated)!")
                .setContent("added some more content").build();
        stub.updateBlog(newBlog);
        System.out.println(newBlog);
    }

    static void listBlogs(BlogServiceGrpc.BlogServiceBlockingStub stub, PrintStream ps) {
        stub.listBlogs(Empty.getDefaultInstance()).forEachRemaining(ps::print);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void deleteBlog(BlogServiceGrpc.BlogServiceBlockingStub stub, @Nonnull BlogId blogId) {
        stub.deleteBlog(blogId);
    }

    static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50053)
                .usePlaintext().build();
        BlogServiceGrpc.BlogServiceBlockingStub stub = BlogServiceGrpc.newBlockingStub(channel);

        BlogId id = createBlog(stub);
        readBlog(stub, id);
        updateBlog(stub, id);
        listBlogs(stub, System.out);
        deleteBlog(stub, id);

        System.out.println("Shutting Down");
        channel.shutdown();
    }
}