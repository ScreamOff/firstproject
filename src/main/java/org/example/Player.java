package org.example;

import java.io.Serializable;

public class Player implements Serializable {

    private Hand hand;
    private boolean stand,hit;

    Deck deck;
    public Player(Deck deck) {
        this.hand = new Hand(deck);
        this.stand = false;
        this.deck = deck;
    }

    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    public Hand getHand() {
        return hand;
    }

    public void displayHand() {
        System.out.println("Player's hand: " + hand.getCards());
    }

    public int calculateHandValue() {
        return hand.calculateCardValue();
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    //obsluga stania
    public boolean isStand() {
        return stand;
    }

    public void setStand(boolean stand) {
        this.stand = stand;
    }
}
