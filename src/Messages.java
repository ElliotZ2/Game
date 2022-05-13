import java.util.ArrayList;

public class Messages {
    public static void startOfDay() {
        String[] messages = {"A new day has arrived."};
        System.out.println(selectRandomMessage(messages));
    }

    public static void dayChoices() {
        System.out.println("What would you like to do for the day?");
        System.out.println("Options: \"scavenge\" for loot, \"look\" for humans, \"fight\" zombies, or access your \"inventory\".");
    }

    private static String selectRandomMessage(String[] array) {
        if(array.length == 0) {
            System.out.println("error: cannot select random message from empty array");
            return "";
        }
        int random = (int) (Math.random() * array.length);
        return array[random];
    }
}
