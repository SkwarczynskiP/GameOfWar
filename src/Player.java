import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Player {

    private Stack<Card> cards;
    private ArrayList<Card> winPile;

    public Player() {
        cards = new Stack<>();
        winPile = new ArrayList<>();
    }

    public void dealCard(Card card) {
        cards.push(card);
    }

    public Card playCard() {
        Card card = cards.pop();
        if (cards.isEmpty()) {
            shufflePileIntoStack();
        }
        return card;
    }

    public void winCards(ArrayList<Card> cards) {
        winPile.addAll(cards);
    }

    public void shufflePileIntoStack() {
        Collections.shuffle(winPile);

        for (Card card : winPile) {
            cards.push(card);
        }
        winPile.clear();
    }

    public boolean isOutOfCards() {
        return cards.isEmpty();
    }
}
