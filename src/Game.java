import java.util.Scanner;

//TODO implement the option to leave after each night; also keep track of players' names and how many days they survived, and always print the top 10 days
//TODO difficulty scale depending on the days; enemies get more hp and damage (damage += (damage * 0.1) days^2)

public class Game {
    private static final int SLEEP_TIME = 0;//for future thread.sleep so that i can test fast by changing SLEEP_TIME to 0
    private static final int THIRST_INCREASE = 5;
    private static final int HUNGER_INCREASE = 5;

    private Player player;
    private int daysSurvived;
    private final int DAY_THRESHOLD_FOR_WIN = 10;
    private String timeOfDay; //day, afternoon, night

    public Game() {
        player = new Player("");
        timeOfDay = "day";
        daysSurvived = 1;
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
            if(timeOfDay == "day") {
                System.out.println("DAY: " + daysSurvived);
                day();
                timeOfDay = "afternoon";
            }
            else if(timeOfDay == "afternoon") {
                afternoon();
                timeOfDay = "night";
            }
            else{
                night();
                timeOfDay = "day";
                daysSurvived++;
            }
        }
        System.out.println("YOU DIED!");
        System.out.println(player.getName() + " was able to survive for " + daysSurvived + " days.");
    }

    private void decrementPlayerStats() { //TODO finish this, make it so that if hunger or thirst is too high they lose hp
        //TODO add the infection level and new consumable type
        player.increaseHunger(HUNGER_INCREASE);
        player.increaseThirst(THIRST_INCREASE);
    }

    private void day() {
        String choice = "";
        Scanner input = new Scanner(System.in);
        String options = "Options: \"scavenge\" for loot, \"look\" for humans, \"fight\" zombies, access your \"inventory\", or check your \"stats\".";
        System.out.println("A new day has arrived.");
        System.out.println("What would you like to do for the day?");
        System.out.println(options);
        choice = input.nextLine();
        choice = choice.toLowerCase();
        while(!(choice.equals("scavenge") || choice.equals("look") || choice.equals("fight"))) {
            if(choice.equals("inventory")) {
                player.accessInventory();
                System.out.println("After accessing your inventory, what would you like to do for the day?");
            }
            else if(choice.equals("stats")) {
                System.out.println(player);
                System.out.println("After checking " + player.getName() + "\'s stats, what would you like to do for the day?");
            }
            else{
                System.out.println("Please enter a valid option.");
            }
            System.out.println(options);
            choice = input.nextLine();
        }
        if(choice.equals("fight")) {
            //System.out.println("You chose to look for a fight.");
            fight();
        }
        else if(choice.equals("look")) {
            //System.out.println("You chose to look for people.");
            lookForHumans();
        }
        else if(choice.equals("scavenge")) {
            //System.out.println("You chose to scavenge for loot.");
            scavenge(player);
        }
        else{
            System.out.println("a");
        }
        decrementPlayerStats();
    }

    private void afternoon() {
        //player can choose to hunt for food, find water, search for materials to make bandages/healing herbs, there's a chance a fight will happen no matter what
        String choice = "";
        Scanner input = new Scanner(System.in);
        String options = "Options: \"hunt\" for food, find a source of \"water\", \"search\" for materials to make healing items, access your \"inventory\", or check your \"stats\".";
        System.out.println("The afternoon has arrived.");
        System.out.println("What would you like to do for the afternoon?");
        System.out.println(options);
        choice = input.nextLine();
        choice = choice.toLowerCase();
        while(!(choice.equals("hunt") || choice.equals("water") || choice.equals("search"))) {
            if(choice.equals("inventory")) {
                player.accessInventory();
                System.out.println("After accessing your inventory, what would you like to do for the afternoon?");
            }
            else if(choice.equals("stats")) {
                System.out.println(player);
                System.out.println("After checking " + player.getName() + "\'s stats, what would you like to do for the afternoon?");
            }
            else{
                System.out.println("Please enter a valid option.");
            }
            System.out.println(options);
            choice = input.nextLine();
        }
        double random = Math.random();
        if(choice.equals("hunt")) {
            if(random < 0.3) {
                System.out.println("While you were looking for a place to hunt, you got ambushed by an enemy!");
                battle(Enemy.generateRandomMedEnemy());
            }
            else{
                Consumable c = Consumable.generateRandomHuntedFood();
                System.out.println("You hunted for the afternoon and managed to hunt and cook some " + c.getName());
                player.addToInventory(c);
            }
        }
        else if(choice.equals("water")) {
            if(random < 0.3) {
                System.out.println("While you were heading to a nearby river, you got ambushed by an enemy!");
                battle(Enemy.generateRandomMedEnemy());
            }
            else{
                System.out.println("You managed to find some clean water and put it in an empty bottle you found.");
                player.addToInventory(new Consumable("bottled water", "drink", 40));
            }
        }
        else if(choice.equals("search")) {
            if(random < 0.3) {
                System.out.println("While you were looking for herbs, you got ambushed by an enemy!");
                battle(Enemy.generateRandomMedEnemy());
            }
            else{
                if(Math.random() < 0.5) {
                    System.out.println("You managed to find some cloth and make a bandage.");
                    player.addToInventory(new Consumable("bandages", "healing", 15));
                }
                else{
                    System.out.println("You found some medicinal herbs."); {
                        player.addToInventory(new Consumable("medicinal herbs", "healing", 10));
                    }
                }
            }
        }
        decrementPlayerStats();
    }

    private void night() {
        //player can choose to sleep to regain health, but they have a chance to get ambushed
        //they can also choose to "guard" and fortify their base to prevent a zombie attack or player raid
        //they can also choose to "enhance" their weapon to increase its base attack up to a certain point (5 sharpens) but it has a 10% chance to break when sharpening
        String choice = "";
        Scanner input = new Scanner(System.in);
        String options = "Options: \"sleep\" and regain health, \"guard\" and fortify your base to prevent an attack, \"enhance\" your currently equipped weapon, access your \"inventory\", or check your \"stats\".";
        System.out.println("Night has fallen.");
        System.out.println("What would you like to do for the night?");
        System.out.println(options);
        choice = input.nextLine();
        choice = choice.toLowerCase();
        while(!(choice.equals("sleep") || choice.equals("guard") || choice.equals("enhance"))) {
            if(choice.equals("inventory")) {
                player.accessInventory();
                System.out.println("After accessing your inventory, what would you like to do for the night?");
            }
            else if(choice.equals("stats")) {
                System.out.println(player);
                System.out.println("After checking " + player.getName() + "\'s stats, what would you like to do for the night?");
            }
            else{
                System.out.println("Please enter a valid option.");
            }
            System.out.println(options);
            choice = input.nextLine();
            choice = choice.toLowerCase();
        }
        if(choice.equals("sleep")) {
            if(Math.random() < 0.75) {
                int previousHealth = player.getHealth();
                player.heal(20);
                int restoredHealth = player.getHealth() - previousHealth;
                System.out.println("You were able to sleep and restore " + restoredHealth + " health.");
            }
            else{
                String[] thingsHeard = {"your door break open", "footsteps getting closer"};
                String thingHeard = thingsHeard[(int) (Math.random() * thingsHeard.length)];
                System.out.println("As soon as you try to go to sleep, you suddenly hear " + thingHeard + ".");
                Enemy e = Enemy.generateRandomEnemy(0.2,0.3,0.5);
                System.out.println("You prepare yourself to defend your home against a " + e.getName());
                battle(e);
            }
        }
        else if(choice.equals("guard")) {
            int previousHealth = player.getHealth();
            player.heal(5);
            int restoredHealth = player.getHealth() - previousHealth;
            System.out.println("You were guard your house whilst resting and restoring " + restoredHealth + " health.");
        }
        else {//enhancing weapon
            System.out.println("You place your " + player.getEquippedWeapon().getName() + " on a workbench and attempt to enhance it.");
            player.getEquippedWeapon().enhance();
        }
    }

    private void scavenge(Player player) {
        if(Math.random() > 0.75) {
            System.out.println("You went looking for loot, but ended up getting ambushed!");
            fight();
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
            System.out.println("You were able to find a " + c.getName());
            player.addToInventory(c);
        }
        else if(random < 0.75) {//found some drink
            Consumable c = Consumable.generateRandomDrink();
            System.out.println("You were able to find a " + c.getName());
            player.addToInventory(c);
        }
        else{//found some healing
            Consumable c = Consumable.generateRandomHealing();
            System.out.println("You were able to find a " + c.getName());
            player.addToInventory(c);
        }
    }

    private void fight() {
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
        battle(enemy);
        if(player.getHealth() > 0) {
            Item i = enemy.dropItem();
            System.out.println("The " + enemy.getName() + " dropped a " + i.getName() + ".");
            player.addToInventory(i);
        }

    }

    private void battle(Enemy enemy) {
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

    private void lookForHumans() {
        /*possibilities for looking for humans:
        -you meet a merchant and they offer a trade
        -after a certain day threshold, you can meet certain people that ends the game and is considered a win:endings:bandit,military, government, lone wolf (neutral one where you find a ship or something and live out the rest of your life in safety)
        -meeting bad guys that you have to fight
        -meeting struggling survivors that you have the option to give something or steal something, doing so may unlock certain endings or have rewards
         */
        String choice = "";
        Scanner input = new Scanner(System.in);
        double random = Math.random();
        if(random < 0.3) {
            String[] modesOFTransport = {"bike", "camel", "horse", "wagon", "unicycle", "boat", "ship"};
            String modeOfTransport = modesOFTransport[(int) (Math.random() * modesOFTransport.length)];
            System.out.println("You meet a strange merchant on a " + modeOfTransport + "who seems willing to offer you something for a price.");
            Consumable c = Consumable.generateRandomConsumable();
            if(Math.random() < 0.65) {
                //item to item
            }
            else{
                String[] actions = {"sing", "dance", "do a flip", "break-dance", "show a magic trick", "do a back flip"};
                String action = actions[(int) (Math.random() * actions.length)];
                System.out.println("The merchant is willing to give you a " + c.getName() + " if you agree to " + action + ".");
                System.out.println("Are you willing to " + action + " for a " + c.getName() + "?:");
                choice = input.nextLine();
                if(choice.length() > 0 && choice.toLowerCase().substring(0,1).equals("y")) {
                    if(Math.random() > 0.5) {
                        System.out.println("You put on a spectacular performance, and the merchant rewarded you with a " + c.getName() + " for your display.");
                        player.addToInventory(c);
                    }
                    else{
                        System.out.println("You tried your best to " + action + ", but ended up hurting yourself and failing.");
                        System.out.println("The merchant rides away dissatisfied with your performance, and you end up leaving with nothing gained.");
                        player.takeDamage(10);
                    }
                }
                else{
                    System.out.println("You decide not to mess with sketchy merchants and walk away.");
                }
            }
        }
        else if(random < 0.8) {
            System.out.println("You encounter a sketchy person armed with weapons.");
            System.out.println("You prepare yourself for a battle.");
            battle(Enemy.generateRandomHumanEnemy());
        }
        else{
            System.out.println("You come across a group of struggling survivors; they haven't noticed your presence yet.");
            System.out.println("Options: \"steal\" from them, \"give\" them something to help, or \"leave\" them be.");
            System.out.println("What do you want to do?");
            choice = input.nextLine();
            while(!(choice.equals("steal") || choice.equals("give") || choice.equals("leave"))) {
                System.out.println("Please enter a valid choice.");
                System.out.println("Options: \"steal\" from them, \"give\" them something to help, or \"leave\" them be.");
                choice = input.nextLine();
            }
            if(choice.equals("steal")) {
                Consumable c = Consumable.generateRandomConsumable();
                System.out.println("You swiftly snatch a random box that says \"" + c.getName() + "\" for yourself and dash away.");
            }
            else if(choice.equals("give")) {
                if(player.getInventory().size() == 0) {
                    System.out.println("You walk up to the survivors and offer them fresh air because your inventory is empty.");
                    System.out.println("The survivors walk away unamused.");
                }
                else{
                    System.out.println("What item are you willing to offer?");
                    player.printInventory();
                    System.out.println("Type the index of the item you're giving away:");
                    choice = input.nextLine();
                    boolean isNumeric = false;
                    while(!choice.toLowerCase().equals("quit") && (isNumeric == false || Integer.parseInt(choice) < 0 || Integer.parseInt(choice) >= player.getInventory().size())) {
                        isNumeric = true;
                        for(int i = 0; i < choice.length(); i++) {
                            if(! Character.isDigit(choice.charAt(i))) {
                                isNumeric = false;
                            }
                        }
                        if(isNumeric == false) {
                            System.out.println("Please enter a valid index, or type \"quit\" to give up on giving an item:");
                            choice = input.nextLine();
                        }
                    }
                    if(choice.toLowerCase().equals("quit")) {
                        System.out.println("You decide that your items are too valuable to be given away to others, so you leave those survivors to struggle on their own.");
                    }
                    int index = Integer.parseInt(choice);
                    System.out.println("You walk up to the survivors and offer them your " + player.getInventory().remove(index).getName() +".");
                    System.out.println("They thank you for your generosity and leave you after some thoughtful goodbyes.");
                }
            }
        }
    }
}
