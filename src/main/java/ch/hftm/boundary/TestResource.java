package ch.hftm.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
@Path("/test")
public class TestResource {

    @Inject
    @Channel("source")
    Emitter<String> emitter;

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(String name) {
        emitter.send(name);
        return "done " + name;
    }
}