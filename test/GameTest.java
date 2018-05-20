import objects.Map;
import objects.Player;
import objects.creatures.NPC;
import static objects.creatures.NPCType.*;
import static objects.map.FieldType.*;
import objects.map.PlayField;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import view.game.ViewGame;

public class GameTest{
    @Test
    public void mapSizeIsCorrect(){
        Map map = new Map();
        map.addField(new PlayField(0,0,GRASS));
        assertTrue(map.getMap().size() == 1);
    }
    
    @Test
    public void mapSizeIsCorrect_2(){
        Map map = new Map();
        map.addField(new PlayField(0,0,GRASS));
        map.addField(new PlayField(0,3,GRASS));
        assertTrue(map.getMap().size() == 2);
    }
    
    @Test
    public void x_coordinate_is_good(){
        Map map = new Map();
        map.addField(new PlayField(0,0,GRASS));
        assertTrue(map.getMap().get(0).getX()== 0);
    }
    
    @Test
    public void y_coordinate_is_good(){
        Map map = new Map();
        map.addField(new PlayField(0,0,GRASS));
        assertTrue(map.getMap().get(0).getY()== 0);
    }
    
    @Test
    public void player_name_is_good(){
        Player player = new Player("Gipsz Jakab",0,0,0,0,0,0,PLAYER,true,true);
        assertTrue(player.getName().equals("Gipsz Jakab"));
    }
    
    @Test
    public void player_coordinates_are_good(){
        Player player = new Player("Gipsz Jakab",0,0,0,0,0,0,PLAYER,true,true);
        assertTrue(player.getX()==0 && player.getY()==0);
    }
    
    @Test
    public void NPC_is_ork(){
        NPC npc = new NPC("Ork",0,0,0,0,0,0,ORK,true,true);
        assertTrue(npc.getType() == ORK);
    }
    
    @Test
    public void NPC_is_friendly(){
        NPC npc = new NPC("Ork",0,0,0,0,0,0,ORK,true,true);
        assertTrue(npc.getFriendly());
    }
    
    @Test
    public void NPC_hp_is_5(){
        NPC npc = new NPC("Ork",0,0,5,0,0,0,ORK,true,true);
        assertTrue(npc.getHp() == 5);
    }
    
    @Test
    public void playfield_x_coordinate_is_good(){
        PlayField field = new PlayField(0,0,GRASS);
        assertTrue(field.getX()==0 && field.getY()==0);
    }
    
    @Test
    public void playfield_type_is_good(){
        PlayField field = new PlayField(0,0,GRASS);
        assertTrue(field.getType()==GRASS);
    }
}
