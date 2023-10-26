import interfaces.IPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    int noOfPlayers = 0;
    Player[] players = new Player[noOfPlayers];
    int turnNumber = 0;
    boolean gameOver = false;
    NonPlayingStack nonPlayingStack;
    PlayingStack playingStack;
    boolean pickUpTwoCards = false;
    boolean missTurn = false;


    public void startGame() {
        getAndCreatePlayersFromUserInput();
        startTurns();
    }

    private void getAndCreatePlayersFromUserInput() {
        boolean validInput = false;
        String name;
        do {
            System.out.println("What's your player's name? ");
            name = scanner.nextLine();
            System.out.println("How many other players do you want to play against? (1 - 4) ");
            try {
                String temp = scanner.nextLine();
                noOfPlayers = Integer.parseInt(temp) + 1;
                if (noOfPlayers >= 1 && noOfPlayers <= 4) {
                    players = new Player[noOfPlayers];
                    validInput = true;
                } else {
                    System.out.println("Invalid number of players. ");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid. Select a number from 1 - 4.");
            }
        } while (!validInput);

        for (int x = 0; x < noOfPlayers; x++) {
            players[x] = x == 0 // TODO: Refactor to use AIPlayer class instead of normal Player.
                    ? new Player(players, true, name)
                    : new Player(players, false, ("Player " + (x + 1)));
        }
        printAllPlayersNames();
    }

    private void printAllPlayersNames() {
        System.out.println("-------------------");
        for (Player p : players) {
            System.out.println(p.getName());
        }
        System.out.println("-------------------");
    }

    private void initializePlayingAndNonPlayingStacks() {
        nonPlayingStack = new NonPlayingStack(players);
        playingStack = new PlayingStack();
    }

    // TODO: Delete this method before submission.
    private void setPlayingAndNonPlayingStacks____DELETEME() {
        playingStack.setDeck(new Card[]{
                new Card("Hearts", "4")
        });
    }

    private void startTurns() {
        initializePlayingAndNonPlayingStacks();
        while (!gameOver) {
            for (Player player : players) {
                if (missTurn) {
                  continue;
                } else if (pickUpTwoCards) {
                    Card[] cards = Player.pickUpCards(nonPlayingStack, 2);
                    player.addCardsToHand(cards);
                } else {
                    Card lastCard = playingStack.getLastCard();
                    ArrayList<Card> playerHand = player.seeHand();

                    if (player.isControlledByUser()) {
                        // TODO: Make card selecting process better. Make the cards look like actual cards.
                        showPlayerHandToUser(playerHand);

                        if (lastCard == null) {
                            lastCard = nonPlayingStack.pop();
                            playingStack.addCard(lastCard);
                        }

                        System.out.println("The current card is " + lastCard);

                        boolean isTurnOver = false;

                        System.out.println("Your turn...");
                        System.out.println("Enter 'w' to see the last card that was played or 'e' to see your hand.");

                        do {
                            System.out.printf(">> ");
                            String input = scanner.nextLine();


                            if (Objects.equals(input, "w")) {
                                System.out.println(lastCard);
                            } else if (Objects.equals(input, "e")) {
                                showPlayerHandToUser(playerHand);
                            } else {
                                try {
                                    int cardSelectedIndex = Integer.parseInt(input);
                                    if (cardSelectedIndex >= 1 && cardSelectedIndex < playerHand.size()) {
                                        Card selectedCard = playerHand.get(cardSelectedIndex - 1);

                                        if (isCardPlayable(lastCard, selectedCard)) {
                                            if (selectedCard.isSpecial()) {
                                                if (Objects.equals(selectedCard.getValue(), "2")) {
                                                    pickUpTwoCards = true;
                                                } else if (Objects.equals(selectedCard.getValue(), "8")) {
                                                    missTurn = true;
                                                } else if (Objects.equals(selectedCard.getValue(), "King")) {
                                                    Collections.reverse(playerHand);
                                                }
                                            }
                                            playCard(selectedCard, playerHand, cardSelectedIndex);
                                        } else {
                                            // TODO: do something here.
                                            // TODO: If the card selected isn't playable. Blunder
                                            System.out.println("You have blundered");
                                            player.addCardsToHand(Player.pickUpCards(nonPlayingStack, 1));

                                        }
                                        isTurnOver = true;
                                    }
                                } catch (NumberFormatException err) {
                                    // TODO: Make this work.
                                    System.out.println("Invalid");
                                }
                            }

                        } while (!isTurnOver);

                    } else {
                        // TODO: Find out how to get the AI players to work.
                        // Refactor AI player logic from here to it's own class.

                        for (int x = 0; x < playerHand.size(); x++) {
                            Card selectedCard = playerHand.get(x);
                            if (isCardPlayable(lastCard, selectedCard)) {
                                System.out.println("player is playing " + selectedCard);
                                System.out.println(players.length);
                                playCard(selectedCard, playerHand, x);
                            }
                        }

                    }
                }
                // TODO: only end the turn when the turn is over.
                turnNumber++;
//                gameOver = true;
            }
        }
    }

    private static void showPlayerHandToUser(ArrayList<Card> playerHand) {
        System.out.println("Your hand: ");
        for (int x = 0; x < playerHand.size(); x++) {
            System.out.println((x + 1) + ") " + playerHand.get(x).toString());
        }
        System.out.println("-------------------");
    }

    private void playCard(Card selectedCard, ArrayList<Card> playerHand, int cardSelectedIndex) {
        playingStack.addCard(selectedCard);
        playerHand.remove(cardSelectedIndex);
    }

    private boolean isCardPlayable(Card lastCard, Card cardToPlay) {
        if (Objects.equals(cardToPlay.getValue(), "Ace") || Objects.equals(cardToPlay.getValue(), "Jack")) {
            return true;
        } else return Card.isSameValue(lastCard, cardToPlay) || Card.isSameSuit(lastCard, cardToPlay);
    }
}
