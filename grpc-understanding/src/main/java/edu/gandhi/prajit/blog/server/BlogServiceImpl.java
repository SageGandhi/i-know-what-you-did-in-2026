package edu.gandhi.prajit.blog.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.protobuf.Empty;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import edu.gandhi.proto.blog.Blog;
import edu.gandhi.proto.blog.BlogId;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public final class BlogServiceImpl extends edu.gandhi.proto.blog.BlogServiceGrpc.BlogServiceImplBase {

    @VisibleForTesting
    static final String BLOG_COULD_NOT_BE_CREATED = "The blog could not be created";
    @VisibleForTesting
    static final String BLOG_COULD_NOT_BE_DELETED = "The blog could not be deleted";
    @VisibleForTesting
    static final String BLOG_WAS_NOT_FOUND = "The blog with the corresponding id was not found";

    private final MongoCollection<Document> mongoCollection;

    BlogServiceImpl(MongoClient client) {
        MongoDatabase db = client.getDatabase("blog-database");
        mongoCollection = db.getCollection("blog");
    }

    private io.grpc.StatusRuntimeException error(String message) {
        return Status.INTERNAL.withDescription(message).asRuntimeException();
    }

    @SuppressWarnings("SameParameterValue")
    private io.grpc.StatusRuntimeException error(Status status, String message, String augmentMessage) {
        return status.withDescription(message).augmentDescription(augmentMessage).asRuntimeException();
    }

    Blog documentToBlog(Document document) {
        return Blog.newBuilder()
                .setAuthor(document.getString("author"))
                .setTitle(document.getString("title"))
                .setContent(document.getString("content"))
                .setId(document.getObjectId("_id").toString())
                .build();
    }

    @Override
    public void createBlog(Blog request, StreamObserver<BlogId> responseObserver) {
        Document doc = new Document("author", request.getAuthor()).append("title", request.getTitle())
                .append("content", request.getContent());

        InsertOneResult result = mongoCollection.insertOne(doc);

        if (!result.wasAcknowledged() || Objects.isNull(result.getInsertedId())) {
            responseObserver.onError(error(BLOG_COULD_NOT_BE_CREATED));
        }

        String id = Objects.requireNonNull(result.getInsertedId()).asObjectId().getValue().toString();
        responseObserver.onNext(BlogId.newBuilder().setId(id).build());
        responseObserver.onCompleted();
    }

    @Override
    public void readBlog(BlogId request, StreamObserver<Blog> responseObserver) {
        String id = request.getId();
        Document result = mongoCollection.find(eq("_id", new ObjectId(id))).first();
        responseObserver.onNext(documentToBlog(Objects.requireNonNull(result)));
        responseObserver.onCompleted();
    }

    @Override
    public void updateBlog(Blog request, StreamObserver<Empty> responseObserver) {
        String id = request.getId();
        Document result = mongoCollection.findOneAndUpdate(eq("_id", new ObjectId(id)),
                combine(set("author", request.getAuthor()), set("title", request.getTitle()),
                        set("content", request.getContent())));

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteBlog(BlogId request, StreamObserver<Empty> responseObserver) {
        String id = request.getId();
        DeleteResult result = mongoCollection.deleteOne(eq("_id", new ObjectId(id)));

        if (!result.wasAcknowledged()) {
            responseObserver.onError(error(BLOG_COULD_NOT_BE_DELETED));
        }

        if (result.getDeletedCount() == 0) {
            responseObserver.onError(error(Status.NOT_FOUND, BLOG_WAS_NOT_FOUND, "BlogId: " + id));
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void listBlogs(Empty request, StreamObserver<Blog> responseObserver) {
        for (Document document : mongoCollection.find()) {
            responseObserver.onNext(documentToBlog(document));
        }
        responseObserver.onCompleted();
    }
}