package blog.client;

import com.proto.blog.Blog;
import com.proto.blog.BlogId;
import com.proto.blog.BlogServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class BlogClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        run(channel);

        System.out.println("Shutting down");
        channel.shutdown();
    }

    private static void run(ManagedChannel channel) {
        BlogServiceGrpc.BlogServiceBlockingStub stub = BlogServiceGrpc.newBlockingStub(channel);

        BlogId blogId = createBlog(stub);

        if(blogId == null)
            return;
        //...
    }

    private static BlogId createBlog(BlogServiceGrpc.BlogServiceBlockingStub stub) {
        try {
            BlogId createResponse = stub.createBlog(
                    Blog.newBuilder()
                            .setAuthor("Bruno")
                            .setTitle("New Blog!")
                            .setContent("Hello World this is a new blog!")
                            .build()
            );

            System.out.println("Blog create: " + createResponse.getId());
            return createResponse;
        } catch (StatusRuntimeException e) {
            System.out.println("Couldn't create the blog");
            e.printStackTrace();
            return null;
        }
    }

}
