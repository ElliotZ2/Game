import java.util.Scanner;

//TODO implement the option to leave after each night; also keep track of players' names and how many days they survived, and always print the top 10 days
//TODO difficulty scale depending on the days

public class Game {

    private Player player;
    private int daysSurvived;
    private final int DAY_THRESHOLD_FOR_WIN = 10;
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
        while(player.getHealth() > 0) {
            Event.day(player);
        }
    }
}
