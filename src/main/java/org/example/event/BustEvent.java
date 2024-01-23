package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.event.init.Event;
import org.example.server.ServerClient;

import java.util.List;
import java.util.UUID;

/// Klasa reprezentująca zdarzenie "Bust" (przekroczenie liczby punktów 21) w grze.
@Getter
@AllArgsConstructor
public class BustEvent implements Event {
    /// Unikalny identyfikator klienta, który przekroczył 21 punktów.
    private UUID id;

    /// Metoda do obsługi zdarzenia "Bust".
    /// Przyjmuje listę klientów i wysyła do każdego z nich zdarzenie "WinResultEvent" z identyfikatorem klienta, który przekroczył 21 punktów.
    public void consume(List<ServerClient> clientList) {
        clientList.forEach(client -> {
            client.sendEvent(new WinResultEvent(id));
        });
    }
}
