package org.example.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/// Klasa przedstawiająca reke gracza
public class Hand implements Serializable {
    /// Stos kart, które stanowią talię
    private final List<Card> cards;
    /// Konstruktor klasy ręki
    public Hand() {

        cards = new ArrayList<>();
    }
    /// metoda dodająca karte
    public void addCard(Card card) {
        cards.add(card);
    }
    /// metoda pobierająca klase z klasy Card
    public List<Card> getCards() {
        return cards;
    }
    /// metoda pobierająca karte
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
    /// zmienna na ilosc kart w ręku
    public int size() {
        return cards.size();
    }
    /// metoda czyszcząca dłoń służaca do nowej rozgrywki(do implementacji)
    public void clear() {
        cards.clear();
    }
    /// metoda blokująca widok karty przed przeciwnikiem
    public void block() {
        cards.getFirst().setRestricted(true);
    }
    /// metoda pokazująca karty przeciwnika
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


