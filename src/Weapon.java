public class Weapon extends Item{
    private int damage;
    private static Weapon[] allWeapons =
            {new Weapon("toothpick", 1),
            new Weapon("bat", 5),
            new Weapon("shovel", 7)};

    public Weapon (String name, int damage) {
        super(name);
        this.damage = damage;
    }

    public static Weapon generateRandomWeapon() {
        return allWeapons[(int) (Math.random() * allWeapons.length)];
    }

    public int getDamage() {
        return damage;
    }
}
