public class Weapon extends Item{
    private int durability;
    private int damage;
    //figure out how to store a list of all existing items, maybe in another file

    public Weapon (String name, int damage, int durability) {
        super(name);
        this.damage = damage;
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public int getDamage() {
        return damage;
    }
}
