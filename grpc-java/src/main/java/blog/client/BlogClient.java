package blog.client;

import com.google.protobuf.Empty;
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

        readBlog(stub, blogId);
        updateBlog(stub, blogId);
        listBlogs(stub);
        deleteBlog(stub, blogId);
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

    private static void readBlog(BlogServiceGrpc.BlogServiceBlockingStub stub, BlogId blogId) {
        try {
            Blog readResponse = stub.readBlog(blogId);
            System.out.println("Blog read: " + readResponse);
        } catch (StatusRuntimeException e) {
            System.out.println("Couldn't read the blog");
            e.printStackTrace();
        }
    }

    private static void updateBlog(BlogServiceGrpc.BlogServiceBlockingStub stub, BlogId blogId) {
        try {
            Blog newBlog = Blog.newBuilder()
                    .setId(blogId.getId())
                    .setAuthor("Bruno")
                    .setTitle("New blog (Changed)")
                    .setContent("Hello World this is my first blog! I've added some content")
                    .build();

            stub.updateBlog(newBlog);
            System.out.println("Blog updated: " + newBlog);
        } catch (StatusRuntimeException e) {
            System.out.println("Couldn't update the blog");
            e.printStackTrace();
        }
    }

    private static void listBlogs(BlogServiceGrpc.BlogServiceBlockingStub stub) {
        stub.listBlogs(Empty.getDefaultInstance()).forEachRemaining(System.out::println);
    }

    private static void deleteBlog(BlogServiceGrpc.BlogServiceBlockingStub stub, BlogId blogId) {
        try {
            stub.deleteBlog(blogId);
            System.out.println("Blog deleted: " + blogId.getId());
        } catch (StatusRuntimeException e) {
            System.out.println("The blog couldn't be deleted");
            e.printStackTrace();
        }
    }

}
