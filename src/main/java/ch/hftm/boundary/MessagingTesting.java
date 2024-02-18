package ch.hftm.boundary;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class MessagingTesting {

    @Outgoing("source")
    public Multi<String> source() {
        return Multi.createFrom().items("hallo", "böse", "quarkus", "fans");
    }

    @Incoming("source")
    @Outgoing("processed-a")
    public String toUpperCase(String payload) {
        return payload.toUpperCase();
    }

    @Incoming("processed-a")
    public void sink(String word) {
        System.out.println(">> " + word);
    }
}