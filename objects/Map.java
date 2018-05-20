package objects;

import java.util.ArrayList;
import objects.map.FieldType;
import objects.map.PlayField;

public final class Map {
    private ArrayList<PlayField> map = new ArrayList<>();
    
    public void addField(PlayField field){
        map.add(field);
    }

    public FieldType getType(PlayField field){
        return field.getType();
    }
    
    public FieldType getType(int x, int y){
        for(PlayField field : map){
            if(field.getX() == x && field.getY() == y){
                return field.getType();
            }
        }
        return null;
    }
    
    public ArrayList<PlayField> getMap() { return map; }
}
