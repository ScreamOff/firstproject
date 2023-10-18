public class BlackjackClient {
    public static void main(String[] args) {
        Deck deck = new Deck();

        // Wyświetlenie ilości kart w talii
        System.out.println("Liczba kart w talii: " + deck.getDeckSize());

        // Pobranie kilku kart z talii (na potrzeby testów)
        int numCardsToDraw = 5;
        for (int i = 0; i < numCardsToDraw; i++) {
            Card card = deck.drawCard();
            if (card != null) {
                System.out.println("Pobrana karta: " + card);
            } else {
                System.out.println("Brak kart w talii.");
                break;
            }
        }

        // Ponowne wyświetlenie ilości kart w talii po pobraniu kilku kart
        System.out.println("Liczba kart w talii po pobraniu: " + deck.getDeckSize());
    }
}
