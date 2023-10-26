import java.util.ArrayList;

// TODO: Make an interface for this class.
public class PlayingStack extends Stack {
    public PlayingStack() {
        super();
    }

    public void addCard(Card cardToAdd) {
        deck.add(cardToAdd);
    }

    public ArrayList<Card> seeDeck() {
        return deck;
    }

    public void popWholeStack() {
        deck.clear();
    }

    public void pop() {
        deck.remove(deck.size() - 1);
    }

    public Card getLastCard() {
        try {
            return deck.get(deck.size() - 1);

        } catch (IndexOutOfBoundsException err) {
            return null;
        }
    }

    public void setDeck(Card[] newDeck) {
        for (int x = 0; x < this.deck.size(); x++) {
            this.deck.set(x, newDeck[x]);
        }
    }
}
