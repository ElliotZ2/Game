public class Consumable extends Item{
    private String type; //healing, food, drink
    private int effectiveness;
    //private int uses; if i want items to have multiple uses
    private static final Consumable[] allFoods =
            {new Consumable("cookie", "food", 10),
            new Consumable("royal chicken dinner", "food", 100)};
    private static final Consumable[] allDrinks =
            {new Consumable("bottled water", "drink", 40),
            new Consumable("orange juice", "drink", 30)};
    private static final Consumable[] allHealing =
            {new Consumable("bandages", "healing", 15),
            new Consumable("medkit", "healing", 45)};

    public Consumable(String name, String type, int effectiveness) {
        super(name);
        this.type = type;
        this.effectiveness = effectiveness;
    }

    public String getType() {
        return type;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public static Consumable generateRandomFood() {
        return allFoods[(int) (Math.random() * allFoods.length)];
    }

    public static Consumable generateRandomDrink() {
        return allDrinks[(int) (Math.random() * allDrinks.length)];
    }

    public static Consumable generateRandomHealing() {
        return allHealing[(int) (Math.random() * allHealing.length)];
    }
}
