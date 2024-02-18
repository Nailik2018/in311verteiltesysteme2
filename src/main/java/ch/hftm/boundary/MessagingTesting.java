package ch.hftm.boundary;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class MessagingTesting {

//    Für TestResource.java Wichtig: Die source()-Methode aus der Messaging-Klasse darf nicht mehr vorhanden sein.
//    @Outgoing("source")
//    public Multi<String> source() {
//        return Multi.createFrom().items("hallo", "böse", "quarkus", "fans");
//    }

    @Incoming("source")
    @Outgoing("processed-a")
    public String toUpperCase(String payload) {
        return payload.toUpperCase();
    }

//    @Incoming("processed-a")
//    public void sink(String word) {
//        System.out.println(">> " + word);
//    }

    // b) Weiterer Transformer einbinden
    // Erstelle ein weiterer Transformer, welcher jeden Wert "BÖSE" mit "LIEBE" ersetzt. Stelle sicher,
    // dass diese Transformation nach dem toUpperCase() stattfindet.
    @Incoming("processed-a")
    @Outgoing("processed-b")
    public String correctStrings(String payload) {
        if(payload.equals("BÖSE")) {
            return "LIEBE";
        }
        return payload;
    }

//    @Incoming("processed-b")
//    public void sink(String word) {
//        System.out.println(">> " + word);
//    }

    // c) Ergänze einen Filter
    // Erweitere das Projekt mit einem zusätzlichem Transformer, welche die Zeichenketten ausschliesst,
    // welche kleiner als 6 Zeichen sind. Verwende dazu ein Transformer, der mit Multi<String> arbeitet,
    // als Input- sowie auch als Output-Typ
    @Incoming("processed-b")
    @Outgoing("processed-c")
    public Multi<String> filter(Multi<String> input) {
        return input.select().where(item -> item.length() > 5);
    }

    @Incoming("processed-c")
    public void sink(String word) {
        System.out.println(">> " + word);
    }
}