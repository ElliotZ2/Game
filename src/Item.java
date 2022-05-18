public class Item {
    private String name;
    private String description;

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        String s = getName();
        if(description != null) {
            s += ": " + description;
        }
        return s;
    }
}
