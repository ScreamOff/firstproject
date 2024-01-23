package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.base.Hand;
import org.example.event.init.Event;
import org.example.server.ScoreBoard;
import org.example.server.ScoreBoardComparator;
import org.example.server.ServerClient;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
/// Klasa reprezantująca zakończenie gry
public class Ending implements Event {

    /// Unikalny identyfikator klienta kończącego grę.
    private UUID id;

    /// Ręka (karty) klienta kończącego grę.
    private Hand hand;

    /// Metoda do obsługi zdarzenia zakończenia gry.
    public void consume(List<ScoreBoard> sc, List<ServerClient> clientList) {
        // Dodanie wyniku do tablicy wyników.
        sc.add(new ScoreBoard(this.hand.calculateCardValue(), this.id));

        // Sprawdzenie, czy zebrano wyniki od wszystkich graczy.
        if (sc.size() == 2) {
            // Sortowanie tablicy wyników w malejącej kolejności.
            sc.sort(new ScoreBoardComparator());

            // Wysłanie zdarzenia WinResultEvent do wszystkich klientów z wynikiem pierwszego gracza.
            clientList.forEach(client -> {
                client.sendEvent(new WinResultEvent(sc.getFirst().getId()));
            });
        }
    }
}
