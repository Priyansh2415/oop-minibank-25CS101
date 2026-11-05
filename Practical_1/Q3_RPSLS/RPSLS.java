import java.util.Random;
import java.util.Scanner;

public class RPSLS {

    // (a) The five moves
    enum Move { ROCK, PAPER, SCISSORS, LIZARD, SPOCK }

    // (b) 1 if a beats b, -1 if b beats a, 0 for a tie
    static int winner(Move a, Move b) {
        if (a == b) {
            return 0;
        }
        boolean aWins = switch (a) {
            case SCISSORS -> b == Move.PAPER  || b == Move.LIZARD;   // cuts Paper, decapitates Lizard
            case PAPER    -> b == Move.ROCK   || b == Move.SPOCK;    // covers Rock, disproves Spock
            case ROCK     -> b == Move.LIZARD || b == Move.SCISSORS; // crushes Lizard & Scissors
            case LIZARD   -> b == Move.SPOCK  || b == Move.PAPER;    // poisons Spock, eats Paper
            case SPOCK    -> b == Move.SCISSORS || b == Move.ROCK;   // smashes Scissors, vaporizes Rock
        };
        return aWins ? 1 : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();
        Move[] moves = Move.values();
        int player = 0, computer = 0, draws = 0;

        // (c) play 5 rounds
        for (int round = 1; round <= 5; round++) {
            Move you;
            while (true) {
                System.out.print("Round " + round + " - your move " + java.util.Arrays.toString(moves) + ": ");
                try {
                    you = Move.valueOf(sc.next().trim().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid move, try again.");
                }
            }
            Move cpu = moves[rnd.nextInt(moves.length)];
            int result = winner(you, cpu);
            String text;
            if (result > 0)      { player++;   text = "You win this round"; }
            else if (result < 0) { computer++; text = "Computer wins this round"; }
            else                 { draws++;    text = "Tie"; }
            System.out.println("  You: " + you + " | Computer: " + cpu + " -> " + text);
        }

        // (d) overall winner
        System.out.println("-----------------------------------");
        String suffix = draws > 0 ? " (" + draws + " tie" + (draws > 1 ? "s" : "") + ")" : "";
        if (player > computer)      System.out.println("You win " + player + "\u2013" + computer + suffix);
        else if (computer > player) System.out.println("Computer wins " + computer + "\u2013" + player + suffix);
        else                        System.out.println("Overall draw " + player + "\u2013" + computer + suffix);
    }
}
