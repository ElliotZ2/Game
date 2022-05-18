import java.util.Scanner;

public class Event {
    private static final int SLEEP_TIME = 0;//for future thread.sleep so that i can test fast by changing SLEEP_TIME to 0
    private static final int THIRST_INCREASE = 5;
    private static final int HUNGER_INCREASE = 5;

    //TODO move all this to the game class

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
            System.out.println("You chose to look for a fight.");
            fight(player);
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
        //the player can choose to try and avoid the fight, but they may be unsuccessful, and they will also gain hunger and thirst
        double random = Math.random();
        Enemy enemy;
        String endPunc = ".";
        String choice = "";
        Scanner input = new Scanner(System.in);
        if(random < 0.5) {
            enemy = Enemy.generateRandomBasicEnemy();
        }
        else if(random < 0.8) {
            enemy = Enemy.generateRandomMedEnemy();
        }
        else{
            enemy = Enemy.generateRandomAdvancedEnemy();
            endPunc = "!";
        }
        System.out.println("You encountered a " + enemy.getName() + endPunc);
        System.out.println("Are you going try and avoid battle with the " + enemy.getName() + "?");
        choice = input.nextLine();
        if(choice.length() > 0 && choice.substring(0,1).toLowerCase().equals("y")) {
            player.increaseThirst(10);
            player.increaseHunger(10);
            if(Math.random() < 0.7) {
                System.out.println("You were able to avoid a battle with the " + enemy.getName() + ".");
                System.out.println("You gained 10 hunger and 10 thirst while you were running away.");
                return;
            }
            else{
                System.out.println("You gained 10 hunger and 10 thirst while trying to run away.");
                System.out.println("You were unable to escape the " + enemy.getName() + ".");
            }
        }
        battle(player,enemy);
        if(player.getHealth() > 0) {
            Item i = enemy.dropItem();
            System.out.println("The " + enemy.getName() + " dropped a " + i.getName() + ".");
            player.addToInventory(i);
        }

    }

    public static void battle(Player player, Enemy enemy) {
        boolean playerTurn = true;
        while(player.getHealth() > 0 && enemy.getHealth() > 0) {
            if(playerTurn) {
                int damage = player.attack();
                enemy.takeDamage(damage);
                System.out.println(player.getName() + " attacked the " + enemy.getName() + " for " + damage + " damage " + " with their " + player.getEquippedWeapon().getName() + ".");
                System.out.println("The " + enemy.getName() + " now has " + enemy.getHealth() + " health.");
                System.out.println();
            }
            else{
                int damage = enemy.attack();
                if(damage == 0) {
                    System.out.println("The " + enemy.getName() + "\'s attack missed.");
                }
                else{
                    player.takeDamage(damage);
                    System.out.println("The " + enemy.getName() + " attacked " + player.getName() + " for " + damage + " damage.");
                    System.out.println(player.getName() + " now has " + player.getHealth() + " health.");
                    /*
                    for adding the infection thing later
                    if(enemy.getName().contains("zombie")) {
                        infect the player
                    }*/
                }
                System.out.println();
            }
            playerTurn = !playerTurn;
        }
        if(player.getHealth() > 0) {
            System.out.println(player.getName() + " managed to survive against the " + enemy.getName() + ".");
        }
        else{
            System.out.println("The " + enemy.getName() + " has slain " + player.getName());
        }
    }

    public static void lookForHumans(Player player) {
        /*possibilities for looking for humans:
        -you meet a merchant and they offer a trade
        -after a certain day threshold, you can meet certain people that ends the game and is considered a win: bandits, military,
        -meeting bad guys that you have to fight
        -meeting struggling survivors that you have the option to give something or steal something, doing so may unlock certain endings or have rewards
         */
        double random = Math.random();
        if(random < 0.4) {

        }
        else if(random < 0.8) {

        }
        else{

        }
    }
}
