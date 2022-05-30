public class Consumable extends Item{
    private String type; //healing, food, drink
    private int effectiveness;
    //private int uses; if i want items to have multiple uses
    private static final Consumable[] allFoods =
            {new Consumable("cookie", "food", 10),
            new Consumable("royal chicken dinner", "food", 100),
            new Consumable("granola bar", "food", 25),
            new Consumable("chips", "food", 35),
            new Consumable("canned beans", "food", 25),
            new Consumable("chocolate bar", "food", 20)};
    private static final Consumable[] huntedFoods =
            {new Consumable("chicken", "food", 30),
            new Consumable("pork", "food", 35),
            new Consumable("beef", "food", 40),
            new Consumable("duck", "food", 25)};
    private static final Consumable[] allDrinks =
            {new Consumable("bottled water", "drink", 40),
            new Consumable("orange juice", "drink", 30),
            new Consumable("canned soda", "drink", 35),
            new Consumable("milk", "drink", 30)};
    private static final Consumable[] allHealing =
            {new Consumable("bandages", "healing", 15),
            new Consumable("medkit", "healing", 45)};
    private static final Consumable[] allCuring =
            {new Consumable("type 0 vaccine", "curing", 75),
            new Consumable("prototype vaccine", "curing", 50),
            new Consumable("type 51 vaccine", "curing", 100)};

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

    public static Consumable generateRandomConsumable() {
        double random = Math.random();
        if(random < 0.25) {
            return generateRandomDrink();
        }
        if(random < 0.50) {
            return generateRandomFood();
        }
        if(random< 0.75) {
            return generateRandomCuring();
        }
        return generateRandomHealing();
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

    public static Consumable generateRandomCuring() {
        return allCuring[(int) (Math.random() * allCuring.length)];
    }

    public static Consumable generateRandomHuntedFood() {
        return huntedFoods[(int) (Math.random() * huntedFoods.length)];
    }
}
