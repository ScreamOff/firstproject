package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        initializeDeck();
    }

    private void initializeDeck() {
        List<Card> cardList = new ArrayList<>(); // Tworzymy listę kart

        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(rank, suit);
                cardList.add(card);
            }
        }

        // Tasujemy listę kart
        Collections.shuffle(cardList);

        // Przenosimy karty z listy do stosu
        for (Card card : cardList) {
            cards.push(card);
        }
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.pop();
        } else {
            return null; // No cards left in the deck
        }
    }

    public int getDeckSize() {
        return cards.size();
    }
}
