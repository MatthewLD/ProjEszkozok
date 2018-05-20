package objects.item;

public class Item {
    private final String name;
    private final ItemType type;
    private final int atkMply;
    private final int defMply;
    private final int x;
    private final int y;
    private final int hpMply;
    private final int mpMply;
    private boolean hasGot;
    private int hp;
    
    public Item(String name, ItemType type, int atk, int def, int x, int y, int hp, int mp, boolean has, int hp_of_item){
        this.name = name;
        this.type = type;
        this.atkMply = atk;
        this.defMply = def;
        this.x = x;
        this.y = y;
        this.hpMply = hp;
        this.mpMply = mp;
        this.hasGot = has;
        this.hp = hp_of_item;
    }
    
    @Override
    public String toString(){
        return name + " " + type.toString() + " " + Integer.toString(atkMply) + " " +
                Integer.toString(defMply) + " " + Integer.toString(x) + " " +
                Integer.toString(y) + " " + Integer.toString(hpMply) + " " +
                Integer.toString(mpMply) + " " + Boolean.toString(hasGot) + " " +
                Integer.toString(hp);
    }
}
