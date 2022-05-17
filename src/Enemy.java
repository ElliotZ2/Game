public class Enemy {
    private String name;
    private int health;
    private int damage;
    private double chanceToHit;
    private String weaponDrop; //none, low, med, or high
    private static final Enemy[] allBasicEnemies =
            {
                    new Enemy("civilian zombie", 70, 10, "none"),
                    new Enemy("salesperson zombie", 80, 9, "none"),
            };
    private static final Enemy[] allMedEnemies =
            {
                    new Enemy("firefighter zombie", 100, 15, "low"),
                    new Enemy("police zombie", 90, 17, "low"),
                    new Enemy("gangster zombie", 85, 20, "low")
            };
    private static final Enemy[] allAdvancedEnemies =
            {
                    new Enemy("soldier zombie", 150, 20, "med"),
                    new Enemy("mutated zombie", 200, 20, "high")
            };

    public Enemy(String name, int health, int damage, String weaponDrop){
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.weaponDrop = weaponDrop; //none low med high
        chanceToHit = 0.25;
    }

    public Enemy(String name, int health, int damage, double chanceToHit, String weaponDrop) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.weaponDrop = weaponDrop;
        this.chanceToHit = chanceToHit;
    }

    public int attack() {
        if(Math.random() > chanceToHit) {
            return 0;
        }
        else{
            int random = (int) (Math.random() * 3) - 2;
            return damage + random;
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if(health < 0) {
            health = 0;
        }
    }

    public Item dropItem() { //this method is called when the enemy dies and a weapon drops
        if(weaponDrop.equals("none")) {//since no weapons are dropped, just drop a random consumable
            return Consumable.generateRandomConsumable();
        }
        else if(weaponDrop.equals("low")) {
            return Weapon.generateRandomLowTierWeapon();
        }
        else if(weaponDrop.equals("med")) {
            return Weapon.generateRandomMedTierWeapon();
        }
        else if(weaponDrop.equals("high")) {
            return Weapon.generateRandomHighTierWeapon();
        }
        return null;
    }

    public static Enemy generateRandomBasicEnemy() {
        return allBasicEnemies[(int) (Math.random() * allBasicEnemies.length)];
    }

    public static Enemy generateRandomMedEnemy() {
        return allMedEnemies[(int) (Math.random() * allMedEnemies.length)];
    }

    public static Enemy generateRandomAdvancedEnemy() {
        return allAdvancedEnemies[(int) (Math.random() * allAdvancedEnemies.length)];
    }
}
