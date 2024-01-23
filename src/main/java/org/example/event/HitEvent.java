package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.event.init.Event;

import java.util.UUID;

/// Klasa reprezentująca zdarzenie "Hit" (dobranie karty) w grze.
@Slf4j
@Getter
@AllArgsConstructor
@Setter
public class HitEvent implements Event {
    /// Unikalny identyfikator klienta, który wykonuje zdarzenie "Hit".
    private UUID id;
    /// Metoda do obsługi zdarzenia "Hit".
    public void consume() {
    }
}
