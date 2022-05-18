public class Weapon extends Item{
    private int damage;
    private static final Weapon[] allLowTierWeapons =
            {new Weapon("toothpick", 1),
            new Weapon("bat", 5),
            new Weapon("shovel", 7),
            new Weapon("fork", 2),
            new Weapon("pencil", 3)};
    private static final Weapon[] allMedTierWeapons =
            {new Weapon("knife", 12),
            new Weapon("spear", 15),
            new Weapon("police baton", 10),
            new Weapon("axe", 17),
            new Weapon("machete", 20)};
    private static final Weapon[] allHighTierWeapons =
            {new Weapon("halberd", 25),
            new Weapon("lance", 27),
            new Weapon("chainsaw", 29),
            new Weapon("katana", 32),
            new Weapon("waraxe", 35)};

    public Weapon (String name, int damage) {
        super(name);
        this.damage = damage;
    }

    public Weapon (String name, int damage, String description) {
        super(name, description);
        this.damage = damage;
    }

    public static Weapon generateRandomLowTierWeapon() {
        return allLowTierWeapons[(int) (Math.random() * allLowTierWeapons.length)];
    }

    public static Weapon generateRandomMedTierWeapon() {
        return allMedTierWeapons[(int) (Math.random() * allMedTierWeapons.length)];
    }

    public static Weapon generateRandomHighTierWeapon() {
        return allHighTierWeapons[(int) (Math.random() * allHighTierWeapons.length)];
    }

    public int getDamage() {
        return damage;
    }

    public String toString() {
        String s = super.toString();
        s += "\nDamage: " + damage;
        return s;
    }
}
