public class Test {
    public static void main(String[] args) {
        Player p = new Player("Joseph", 100, new Weapon("sword", 1));
        for(int i = 0; i < 20; i++) {
            System.out.println(p.attack());
        }
    }
}
