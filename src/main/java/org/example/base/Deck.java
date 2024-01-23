package org.example.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/// Klasa reprezentująca talię kart do gry.
public class Deck {
    /// Stos kart, które stanowią talię
    private final Stack<Card> cards;
    /// Konstruktor tworzący nową talię i inicjalizujący ją.
    public Deck() {
        cards = new Stack<>();
        initializeDeck();
    }
    /// Metoda inicjalizująca talię kart.
    private void initializeDeck() {
        List<Card> cardList = new ArrayList<>(); // Tworzymy listę kart
        /// Tablice z rangami i kolorami kart.
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(rank, suit);
                cardList.add(card);
            }
        }

        /// Tasujemy listę kart
        Collections.shuffle(cardList);

        /// Przenosimy karty z listy do stosu
        for (Card card : cardList) {
            cards.push(card);
        }
    }
    /// Metoda wyciągająca kartę z tali.
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.pop();
        } else {
            /// Brak kart w talii.
            return null;
        }
    }
    /// Metoda zwracająca rozmiar talii.
    public int getDeckSize() {
        return cards.size();
    }
}
