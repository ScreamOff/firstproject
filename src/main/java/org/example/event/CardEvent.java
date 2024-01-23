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

public class CardEvent implements Event {
    UUID owner;
    Card card;

    public void consume(UUID clientId, Hand myHand, Hand opositeHand) {
        if (Objects.equals(owner, clientId)) {
            myHand.addCard(card); // myHand.addCard(card, false); drugi argument boolean steruje czy karta ma byc odwócana
        } else {
            opositeHand.addCard(card); //opositeHand.addCard(card, isPrivate);
        }
    }
}
