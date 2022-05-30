import java.util.Scanner;

public class ZombieGameRunner {
    public static void main(String[] args) {
        ZombieGame.load(); //prints out data for previous survival runs
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\nWhat is your name?:");
        String choice = "";
        Scanner input = new Scanner(System.in);
        String playerName = input.nextLine();
        playerName = playerName.trim();
        while(playerName.length() == 0) {
            System.out.println("Please enter a valid name:");
            playerName = input.nextLine();
        }
        ZombieGame zombieGame = new ZombieGame(playerName);//creates a new zombieGame instance using the player's name
        zombieGame.playGame();
    }
}
