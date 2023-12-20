public class Player {

    private Hand hand;
    private boolean stand;

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
    //obsluga stania
    public boolean isStand() {
        return stand;
    }

    public void setStand(boolean stand) {
        this.stand = stand;
    }
}
