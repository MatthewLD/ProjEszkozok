package objects.creatures;

public class NPC {
    private final String name;
    private int hp;
    private int mp;
    private final int atk;
    private final int def;
    private int x;
    private int y;
    private final NPCType type;
    private boolean friendly;
    private boolean dead;
    
    public NPC(String n, int at, int df, int hp, int mp, int x, int y, NPCType type, boolean friendly, boolean dead){
        this.name = n;
        this.atk = at;
        this.def = df;
        this.hp = hp;
        this.mp = mp; 
        this.x = x;
        this.y = y;
        this.type = type;
        this.friendly = friendly;
        this.dead = dead;
    }
    
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getFriendly() { return friendly; }
    public boolean getDead() { return dead; }
    
    public void setHP(int hp) { this.hp = hp; }
    public void setMP(int mp) { this.mp = mp; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setFriendly(boolean friendly) { this.friendly = friendly; }
    public void setDead(boolean dead){ this.dead = dead; }
    
    @Override
    public String toString(){
        return name + " " + Integer.toString(hp) + " " + Integer.toString(mp) + " " +
                Integer.toString(atk) + " " + Integer.toString(def) + " " +
                Integer.toString(x) + " " + Integer.toString(y) + " " +
                type.toString() + " " + Boolean.toString(friendly) + " " +
                Boolean.toString(dead);
    }
}
