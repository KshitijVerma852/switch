import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class NonPlayingStack {
    private final ArrayList<Card> deck = new ArrayList<Card>();

    public NonPlayingStack(Player[] players) {
        autoGen(players);
    }

    // TODO: make this more DRY
    private void autoGen(Player[] otherPlayers) {
        int temp = 0;
        while (temp < (52 - otherPlayers.length * 5)) {
            String[] cardSuits = Card.getCardSuits();
            String[] cardValues = Card.getCardValues();

            String randomCardSuit = cardSuits[(new Random()).nextInt(cardSuits.length)];
            String randomCardValue = cardValues[(new Random()).nextInt(cardValues.length)];
            Card newCard = new Card(randomCardSuit, randomCardValue);

            boolean found = false;
            for (Player otherPlayer : otherPlayers) {
                for (Card otherCard : otherPlayer.seeHand()) {
                    if (Objects.equals(otherCard.getSuit(), newCard.getSuit())
                            && Objects.equals(otherCard.getValue(), newCard.getValue())) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                deck.add(newCard);
                temp++;
            }
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
