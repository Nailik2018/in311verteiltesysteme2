package ch.hftm.boundary;
import entities.Blog;
import org.eclipse.microprofile.reactive.messaging.Incoming;

public class BlogSink {
    @Incoming("blog")
    public void blogSink(Blog blog) {
        System.out.println("SINK " + blog);
        System.out.println("SINK " + blog.getTitle());
        System.out.println("SINK " + blog.getContent());
    }
}