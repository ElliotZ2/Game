import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class ZombieGame {
    private static final int THIRST_INCREASE = 10;
    private static final int HUNGER_INCREASE = 8;

    private Player player;
    private int daysSurvived;
    private final int DAY_THRESHOLD_FOR_WIN = 7;
    private String timeOfDay; //day, afternoon, night
    private boolean gameWon;
    private ZombieGameGUI gui;

    public ZombieGame(String playerName) {
        player = new Player(playerName);
        timeOfDay = "day";
        daysSurvived = 1;
        gameWon = false;
        gui = new ZombieGameGUI(player);
    }
    public void playGame() {
        String choice = "";
        Scanner input = new Scanner(System.in);
        gui.run();
        System.out.println("Welcome to the zombie apocalypse, " + player.getName() + ".");
        double winPercentage = 0.0;
        int standing = 0;
        while(player.getHealth() > 0) {
            gui.setTime(timeOfDay + " " + daysSurvived);
            if(timeOfDay.equals("day")) {
                System.out.println("DAY: " + daysSurvived);
                if(daysSurvived == DAY_THRESHOLD_FOR_WIN) {
                    winPercentage = 1.0;
                }
                else if(daysSurvived > DAY_THRESHOLD_FOR_WIN) {
                    winPercentage += 0.1;
                }
                if(Math.random() < winPercentage) {
                    standing = player.getNumOfTimesGiven() - player.getNumOftimesStolen();
                    if(standing == 0) {
                        System.out.println("During the day, you stumble across a huge boat near the ocean.");
                        System.out.println("It looks a little damaged, but definitely fixable.");
                        System.out.println("Do you want to fix it? (ENDS GAME)");
                    }
                    else if(standing > 0) {
                        System.out.println("During the day, you meet a coalition of people with military uniforms.");
                        System.out.println("\"Hello, we\'re a group of survivors headed by the remnants of the US government.\"");
                        System.out.println("\"We\'re looking to restore order and peace in the world, which is why we\'ll need virtuous people like you to help us.\"");
                        System.out.println("\"Whad'ya think? Are you willing to join us for the greater good?\"");
                        System.out.println("Do you want to join them? (ENDS GAME)");
                    }
                    else if(standing < 0) {
                        System.out.println("During the day, you meet a group of bandits near the entrance of a cave.");
                        System.out.println("\"We've seen your plundering around this area.\"");
                        System.out.println("\"We're looking for strong recruits like you to join our ranks.\"");
                        System.out.println("\"After all, it's the survival of the fittest out here.\"");
                        System.out.println("\"So what do you say? Are you willing to join us bandits?\"");
                        System.out.println("Do you want to join them? (ENDS GAME)");
                    }
                    choice = input.nextLine();
                    if(choice.substring(0,1).toLowerCase().equals("y")) {
                        gameWon = true;
                        break;
                    }
                    else{
                        winPercentage = 0.1;
                    }
                }
                else{day();}
                timeOfDay = "afternoon";
            }
            else if(timeOfDay.equals("afternoon")) {
                afternoon();
                timeOfDay = "night";
            }
            else{
                night();
                timeOfDay = "day";
                daysSurvived++;
            }
        }
        if(gameWon == true) {
            String action = "";
            if(standing == 0) {
                action = "rode out on a ship";
                System.out.println("LONE WOLF ENDING: You live out the rest of your life in peace on your ship, occasionally visiting islands and exploring.");
            }
            else if(standing > 0) {
                action = "joined the US coalition";
                System.out.println("MILITARY ENDING: You live out the rest of your life dedicating yourself to restoring peace to the world with the US Coalition.");
            }
            else{
                action = "joined the group of bandits";
                System.out.println("BANDIT ENDING: You live out the rest of your life surviving at all costs with your fellow bandits.");
            }
            gui.showWinScreen();
            System.out.println(player.getName() + " survived for " + daysSurvived + " days on their own before they " + action + ".");
            System.out.println("YOU WIN!");
            try{
                Thread.sleep(4000);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        else{
            System.out.println(player.getName() + " was able to survive for " + daysSurvived + " days.");
            System.out.println("YOU LOSE!");
            gui.showLostScreen();
            try{
                Thread.sleep(4000);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        save();
        System.exit(0);
    }

    private void decrementPlayerStats() {
        player.increaseHunger(HUNGER_INCREASE);
        player.increaseThirst(THIRST_INCREASE);
        if(player.getHunger() >= player.STARVING_LIMIT) {
            player.takeDamage(player.MAX_HEALTH);
            System.out.println("YOU DIED OF STARVATION!");
        }
        else if(player.getHunger() >= 0.7 * player.STARVING_LIMIT) {
            System.out.println("You feel your body getting weaker from the lack of food.");
        }
        if(player.getThirst() >= player.DEHYDRATION_LIMIT && player.getHealth() > 0) {
            player.takeDamage(player.MAX_HEALTH);
            System.out.println("YOU DIED OF DEHYDRATION!");
        }
        else if(player.getThirst() >= 0.7 * player.DEHYDRATION_LIMIT) {
            System.out.println("You feel your body getting weaker from the lack of hydration.");
        }
        if(player.isTurning()) {
            player.increaseInfectionLevel(10);
        }
        if(player.getInfectionLevel() >= 100) {
            System.out.println("YOUR INFECTION LEVEL REACHED THE POINT OF NO RETURN.");
            player.takeDamage(player.MAX_HEALTH);
        }
        gui.setAllPlayerStats();
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
                player.accessInventory(gui);
                System.out.println("After accessing your inventory, what would you like to do for the day?");
            }
            else if(choice.equals("stats")) {
                System.out.println(player);
                System.out.println("After checking " + player.getName() + "'s stats, what would you like to do for the day?");
            }
            else{
                System.out.println("Please enter a valid option.");
            }
            System.out.println(options);
            choice = input.nextLine();
        }
        if(choice.equals("fight")) {
            fight();
        }
        else if(choice.equals("look")) {
            lookForHumans();
        }
        else{
            scavenge();
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
                player.accessInventory(gui);
                System.out.println("After accessing your inventory, what would you like to do for the afternoon?");
            }
            else if(choice.equals("stats")) {
                System.out.println(player);
                System.out.println("After checking " + player.getName() + "'s stats, what would you like to do for the afternoon?");
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
                player.accessInventory(gui);
                System.out.println("After accessing your inventory, what would you like to do for the night?");
            }
            else if(choice.equals("stats")) {
                System.out.println(player);
                System.out.println("After checking " + player.getName() + "'s stats, what would you like to do for the night?");
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
        decrementPlayerStats();
    }

    private void scavenge() {
        if(Math.random() > 0.75) {
            System.out.println("You went looking for loot, but ended up getting ambushed!");
            Enemy e = Enemy.generateRandomEnemy(0.55,0.3,0.15);
            battle(e);
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
        if(random < 0.4) {
            enemy = Enemy.generateRandomBasicEnemy();
        }
        else if(random < 0.75) {
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
    }

    private void battle(Enemy enemy) { //restore to private after testing
        int enemyMaxHp = enemy.getHealth();
        boolean playerTurn = true;
        double numOfEnemyAttackHits = 0;
        try{
            Thread.sleep(1000);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        gui.setEnemyName(enemy.getName());
        gui.showEnemyData();
        while(player.getHealth() > 0 && enemy.getHealth() > 0) {
            if(playerTurn) {
                int damage = player.attack();
                enemy.takeDamage(damage);
                System.out.println(player.getName() + " attacked the " + enemy.getName() + " for " + damage + " damage with their " + player.getEquippedWeapon().getName() + ".");
                System.out.println("The " + enemy.getName() + " now has " + enemy.getHealth() + " health.");
                System.out.println();
                gui.setEnemyHealth(enemy.getHealth(), enemyMaxHp);
            }
            else{
                int damage = enemy.attack();
                if(damage == 0) {
                    System.out.println("The " + enemy.getName() + "'s attack missed.");
                }
                else{
                    player.takeDamage(damage);
                    System.out.println("The " + enemy.getName() + " attacked " + player.getName() + " for " + damage + " damage.");
                    System.out.println(player.getName() + " now has " + player.getHealth() + " health.");
                    numOfEnemyAttackHits++;
                }
                gui.setAllPlayerStats();
                System.out.println();
            }
            playerTurn = !playerTurn;
            try{
                Thread.sleep(500);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        if(player.getHealth() > 0) {
            System.out.println(player.getName() + " managed to survive against the " + enemy.getName() + " with " + player.getHealth() + " health.");
            if(player.getHealth() > 0) {
                Item i = enemy.dropItem();
                System.out.println("The " + enemy.getName() + " dropped a " + i.getName() + ".");
                player.addToInventory(i);
            }
            if(enemy.getName().contains("zombie")) {
                if(numOfEnemyAttackHits > 0) {
                    if(player.isTurning() == false && Math.random() < numOfEnemyAttackHits * 0.1) {
                        player.setTurning(true);
                        System.out.println("The " + enemy.getName() + " managed to infect you during your battle.");
                        System.out.println("Your infection level will steadily rise unless you use something to cure it.");
                    }
                }
            }
        }
        else{
            System.out.println("The " + enemy.getName() + " has slain " + player.getName());
        }
        gui.hideEnemyData();
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
            System.out.println("You meet a strange merchant on a " + modeOfTransport + " who seems willing to offer you something for a price.");
            Consumable c = Consumable.generateRandomConsumable();
            if(Math.random() < 0.65) {
                Weapon w  = Weapon.generateRandomMedTierWeapon();
                System.out.println("The merchant is willing to offer you a " + w.getName()+".");
                if(player.getInventory().size() == 0) {
                    System.out.println("But it doesn't seem like you have anything, so the merchant rides away.");
                }
                else{
                    int invIndex = (int) (Math.random() * player.getInventory().size());
                    System.out.println("But you'll have to trade your " + player.getInventory().get(invIndex).getName() + ".");
                    System.out.println("Do you want to trade your " + player.getInventory().get(invIndex).getName() + " for a " + w.getName() +"?");
                    choice = input.nextLine();
                    if(choice.length() > 0 && choice.substring(0,1).toLowerCase().equals("y")) {
                        System.out.println("You traded your " + player.getInventory().remove(invIndex) + " for a " + w.getName() + ".");
                        player.addToInventory(w);
                    }
                    else{
                        System.out.println("You declined the trade offer and went on your merry way.");
                    }
                }
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
        else if(random < 0.7) {
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
                player.addToInventory(c);
                player.incrementNumOftimesStolen();
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
                    boolean isNumeric = true;//TODO DEBUG THIS, typing 0 first try didn't work
                    for(int i = 0; i < choice.length(); i++) {
                        if(! Character.isDigit(choice.charAt(i))) {
                            isNumeric = false;
                        }
                    }
                    while(!choice.toLowerCase().equals("quit") && (isNumeric == false || Integer.parseInt(choice) < 0 || Integer.parseInt(choice) >= player.getInventory().size())) {
                        isNumeric = true;
                        for(int i = 0; i < choice.length(); i++) {
                            if(! Character.isDigit(choice.charAt(i))) {
                                isNumeric = false;
                            }
                        }
                        System.out.println("Please enter a valid index, or type \"quit\" to give up on giving an item:");
                        choice = input.nextLine();
                    }
                    if(choice.toLowerCase().equals("quit")) {
                        System.out.println("You decide that your items are too valuable to be given away to others, so you leave those survivors to struggle on their own.");
                    }
                    int index = Integer.parseInt(choice);
                    System.out.println("You walk up to the survivors and offer them your " + player.getInventory().remove(index).getName() +".");
                    System.out.println("They thank you for your generosity and leave you after some thoughtful goodbyes.");
                    player.incrementNumOfTimesGiven();
                }
            }
        }
    }

    public void save() {
        try{
            File f = new File("src/players.data");
            f.createNewFile();
            FileWriter fw = new FileWriter("src/players.data",true);
            fw.write("Player name: " + player.getName() + "\n");
            fw.write("Days survived: " + daysSurvived + "\n");
            fw.write("Status: ");
            if(gameWon) {
                fw.write("Alive");
            }
            else{
                fw.write("Deceased");
            }
            fw.write("\n");
            fw.close();
        }
        catch(IOException e){
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }

    public static void load() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> daysSurvived = new ArrayList<Integer>();
        ArrayList<String> statuses = new ArrayList<String>();
        boolean hasPreviousSaves = true;
        try{
            File f = new File("src/players.data");
            Scanner s = new Scanner(f);
            while(s.hasNextLine()) {
                String data = s.nextLine();
                if(data.contains("Player name: ")) {
                    names.add(data.substring(data.indexOf(": ")+2));
                }
                else if(data.contains("Days survived: ")) {
                    String num = data.substring(data.indexOf(": ")+2);
                    daysSurvived.add(Integer.parseInt(num));
                }
                else if(data.contains("Status: ")) {
                    statuses.add(data.substring(data.indexOf(": ")+2));
                }
            }
        }
        catch(FileNotFoundException e) {
            hasPreviousSaves = false;
        }
        if(names.size() == 0) {
            hasPreviousSaves = false;
        }
        if(hasPreviousSaves) {
            int longestNameLength = 20;
            for(int i = 0; i < names.size(); i++) {
                if(names.get(i).length() > longestNameLength) {
                    longestNameLength = names.get(i).length();
                }
            }
            String longSpace = "";
            for(int i = 0; i < longestNameLength; i++) {
                longSpace += " ";
            }
            System.out.println("Previous survival runs:");
            System.out.println("  Survivor Name: " + longSpace.substring(0, longestNameLength - "Survivor Name:".length()) + "    Days survived:      Status:");
            for(int i = 0; i < names.size(); i++) {
                System.out.print((i+1) + ".");
                System.out.print(names.get(i));
                System.out.print(longSpace.substring(0,longestNameLength - names.get(i).length()));
                System.out.print("     ");
                System.out.print(daysSurvived.get(i));
                System.out.print("                    ".substring(("" + daysSurvived.get(i)).length() ));
                System.out.print(statuses.get(i));
                System.out.println();
            }
        }
    }
}
