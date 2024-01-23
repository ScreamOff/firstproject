package org.example.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable {
    private final List<Card> cards;

    public Hand() {

        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int calculateCardValue() {
        int value = 0;
        int numAce = 0;
        for (Card card : cards) {
            String ranga = card.getRanga();
            if (ranga.equals("Ace")) {
                numAce++;
                value += 11;

            } else if (ranga.equals("Jack") || ranga.equals("Queen") || ranga.equals("King")) {
                value += 10;
            } else {
                value += Integer.parseInt(ranga);

            }
        }
        while (numAce > 0 && value > 21) {
            value -= 10;
            numAce--;
        }
        return value;
    }

    public int size() {
        return cards.size();
    }

    public void clear() {
        cards.clear();
    }

    public void block() {
        cards.getFirst().setRestricted(true);
    }

    public void show() {
        cards.getFirst().setRestricted(false);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}


