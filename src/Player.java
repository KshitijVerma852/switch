import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class Player {
    private final ArrayList<Card> hand = new ArrayList<Card>();
    private final boolean controlledByUser;
    private String name;

    public Player(Player[] otherPlayers, boolean controlledByUser, String name) {
        this.controlledByUser = controlledByUser;
        this.name = name;
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

                if (Card.isSameValueAndSuit(card, newCard)) {
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

    public boolean isControlledByUser() {
        return this.controlledByUser;
    }

    public static Card[] pickUpCards(NonPlayingStack nonPlayingStack, int noOfCardsToPickUp) {
        Card[] ans = new Card[noOfCardsToPickUp];
        for (int x = 0; x < noOfCardsToPickUp; x++) {
            ans[x] = nonPlayingStack.pop();
        }
        return ans;
    }

    public void addCardsToHand(Card[] cardsToAdd) {
        Collections.addAll(hand, cardsToAdd);
    }

    public String getName() {
        return this.name;
    }
}
