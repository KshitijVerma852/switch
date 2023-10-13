import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Player {
    private final ArrayList<Card> hand = new ArrayList<Card>();

    public Player(Player[] otherPlayers) {
        autoGen(otherPlayers);
    }

    // TODO: make this more DRY
    public void autoGen(Player[] otherPlayers) {
        String[] cardSuits = Card.getCardSuits();
        String[] cardValues = Card.getCardValues();

        int temp = 0;
        while (temp < 5) {
            String randomCardSuit = cardSuits[(new Random()).nextInt(cardSuits.length)];
            String randomCardValue = cardValues[(new Random()).nextInt(cardValues.length)];

            Card newCard = new Card(randomCardSuit, randomCardValue);
            boolean found = false;

            for (Card card : hand) {
                if (card == null) {
                    break;
                }

                if (Card.equals(card, newCard)) {
                    found = true;
                }
            }

            for (Player player : otherPlayers) {
                if (player != null) {
                    ArrayList<Card> otherPlayerHand = player.seeHand();
                    for (Card otherPlayerCard : otherPlayerHand) {
                        if (Objects.equals(otherPlayerCard.getSuit(), newCard.getSuit())
                                && Objects.equals(otherPlayerCard.getValue(), newCard.getValue())) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
            }
            if (!found) {
                hand.add(newCard);
                temp++;
            }
        }
    }

    public ArrayList<Card> seeHand() {
        return hand;
    }
}
