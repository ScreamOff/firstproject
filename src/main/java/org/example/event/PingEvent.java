package org.example.event;

import org.example.event.init.Event;

/// Klasa reprezentująca zdarzenie ping.
public class PingEvent implements Event {

    /// Metoda do obsługi zdarzenia ping.
    public void consume() {
        // Wyświetlenie komunikatu "PING" na konsoli.
        System.out.println("PING");
    }
}
