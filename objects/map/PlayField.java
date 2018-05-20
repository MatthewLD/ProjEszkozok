package objects.map;

public class PlayField {
    private final int x;
    private final int y;
    private final FieldType type;
    
    public PlayField(int x, int y, FieldType type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public FieldType getType() { return type; }
}
