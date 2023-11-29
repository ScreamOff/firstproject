public class Dealer {

    private Deck deck;
    private Hand hand;

    public Dealer(Deck deck) {
        this.deck = deck;
        this.hand = new Hand(deck);
    }

    public void dealCardToSelf() {
        Card card = deck.drawCard();
        if (card != null) {
            hand.addCard(card);
        }
    }

    public void play() {
        while (shouldDealerDrawCard()) {
            dealCardToSelf();
        }
    }

    private boolean shouldDealerDrawCard() {
        // Dealer dobiera kartę, dopóki suma punktów nie przekroczy 16
        return hand.calculateCardValue() < 16;
    }

    public void displayDealerHand() {
        System.out.println("Dealer's hand: " + hand.getCards());
    }

    public Hand getHand() {
        return hand;
    }
}
