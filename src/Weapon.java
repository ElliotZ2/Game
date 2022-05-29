public class Weapon extends Item{
    private int damage;
    private int timesEnhanced;
    private final int MAX_ENHANCES = 5;
    private final double ENHANCE_MULTIPLIER = 0.1;
    private static final Weapon[] allLowTierWeapons =
            {new Weapon("toothpick", 1),
            new Weapon("bat", 5),
            new Weapon("shovel", 7),
            new Weapon("fork", 2),
            new Weapon("pencil", 3),
            new Weapon("wrench", 4)};
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
        timesEnhanced = 0;
    }

    public static Weapon generateRandomLowTierWeapon() {
        Weapon w = allLowTierWeapons[(int) (Math.random() * allLowTierWeapons.length)];
        return new Weapon(w.getName(), w.damage);
    }

    public static Weapon generateRandomMedTierWeapon() {
        Weapon w = allMedTierWeapons[(int) (Math.random() * allMedTierWeapons.length)];
        return new Weapon(w.getName(), w.damage);
    }

    public static Weapon generateRandomHighTierWeapon() {
        Weapon w = allHighTierWeapons[(int) (Math.random() * allHighTierWeapons.length)];
        return new Weapon(w.getName(), w.damage);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int d) {
        damage = d;
    }

    public void enhance() {
        if(getName().equals("pair of fists")) {
            System.out.println("You then realize that you probably shouldn't hammer your fists in order to enhance it.");
            return;
        }
        if(timesEnhanced >= MAX_ENHANCES) {
            System.out.println("You can't enhance your weapon anymore beyond " + timesEnhanced + " times.");
        }
        else{
            if(Math.random() <= 0.10) {
                System.out.println("You tried to enhance your " + getName() + ", but ended up breaking it.");
                breakWeapon();
            }
            else{
                int previousDamage = damage;
                setDamage((int) (damage * (1.0 + ENHANCE_MULTIPLIER)) + 1);
                System.out.println("You enhanced your " + getName() + " and increased its damage from " + previousDamage + " to " + damage + ".");
                timesEnhanced++;
            }
        }
    }

    public void breakWeapon() {
        setName("broken " + getName());
        damage = (int) (damage * 0.3);
    }

    public String toString() {
        String s = super.toString();
        s += "\nDamage: " + damage;
        return s;
    }
}
