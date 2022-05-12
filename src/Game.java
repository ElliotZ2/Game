import java.util.Scanner;

public class Game {

    private Player player;
    private int daysSurvived;
    private String timeOfDay; //day, afternoon, night

    public Game() {
        player = new Player("");
        timeOfDay = "day";
    }
    public void playGame() {
        System.out.println("What is your name?:");
        String choice = "";
        Scanner input = new Scanner(System.in);
        String playerName = input.nextLine();
        playerName = playerName.trim();
        while(playerName.length() == 0) {
            System.out.println("Please enter a valid name:");
            playerName = input.nextLine();
        }
        player.setName(playerName);
        System.out.println("Welcome to the zombie apocalypse, " + player.getName() + ".");
        while(player.isAlive()) {
            Event.day(player);
        }
    }
}
