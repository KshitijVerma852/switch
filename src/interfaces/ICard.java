package interfaces;

public interface ICard {
    String suit = null;
    String value = null;
    boolean special = false;

    String getSuit();
    String getValue();
    boolean isSpecial();
}
