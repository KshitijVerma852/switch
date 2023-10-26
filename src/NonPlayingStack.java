import interfaces.IStack;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

// TODO: Make an interface for this class.
public class NonPlayingStack extends Stack {
    public NonPlayingStack(Player[] players) {
        super();
        autoGen(players);
    }

    // TODO: make this more DRY
    private void autoGen(Player[] otherPlayers) {
        int temp = 0;
        while (temp < (52 - otherPlayers.length * 5)) {
            final String[] cardSuits = Card.getCardSuits();
            final String[] cardValues = Card.getCardValues();
            final Random random = new Random();

            String randomCardSuit = cardSuits[random.nextInt(cardSuits.length)];
            String randomCardValue = cardValues[random.nextInt(cardValues.length)];
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

    public Card pop() {
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }
}
