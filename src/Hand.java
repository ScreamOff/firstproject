import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;
    public Hand(Deck deck){

        cards = new ArrayList<>();
        cards.add(deck.drawCard());
        cards.add(deck.drawCard());
    }
    public void addCard(Card card){
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }
    public int calculateCardValue(){
        int value=0;
        int numAce=0;
        for(Card card : cards){
            String ranga = card.getRanga();
            if(ranga.equals("Ace")){
                numAce++;
                value+=11;

            } else if (ranga.equals( "Jack")||ranga.equals("Queen")||ranga.equals("King")){
                value+=10;
            }else{
                value+=Integer.parseInt(ranga);

            }
        }
        while(numAce>0&&value>21){
            value-=10;
            numAce--;
        }
        return value;
    }

}


