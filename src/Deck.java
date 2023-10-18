import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
public class Deck {
    private Stack<Card> cards;

    public Deck(){
        cards = new Stack<>();
        initializeDeck();
    }
    private void initializeDeck(){
        List<Card> cardList = new ArrayList<>();

        String[] rangi = {"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "Jack", "Queen", "King", "Ace"};
        String[] kolory = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for(String kolor : kolory){
            for(String ranga:rangi){
                Card card = new Card(ranga,kolor);
                cardList.add(card);
            }

        }
        Collections.shuffle(cardList);
        for(Card card:cardList){
            cards.push(card);
        }
    }
    public Card drawCard(){
        if(!cards.isEmpty()){
            return cards.pop();
        }else{
            return null;
        }
    }
    public int getDeckSize(){
        return cards.size();
    }
}
