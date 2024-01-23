package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.event.init.Event;
import org.example.server.ServerClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/// Klasa reprezentująca zdarzenie "Stand" (stanie) w grze.
@AllArgsConstructor
@Getter
public class StandEvent implements Event {
    /// Unikalny identyfikator klienta, który wykonuje zdarzenie "Stand".
    private UUID id;

    /// Metoda do obsługi zdarzenia "Stand".
    /// Przyjmuje listę klientów i ustawia flagę "stand" dla klienta o pasującym identyfikatorze.
    public void consume(List<ServerClient> clientList) {
        clientList.forEach(client -> {
            if (Objects.equals(this.id, client.getId())) {
                client.setStand(true);
            }
        });
    }
}
