package org.example.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.event.init.Event;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;


@Slf4j
@Getter
/// Klasa reprezentująca klienta połączonego z serwerem.
public class ServerClient {
    /// Unikalny identyfikator klienta.
    private final UUID id;

    /// Kolejka zdarzeń dla klienta.
    private final BlockingQueue<Event> events;

    /// Obsługiwacz zdarzeń dla klienta.
    private final BiConsumer<UUID, Event> eventHandler;

    /// Flaga informująca, czy klient jest gotowy.
    private boolean ready = false;

    /// Flaga informująca, czy klient zdecydował "stand" (nie dobiera już kart).
    private boolean stand = false;

    /// Konstruktor klasy ServerClient.
    /// @param eventHandler Obsługiwacz zdarzeń dla klienta.
    public ServerClient(BiConsumer<UUID, Event> eventHandler) {
        this.id = UUID.randomUUID();
        this.events = new LinkedBlockingQueue<>();
        this.eventHandler = eventHandler;
    }

    /// Ustawia flagę "stand" dla klienta.
    /// @param stand Wartość flagi "stand".
    public void setStand(boolean stand) {
        this.stand = stand;
    }

    /// Wysyła zdarzenie do klienta.
    /// @param event Zdarzenie do wysłania.
    public void sendEvent(Event event) {
        log.info(event.toString());
        this.events.add(event);
    }

    /// Oznacza klienta jako gotowego.
    public void markAsReady() {
        this.ready = true;
    }
}
