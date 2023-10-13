import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int noOfPlayers = 0;
        Player[] players = new Player[noOfPlayers];

        do {
            System.out.println("How many other players do you want to play against? (1 - 4) ");
            try {
                String temp = scanner.nextLine();
//                String temp = "3";
                noOfPlayers = Integer.parseInt(temp);
                if (noOfPlayers >= 1 && noOfPlayers <= 4) {
                    players = new Player[noOfPlayers];
                    validInput = true;
                } else {
                    System.out.println("Invalid number of players. ");
                }
            } catch (NumberFormatException ignored) {}
        } while (!validInput);

        for (int x = 0; x < noOfPlayers; x++) {
            players[x] = new Player(players);
        }
        NonPlayingStack nonPlayingStack = new NonPlayingStack(players);


    }
}