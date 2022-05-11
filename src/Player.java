import java.util.ArrayList;

public class Player {
    String name;
    private ArrayList<Item> inventory;
    private int inventorySlots;
    public final int MAX_HEALTH = 0;
    private int health;
    private int hunger;
    public final int STARVING_LIMIT = 100;
    private int thirst;
    public final int DEHYDRATION_LIMIT = 100;
    private int infectionLevel;
    public final int INFECTION_LIMIt = 100;
    private int strength;
    private int agility;
    public final int MAX_STATS_LEVEL = 10;
    private boolean alive;

    public Player(String name) {
        this.name = name;
        inventory = new ArrayList<Item>();
        inventorySlots = 6;
        health = 100;
        hunger = 0;
        thirst = 0;
        infectionLevel = 0;
        strength = 1;
        agility = 1;
        alive = true;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
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

    public int getInfectionLevel() {
        return infectionLevel;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setName(String name) {
        this.name = name;
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
    }
}
