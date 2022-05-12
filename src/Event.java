import java.util.Scanner;

public class Event {
    public static void testEvent(Player player) {
        player.increaseHunger(40);
        Item item = new Consumable("chicken", "food", 35);
        player.addToInventory(item);
        player.getItemAtIndex(0);
        System.out.println(player.getHunger());
        player.printInventory();
        player.consumeItem(0);
        System.out.println(player.getHunger());
        player.printInventory();

    }

    public static void day(Player player) {
        String choice = "";
        Scanner input = new Scanner(System.in);
        Messages.startOfDay();
        Messages.dayChoices();
        choice = input.nextLine();
        if(choice.equals("inventory")) {
            player.accessInventory();
            System.out.println("After accessing your inventory, what would you like to do for the day?");
            System.out.println("Options: \"scavenge\" for loot, \"look\" for humans, or \"fight\" zombies.");
            choice = input.nextLine();
        }
        while(!((choice.equals("fight") || choice.equals("look") || choice.equals("scavenge")))) {
            System.out.println("Please enter a valid option.");
            System.out.println("Options: \"scavenge\" for loot, \"look\" for humans, or \"fight\" zombies.");
            choice = input.nextLine();
        }
        if(choice.equals("fight")) {
            System.out.println("fight");
        }
        else if(choice.equals("look")) {
            System.out.println("look");
        }
        else if(choice.equals("scavenge")) {
            System.out.println("You chose to scavenge for loot.");
            scavenge(player);
        }
        else{
            System.out.println("a");
        }
    }

    public static void scavenge(Player player) {
        if(Math.random() > 0.99) {
            System.out.println("You went looking for loot, but ended up getting ambushed!");
            fight(player);
            return;
        }

        //randomize to figure out what item will be generated ex: 0.3 and below for a weapon, between 0.3 and 0.5 for food
        double random = Math.random();
        Weapon w = Weapon.generateRandomWeapon();
        System.out.println("You were able to find a " + w.getName());
        player.addToInventory(w);
    }

    public static void fight(Player player) {

    }
}
