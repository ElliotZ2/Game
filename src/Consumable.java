public class Consumable extends Item{
    private String type; //healing, food, drink
    private int effectiveness;
    //private int uses; if i want items to have multiple uses

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
}
