import java.util.concurrent.ConcurrentSkipListMap;

public class Test {
    public static void main(String[] args) {
        Player p = new Player("James", 90, 90, 90, Weapon.generateRandomLowTierWeapon());
        p.addToInventory(Weapon.generateRandomHighTierWeapon());
        p.addToInventory(Consumable.generateRandomDrink());
        p.addToInventory(Consumable.generateRandomFood());
        p.addToInventory(Consumable.generateRandomHealing());
        p.accessInventory();
        p.printInventory();
        System.out.println(p);
        /*
        Enemy e = new Enemy("normal zombie", 500, 40, "high");
        Event.battle(p,e);*/
    }
}
