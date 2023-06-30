import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to The Game of War!");
        System.out.println("Would you like to play a single game? Or simulate 100 games? (single/simulate)");

        int temp = 0;
        while (temp == 0) {
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("single")) {
                single();
                temp++;

            } else if (choice.equalsIgnoreCase("simulate")) {
                simulate();
                temp++;

            } else {
                System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    public static void single() {

        Deck deck = new Deck();

        Player firstPlayer = new Player();
        Player secondPlayer = new Player();

        while (!deck.isEmpty()) {
            firstPlayer.dealCard(deck.draw());
            secondPlayer.dealCard(deck.draw());
        }

        int turn = 0;

        while (!firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards()) {
            turn++;

            Card firstPlayerCard = firstPlayer.playCard();
            Card secondPlayerCard = secondPlayer.playCard();

            ArrayList<Card> pile = new ArrayList<>();
            pile.add(firstPlayerCard);
            pile.add(secondPlayerCard);

            System.out.println("Player One: " + firstPlayerCard);
            System.out.println("Player Two: " + secondPlayerCard);

            int result = firstPlayerCard.compareTo(secondPlayerCard);

            while (result == 0 && !firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards()) {
                int warCardsPlayed = 0;

                while (!firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards() &&
                        warCardsPlayed < 3) {

                    pile.add(firstPlayer.playCard());
                    pile.add(secondPlayer.playCard());
                    warCardsPlayed++;
                }

                if (!firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards()) {
                    firstPlayerCard = firstPlayer.playCard();
                    secondPlayerCard = secondPlayer.playCard();

                    pile.add(firstPlayerCard);
                    pile.add(secondPlayerCard);

                    System.out.println("War!");
                    System.out.println("Player One: " + firstPlayerCard);
                    System.out.println("Player Two: " + secondPlayerCard);

                    result = firstPlayerCard.compareTo(secondPlayerCard);
                }
            }

            if (result > 0) {
                firstPlayer.winCards(pile);

                if (firstPlayer.isOutOfCards()) {
                    firstPlayer.shufflePileIntoStack();
                }

            } else if (result < 0) {
                secondPlayer.winCards(pile);

                if (secondPlayer.isOutOfCards()) {
                    secondPlayer.shufflePileIntoStack();
                }
            }
        }

        if (firstPlayer.isOutOfCards()) {
            System.out.println("\nPlayer One is out of cards. Player Two Wins!");
            System.out.println("Number of turns: " + turn);
        } else if (secondPlayer.isOutOfCards()) {
            System.out.println("\nPlayer Two is out of cards. Player One Wins!");
            System.out.println("Number of turns: " + turn);
        }
    }

    public static void simulate() {

        int turn = 0;
        int playerOneWins = 0;
        int playerTwoWins = 0;

        for (int numberOfDecks = 1; numberOfDecks <= 10; numberOfDecks++) {

            playerOneWins = 0;
            playerTwoWins = 0;

            for (int game = 0; game < 100; game++) {

                Player firstPlayer = new Player();
                Player secondPlayer = new Player();

                for (int deckCount = 0; deckCount < numberOfDecks; deckCount++) {

                    Deck deck = new Deck();

                    while (!deck.isEmpty()) {
                        firstPlayer.dealCard(deck.draw());
                        secondPlayer.dealCard(deck.draw());
                    }
                }

                int turnCount = 0;
                while (!firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards()) {
                    turnCount++;

                    Card firstPlayerCard = firstPlayer.playCard();
                    Card secondPlayerCard = secondPlayer.playCard();

                    ArrayList<Card> pile = new ArrayList<>();
                    pile.add(firstPlayerCard);
                    pile.add(secondPlayerCard);

                    int result = firstPlayerCard.compareTo(secondPlayerCard);


                    while (result == 0 && !firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards()) {
                        int warCardsPlayed = 0;

                        while (!firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards() &&
                                warCardsPlayed < 3) {

                            pile.add(firstPlayer.playCard());
                            pile.add(secondPlayer.playCard());
                            warCardsPlayed++;
                        }

                        if (!firstPlayer.isOutOfCards() && !secondPlayer.isOutOfCards()) {
                            firstPlayerCard = firstPlayer.playCard();
                            secondPlayerCard = secondPlayer.playCard();

                            pile.add(firstPlayerCard);
                            pile.add(secondPlayerCard);

                            result = firstPlayerCard.compareTo(secondPlayerCard);
                        }
                    }

                    if (result > 0) {
                        firstPlayer.winCards(pile);

                        if (firstPlayer.isOutOfCards()) {
                            firstPlayer.shufflePileIntoStack();
                        }

                    } else if (result < 0) {
                        secondPlayer.winCards(pile);

                        if (secondPlayer.isOutOfCards()) {
                            secondPlayer.shufflePileIntoStack();
                        }
                    }
                }
                turn += turnCount;

                if (firstPlayer.isOutOfCards()) {
                    playerTwoWins++;
                } else if (secondPlayer.isOutOfCards()) {
                    playerOneWins++;
                }
            }
            System.out.println("For " + numberOfDecks + " decks: ");
            System.out.println("\tAverage number of turns per game: " + turn / 100);
            System.out.println("\tPlayer One won: " + playerOneWins + " games");
            System.out.println("\tPlayer Two won: " + playerTwoWins + " games");
        }
    }
}