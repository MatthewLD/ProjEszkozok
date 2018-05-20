package objects;

import objects.item.Item;
import java.util.ArrayList;
import objects.item.Item;
import objects.item.ItemType;

public class ItemList {
    private ArrayList<Item> items = new ArrayList<>();
    
    public void addItem(String name, ItemType type, int atk, int def, int x, int y, int hp, int mp, boolean has, int hp_of_item){
        items.add(new Item(name, type, atk, def, x, y, hp, mp, has, hp_of_item));
    }
    
    public void addItem(Item item){
        items.add(item);
    }
    
    public ArrayList<Item> getItems() { return items; }
}
