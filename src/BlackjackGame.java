 import java.util.List;

public class BlackjackGame {
    private Deck deck;
    private Player player;
    private Dealer dealer;

    public BlackjackGame() {
        deck = new Deck();
        player = new Player(deck);
        dealer = new Dealer(deck);
        //wrzucic dalsza inicjalizacje
    }

    // dodac cala logike gry i rozdan
}
