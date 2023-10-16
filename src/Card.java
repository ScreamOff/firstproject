//klasa karty majaca reprezentowac karte do gry


public class Card {
    private final Ranga ranga;
    private final Kolor kolor;


    public enum Ranga{
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);
        private int wartosc;

        Ranga(int wartosc){//konstruktor dla rangi
            this.wartosc = wartosc;
        }
        @Override
        public String toString(){//nadpisana metoda na zwracanie rangi karty w formie string
            return this.name().charAt(0) + this.name().substring(1, this.name().length()).toLowerCase();
        }
    }
    public enum Kolor{
        CLUBS, DIAMONDS, HEARTS, SPADES;
        @Override
        public String toString() {//nadpisana metoda na zwracanie koloru karty w formie string
            return this.name().charAt(0) + this.name().substring(1, this.name().length()).toLowerCase();
    }

}
    public Card(Ranga ranga,Kolor kolor){//konstruktor dla calej karty
        this.kolor = kolor;
        this.ranga = ranga;

    }
    public int wartosc(){//zwraca wartosc rangi z obiektu karty
        return this.ranga.wartosc;
    }
    @Override
    public String toString() {//nadpisana metoda klasy enum zwracajaca calosc karty w jezyku angielskim
        return this.ranga + " of " + this.kolor;
    }

}
