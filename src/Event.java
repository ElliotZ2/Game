import java.util.Scanner;

public class Event {
    private static final int SLEEP_TIME = 0;//for future thread.sleep so that i can test fast by changing SLEEP_TIME to 0
    private static final int THIRST_INCREASE = 5;
    private static final int HUNGER_INCREASE = 5;


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

    public static void decrementPlayerStats(Player player) {
        player.increaseHunger(HUNGER_INCREASE);
        player.increaseThirst(THIRST_INCREASE);
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
        if(Math.random() > 0.75) {
            System.out.println("You went looking for loot, but ended up getting ambushed!");
            fight(player);
            return;
        }

        //randomize to figure out what item will be generated ex: 0.3 and below for a weapon, between 0.3 and 0.5 for food
        double random = Math.random();
        if(random < 0.25) {//found a weapon
            Weapon w = Weapon.generateRandomLowTierWeapon();
            System.out.println("You were able to find a " + w.getName());
            player.addToInventory(w);
        }
        else if(random < 0.5) {//found some food
            Consumable c = Consumable.generateRandomFood();
            System.out.println("You were able to find a" + c.getName());
            player.addToInventory(c);
        }
        else if(random < 0.75) {//found some drink
            Consumable c = Consumable.generateRandomDrink();
            System.out.println("You were able to find a" + c.getName());
            player.addToInventory(c);
        }
        else{//found some healing
            Consumable c = Consumable.generateRandomHealing();
            System.out.println("You were able to find a" + c.getName());
            player.addToInventory(c);
        }
    }

    public static void fight(Player player) {
        //create enemy class?
    }
}
