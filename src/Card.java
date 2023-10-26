import interfaces.ICard;

import java.util.Arrays;
import java.util.Objects;

public class Card implements ICard {
    private String suit;
    private String value;
    private boolean special;

    public Card(String suit, String value) {
        if (this.validSuit(suit) && this.validValue(value)) {
            this.suit = suit;
            this.value = value;
            this.special = this.isSpecialType(value);
        }
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public boolean isSpecial() {
        return special;
    }

    private boolean validSuit(String suit) {
        return suit.equals("Hearts") || suit.equals("Spades") || suit.equals("Clubs") || suit.equals("Diamonds");
    }

    private boolean validValue(String value) {
        try {
            if (Arrays.asList(getCardValues()).contains(value)) {
                if (value.equals("Queen") || value.equals("Jack") || value.equals("King") || value.equals("Ace")) {
                    return true;
                }
                return inRange(value);
            }
            return false;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }


    private boolean isSpecialType(String value) {
        return Arrays.asList(getCardTypes()).contains(value);
    }

    public static String[] getCardTypes() {
        return new String[]{"King", "Jack", "2", "8", "Ace"};
    }

    public static String[] getCardSuits() {
        return new String[]{"Hearts", "Spades", "Clubs", "Diamonds"};
    }

    public static String[] getCardValues() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    }

    private static boolean inRange(String value) {
        return Integer.parseInt(value) >= 1 && Integer.parseInt(value) <= 10;
    }

    // TODO: Make the Card toString() method look more like a card.
    @Override
    public String toString() {
        return value + " " + suit;
    }

    public static boolean isSameValueAndSuit(Card c1, Card c2) {
        return isSameSuit(c1, c2) && isSameValue(c1, c2);
    }

    public static boolean isSameValue(Card c1, Card c2) {
        return Objects.equals(c1.getValue(), c2.getValue());
    }

    public static boolean isSameSuit(Card c1, Card c2) {
        return Objects.equals(c1.getSuit(), c2.getSuit());
    }
}
