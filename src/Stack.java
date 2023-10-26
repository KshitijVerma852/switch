import interfaces.IStack;

import java.util.ArrayList;

public class Stack implements IStack {
    protected final ArrayList<Card> deck = new ArrayList<Card>();

    public Stack() {

    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
