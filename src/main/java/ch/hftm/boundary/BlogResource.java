package ch.hftm.boundary;
import entities.Blog;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/blog")
public class BlogResource {
    @Inject
    @Channel("blog-source")
    Emitter<Blog> blogEmitter;

    @POST
    public void blogSource(Blog blog) {
        System.out.println("SEND: " + blog);
        blogEmitter.send(blog);
    }
}