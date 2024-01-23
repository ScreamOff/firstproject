package org.example.event;

import org.example.event.init.Event;

/// Klasa reprezentująca zdarzenie żądania zakończenia .
public class EndingRequest implements Event {

    /// Metoda do obsługi zdarzenia zakończenia żądania.
    public void consume() {
        // Wyświetlenie komunikatu "PING" na konsoli.
        System.out.println("PING");
    }
}
