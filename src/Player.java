import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    String name;
    private ArrayList<Item> inventory;
    public final int MAX_HEALTH = 100;
    private int health;
    private int hunger;
    public final int STARVING_LIMIT = 100;
    private int thirst;
    public final int DEHYDRATION_LIMIT = 100;
    private int infectionLevel;
    public final int INFECTION_LIMIt = 100;
    private final int BASE_DAMAGE = 15;
    private final double DAMAGE_RANGE = 0.25;
    private Weapon equippedWeapon;
    private boolean turning;
    private int numOftimesStolen;
    private int numOfTimesGiven;

    public Player(String name) {
        this.name = name;
        inventory = new ArrayList<Item>();
        health = 100;
        hunger = 0;
        thirst = 0;
        infectionLevel = 0;
        equippedWeapon = new Weapon("pair of fists", 1);
        turning = false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addToInventory(Item item) {
        if(item != null) {
            inventory.add(item);
        }
    }

    public int indexOfItem(String searchItem) {
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).getName().toLowerCase().equals(searchItem.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public Item getItemAtIndex(int index) {
        if(index >= inventory.size() || index < 0) {
            return null;
        }
        else{
            return inventory.get(index);
        }
    }

    public void printInventory() {
        String s = "Inventory: [";
        if(inventory.size() >= 1) {
            s += "0 - " + inventory.get(0).getName();
        }
        else{
            System.out.println("Inventory: Empty");
            return;
        }
        for(int i = 1; i < inventory.size(); i++) {
            s += ", " + i + " - " + inventory.get(i).getName();
        }
        s += "]";
        System.out.println(s);
    }

    public int getHealth() {
        return health;
    }

    public void heal(int amt) {
        health += amt;
        if(health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if(health < 0) {
            health = 0;
        }
    }

    public int attack() {
        int damage = BASE_DAMAGE + equippedWeapon.getDamage();
        double random = Math.random() * DAMAGE_RANGE;
        if((int) Math.random() == 0) {
            damage += damage * random;
        }
        else{
            damage -= damage * random;
        }
        return (int) damage;
    }

    public int getHunger() {
        return hunger;
    }

    public void decreaseHunger(int amt) {
        hunger -= amt;
        if(hunger < 0) {
            hunger = 0;
        }
    }

    public void increaseHunger(int amt) {
        hunger += amt;
        if(hunger > STARVING_LIMIT) {
            hunger = STARVING_LIMIT;
        }
    }

    public int getThirst() {
        return thirst;
    }

    public void decreaseThirst(int amt) {
        thirst -= amt;
        if(thirst < 0) {
            thirst = 0;
        }
    }

    public void increaseThirst(int amt) {
        thirst += amt;
        if(thirst > DEHYDRATION_LIMIT) {
            thirst = DEHYDRATION_LIMIT;
        }
    }

    public boolean isTurning() {
        return  turning;
    }

    public void setTurning(boolean b) {
        turning = b;
    }

    public int getInfectionLevel() {
        return infectionLevel;
    }

    public void increaseInfectionLevel(int amount) {
        infectionLevel += amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOftimesStolen() {
        return numOftimesStolen;
    }

    public void incrementNumOftimesStolen() {
        numOftimesStolen++;
    }

    public int getNumOfTimesGiven() {
        return numOfTimesGiven;
    }

    public void incrementNumOfTimesGiven() {
        numOfTimesGiven++;
    }

    public void consumeItem(Consumable c) {
        if(c.getType().equals("healing")) {
            heal(c.getEffectiveness());
        }
        else if(c.getType().equals("food")) {
            decreaseHunger(c.getEffectiveness());
        }
        else if(c.getType().equals("drink")) {
            decreaseThirst(c.getEffectiveness());
        }
        else if(c.getType().equals("curing")) {
            if(infectionLevel > 0) {
                infectionLevel -= c.getEffectiveness();
                if(infectionLevel <= 0) {
                    infectionLevel = 0;
                    turning = false;
                    System.out.println("You were able to stop yourself from turning into a zombie; your infection level is now at 0.");
                }
                else{
                    System.out.println("You were able to decrease your infection level to " + infectionLevel + ", but you need more curing items to stop yourself from turning.");
                }
            }
        }
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void consumeItem(int i) {
        if(i >= inventory.size() || i < 0) {//check for valid index
            return;
        }

        if(inventory.get(i) instanceof Consumable) {
            consumeItem((Consumable)inventory.get(i));
            inventory.remove(i);
        }
    }

    public void accessInventory(ZombieGameGUI gui) {
        String choice = "";
        Scanner input = new Scanner(System.in);
        //System.out.println("Type the index of the item you want to access, or \"quit\" to exit out of your inventory");
        while(!choice.toLowerCase().equals("quit")) {
            printInventory();
            System.out.println("Type the index of the item you want to access, \"discard\" to choose to remove an item, or \"quit\" to exit out of your inventory:");
            choice = input.nextLine();
            boolean isNumeric = true;
            for(int i = 0; i < choice.length(); i++) {
                if(! Character.isDigit(choice.charAt(i))) {
                    isNumeric = false;
                }
            }
            if(choice.length() == 0) {

            }
            else if(isNumeric) {
                int index = Integer.parseInt(choice);
                if(index < 0 || index >= inventory.size()) {
                    System.out.println("Please enter a valid index:");
                    choice = input.nextLine();
                }
                else{
                    Item item = inventory.get(index);
                    if(item instanceof Weapon) {
                        System.out.println(item);
                        if(equippedWeapon == null) {
                            System.out.println("You currently don't have an equipped weapon");
                        }
                        else{
                            System.out.println("You currently have a " + equippedWeapon.getName() + " equipped that does " + equippedWeapon.getDamage() + " damage.");
                        }
                        System.out.println("Would you like to equip the " + item.getName() + "?");
                        choice = input.nextLine();
                        if(choice.length() > 0 && choice.substring(0,1).toLowerCase().equals("y")) {
                            if(equippedWeapon != null) {
                                if(!equippedWeapon.getName().equals("pair of fists")) {
                                    addToInventory(equippedWeapon);
                                }
                            }
                            equippedWeapon = (Weapon) item;
                            inventory.remove(index);
                        }
                    }
                    else if(item instanceof Consumable) {
                        String action = "";
                        Consumable c = (Consumable) item;
                        if(c.getType().equals("food")) {
                            action = "eat";
                        }
                        else if(c.getType().equals("drink")) {
                            action = "drink";
                        }
                        else{
                            action = "use";
                        }
                        System.out.println("Would you like to " + action + " the " + item.getName() + "?");
                        choice = input.nextLine();
                        if(choice.length() > 0 && choice.substring(0,1).toLowerCase().equals("y")) {
                            System.out.println("You " + action + " the " + item.getName());
                            consumeItem(c);
                            inventory.remove(index);
                        }
                    }
                }
            }
            else if(choice.toLowerCase().equals("discard")) {
                if(inventory.size() == 0) {
                    System.out.println("Your inventory is empty, so you don't have anything to discard.");
                }
                else{
                    System.out.println("Type the index of the item you want to discard.");
                    choice = input.nextLine();
                    boolean isNumber = false;
                    while(choice.length() == 0 ||isNumber == false && Integer.parseInt(choice) < 0 || Integer.parseInt(choice) >= inventory.size()) {
                        isNumber = true;
                        for(int i = 0; i < choice.length(); i++) {
                            if(!Character.isDigit(choice.charAt(i))) {
                                isNumber = false;
                            }
                        }
                        System.out.println("Please enter a valid index:");
                        choice = input.nextLine();
                    }
                    int index = Integer.parseInt(choice);
                    System.out.println("Are you sure you want to discard your " + inventory.get(index).getName() + "?");
                    choice = input.nextLine();
                    if(choice.length() > 0 && choice.substring(0,1).toLowerCase().equals("y")) {
                        System.out.println("You discarded your " + inventory.remove(index).getName() + ".");
                    }
                    else{
                        System.out.println("You decided not to discard your " + inventory.get(index).getName() + ".");
                    }
                }
            }
            gui.setAllPlayerStats();
        }
    }

    public String toString() {
        String s = "Stats for " + name + ":\n";
        s += "Health: " + health + "/" + MAX_HEALTH +"\n";
        s += "Hunger Level: " + hunger + "/" + STARVING_LIMIT +"\n";
        s += "Thirst Level: " + thirst + "/" + DEHYDRATION_LIMIT +"\n";
        if(infectionLevel > 0) {
            s += "Infection Level: " + infectionLevel + "\n";
        }
        s += "Equipped Weapon: " + equippedWeapon.getName() + "\n";
        return s;
    }
}
