import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Card {
    private String suit;
    private String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public double getValue() {
        if (value.equals("J") || value.equals("Q") || value.equals("K")) {
            return 0.5;
        } else if (value.equals("A")) {
            return 1;
        } else {
            return Integer.parseInt(value);
        }
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}

class DeckCards {
    private List<Card> cards;

    public DeckCards() {
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"1", "2", "3", "4", "5", "6", "7", "J", "Q", "K"};

        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public void shuffleDeck() {
        // Use Collections.shuffle to shuffle the list
        java.util.Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        } else {
            return null; // Deck is empty
        }
    }

    public int cardsLeft() {
        return cards.size();
    }
}

class StackCards {
    private List<Card> cards;

    public StackCards() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void shuffle() {
        // Use Collections.shuffle to shuffle the list
        java.util.Collections.shuffle(cards);
    }

    public int countCards() {
        return cards.size();
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public Card removeRandomCard() {
        if (!cards.isEmpty()) {
            int index = (int) (Math.random() * cards.size());
            return cards.remove(index);
        } else {
            System.out.println("The stack of cards is empty, cannot remove a card.");
            return null;
        }
    }
    public boolean isBust() {
        return calculateTotal() > 7.5;
    }
    public double calculateTotal() {
        double total = 0.0;
        for (Card card : cards) {
            total += card.getValue();
        }
        return total;
    }

}

class GameSevenHalf {
    private DeckCards deck;
    private StackCards playerHand;
    private StackCards dealerHand;

    public GameSevenHalf() {
        deck = new DeckCards();
        playerHand = new StackCards();
        dealerHand = new StackCards();
    }

    public void startGame() {
        System.out.println("Welcome to 7 and a half!");

        // Initial deal
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        displayHands();

        // Player's turn
        playerTurn();

        // Dealer's turn
        dealerTurn();

        // Determine winner
        determineWinner();
    }

    private void displayHands() {
        System.out.println("Your hand: " + playerHand);
        System.out.println("Dealer's hand: " + dealerHand);
    }

    private void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to (H)it or (S)tand?");
            String choice = scanner.nextLine().toUpperCase();
            if ("H".equals(choice)) {
                Card card = deck.dealCard();
                playerHand.addCard(card);
                System.out.println("You drew: " + card);
                displayHands();
                if (playerHand.isBust()) {
                    System.out.println("Bust! You lose.");
                    return;
                }
            } else if ("S".equals(choice)) {
                System.out.println("You chose to stand.");
                return;
            } else {
                System.out.println("Invalid choice. Please enter 'H' or 'S'.");
            }
        }
    }

    private void dealerTurn() {
        System.out.println("Dealer's turn...");
        while (dealerHand.calculateTotal() < 7.0) {
            Card card = deck.dealCard();
            dealerHand.addCard(card);
            System.out.println("Dealer drew: " + card);
            displayHands();
        }
        if (dealerHand.isBust()) {
            System.out.println("Dealer bust! You win.");
        }
    }

    private void determineWinner() {
        double playerTotal = playerHand.calculateTotal();
        double dealerTotal = dealerHand.calculateTotal();

        System.out.println("Your total: " + playerTotal);
        System.out.println("Dealer's total: " + dealerTotal);

        if (playerTotal > dealerTotal && !playerHand.isBust()) {
            System.out.println("You win!");
        } else if (dealerTotal > 7.5) {
            System.out.println("Dealer bust! You win.");
        } else {
            System.out.println("Dealer wins.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        GameSevenHalf game = new GameSevenHalf();
        game.startGame();
    }
}
