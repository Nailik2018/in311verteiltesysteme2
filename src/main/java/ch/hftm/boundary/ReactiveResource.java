package ch.hftm.boundary;
import java.time.Duration;
import org.jboss.resteasy.reactive.RestStreamElementType;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/reactive")
public class ReactiveResource {
//    @Inject
//    BlogRepository blogRepository;

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Blocking
//    @Path("blocking")
//    public Uni<String> blockingExample() {
//        Optional<Blog> entry = blogRepository.findAll().firstResultOptional();
//        if(entry.isPresent()) {
//            return Uni.createFrom().item(entry.get().getTitle());
//        }
//        return Uni.createFrom().item("No entry around");
//    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Blocking
    @Path("blocking2/{name}/{count}")
    public Uni<String> blockingExample2(String name, int count) {
        return Uni.createFrom().item(generateGreeting(name, count));
    }

    // Hilfsmethode, um den Gruß zu generieren
    private String generateGreeting(String name, int count) {
        StringBuilder greetingBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            greetingBuilder.append("hello ").append(name).append(" - ").append(i + 1);
            if (i < count - 1) {
                greetingBuilder.append("\n"); // Fügen Sie einen Zeilenumbruch hinzu, außer beim letzten Eintrag
            }
        }
        return greetingBuilder.toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("uni/{name}")
    public Uni<String> greeting(String name) {
        return Uni.createFrom().item(name)
                .onItem().transform(n -> String.format("hello %s", name));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/multi/{name}/{count}")
    public Multi<String> greetings(String name, int count) {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n -> String.format("hello %s - %d", name, n))
                .select().first(count);
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.TEXT_PLAIN)
    @Path("/sse/{name}/{count}")
    public Multi<String> greetingsAsServerSentEvents(int count, String name) {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n -> String.format("hello %s - %d", name, n))
                .select().first(count);
    }
}