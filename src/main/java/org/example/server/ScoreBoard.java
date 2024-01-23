package org.example.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/// Klasa reprezentująca wynik gracza w grze.
@Getter
@Setter
@AllArgsConstructor
public class ScoreBoard {
    /// Punkty zdobyte przez gracza
    private int points;
    /// Identyfikator gracza
    private UUID id;
}
