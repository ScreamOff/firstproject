package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.event.init.Event;

import java.util.UUID;


@Getter
@AllArgsConstructor
/// Klasa reprezentująca zdarzenie wyniku wygranej.
public class WinResultEvent implements Event {

    /// Pole przechowujące identyfikator gracza.
    private UUID id;
}
