package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.base.Card;
import org.example.base.Hand;
import org.example.event.init.Event;

import java.util.Objects;
import java.util.UUID;


@AllArgsConstructor
@Setter
@Getter
/// Klasa reprezentująca zdarzenie odbierania karty w grze.
public class CardEvent implements Event {
    /// Unikalny identyfikator właściciela karty.
    private UUID owner;

    /// Karta, która została odebrana.
    private Card card;

    /// Metoda do obsługi zdarzenia odbierania karty.
    /// Dodaje kartę do ręki (myHand lub opositeHand) w zależności od identyfikatora właściciela karty.
    public void consume(UUID clientId, Hand myHand, Hand opositeHand) {
        if (Objects.equals(owner, clientId)) {
            myHand.addCard(card); // myHand.addCard(card, false); drugi argument boolean steruje czy karta ma być odwrócona
        } else {
            opositeHand.addCard(card); // opositeHand.addCard(card, isPrivate);
        }
    }
}
