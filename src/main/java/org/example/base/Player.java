package org.example.base;

import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {

    Deck deck;
    private Hand hand;
    private boolean stand, hit;
    private Socket clientSocket;

    public Player(Deck deck) {
        //this.hand = new Hand(deck);
        this.stand = false;
        this.deck = deck;

    }

    public Socket getSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
