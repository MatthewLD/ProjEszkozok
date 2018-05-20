package objects;

import objects.creatures.NPC;
import objects.creatures.NPCType;
import objects.item.Item;

public class Player extends NPC{
    private ItemList items;
    
    public Player(String n, int at, int df, int hp, int mp, int x, int y, NPCType type, boolean friendly, boolean dead){
        super(n, at, df, hp, mp, x, y, type, friendly, dead);
    }
    
    public ItemList getItems(){ return this.items; }
    
    public void setItems(ItemList items) { this.items = items; }
    
    public void addItem(Item item) { items.addItem(item); }
}