package view.game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import objects.ItemList;
import objects.Map;
import objects.Player;
import objects.creatures.NPC;
import objects.creatures.NPCType;
import static objects.creatures.NPCType.*;
import objects.item.Item;
import objects.item.ItemType;
import static objects.item.ItemType.*;
import objects.map.FieldType;
import static objects.map.FieldType.*;
import objects.map.PlayField;
import view.View;
import view.ViewMainMenu;
import static view.error.ViewError.error;

public class ViewGame extends View{
    private Map map;
    private ItemList items;
    private ArrayList<NPC> npcs;
    private Player player;
    private String filename;
    private PlayField[][] visibleArea;
    private String nehezseg;
    
    private final double atkBoost;
    private final double defBoost;
    private final double npcAtkBoost;
    private final double npcDefBoost;

    public ViewGame(String gamemode, String filename) {
        if(filename==null) createNewGame();
        else createLoadedGame(filename);
        this.filename = filename;
        nehezseg = gamemode;
        visibleArea = new PlayField[9][9];
        switch (nehezseg) {
            case "Barátságos":
                atkBoost = 5.0;
                defBoost = 5.0;
                npcAtkBoost = 0.0;
                npcDefBoost = 0.0;
                break;
            case "Könnyű":
                atkBoost = 2.0;
                defBoost = 2.0;
                npcAtkBoost = 0.25;
                npcDefBoost = 0.25;
                break;
            case "Közepes":
                atkBoost = 1.0;
                defBoost = 1.0;
                npcAtkBoost = 1.0;
                npcDefBoost = 1.0;
                break;
            case "Nehéz":
                atkBoost = 1.0;
                defBoost = 1.0;
                npcAtkBoost = 2.0;
                npcDefBoost = 2.0;
                break;
            default:
                atkBoost = 0.5;
                defBoost = 0.5;
                npcAtkBoost = 3.0;
                npcDefBoost = 3.0;
                break;
        }
        super.createBasics();
        createWindow();
    }
    
    private void createNewGame(){
        map = createMap();
        items = createItems();
        npcs = createNPC();
    }
    
    private void createLoadedGame(String filename){
        map = loadMap(filename);
        items = loadItems(filename,"items-map");
        npcs = loadNPC(filename);
        player = loadPlayer(filename);
        player.setItems(loadItems(filename,"items-own"));
    }
    
    private void createWindow(){
        if(player==null) createPlayer();
        else{
            drawArea();
            JMenuBar menubar = new JMenuBar();
            JMenu gep = new JMenu("Főmenü");
            JMenuItem newGame = new JMenuItem("Játék mentése");
            newGame.addActionListener(actionEvent->{
                if(filename == null){
                    JFrame tmpFrame = new JFrame("Játékmentés");
                    tmpFrame.setSize(500,300);
                    tmpFrame.setVisible(true);
                    tmpFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    tmpFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e){
                            closeGame();
                        }
                    });


                    JPanel panel = new JPanel();
                    panel.setLayout(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.ipadx = width / 20; gbc.ipady = heigth / 20;
                    gbc.fill = gbc.HORIZONTAL;
                    gbc.insets = new Insets(6,6,6,6);

                    int x = 0, y = 0;

                    JLabel label = new JLabel("Add meg a mentésed nevét");
                    gbc.gridx = x; gbc.gridy = y++;
                    panel.add(label,gbc);

                    JTextField mentesNeve = new JTextField();
                    gbc.gridx = x; gbc.gridy = y++;
                    panel.add(mentesNeve,gbc);

                    JButton mentes = new JButton("Mentés");
                    gbc.gridx = x; gbc.gridy = y++;
                    panel.add(mentes,gbc);
                    mentes.addActionListener((ActionEvent actionEvent1) -> {
                        if(!mentesNeve.getText().equals("")){
                            saveGame(mentesNeve.getText());
                            filename = mentesNeve.getText();
                            tmpFrame.dispose();
                        }
                    });

                    tmpFrame.add(panel);
                    tmpFrame.revalidate();
                    tmpFrame.repaint();
                }
                else{
                    saveGame(filename);
                }
            });

            JMenuItem fomenube = new JMenuItem("Kilépés a főmenübe");
            fomenube.addActionListener(actionEvent->{
                String[] options = {"Igen","Nem"};

                if(JOptionPane.showOptionDialog(
                    frame,
                    "Biztos ki akarsz lépni a főmenübe? Minden nem mentett állás elvész.",
                    "Játék bezárása.",
                    JOptionPane.YES_NO_OPTION,
                    0,
                    null,
                    options,
                    null)
                        == JOptionPane.YES_OPTION){
                        frame.getContentPane().removeAll();
                        new ViewMainMenu();
                }
            });

            JMenuItem bezaras = new JMenuItem("Játék bezárása");
            bezaras.addActionListener(actionEvent->{
               closeGame();
            });

            gep.add(newGame);
            gep.addSeparator();
            gep.add(fomenube);
            gep.add(bezaras);

            menubar.add(gep);

            frame.setJMenuBar(menubar);
            
            
                    
            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        }
    }
    
    private Map createMap(){
        Map map = new Map();
        boolean[][] isBusy = new boolean[500][500];
        for(int i =0;i<500;i++) for(int j =0; j<500;j++) isBusy[i][j] = false;
        Random r = new Random();
        int i = 0, j = 0;
        while(i<500 && j < 500){ //Utak létrehozása
            if(r.nextInt(2)==1){
                map.addField(new PlayField(i,j,PATH));
                isBusy[i][j] = true;
                if(r.nextInt(101) % 2 == 1) i++;
                else j++;
            }
        }
        i = 0; j = 0;
        while(i<500 && j <500){ //Füvet ad hozzá
            if(r.nextInt(2)==1 && !isBusy[i][j]){
                map.addField(new PlayField(i,j,GRASS));
                isBusy[i][j] = true;
                if(r.nextInt(2)== 1) i++;
                else j++;
            }else{
                j++;
            }
        }
        i = 0; j = 0;
        while(i<500 && j <500){ //Vizek jönnek
            if(r.nextInt(2)==1 && !isBusy[i][j]){
                map.addField(new PlayField(i,j,WATER));
                isBusy[i][j] = true;
                if(r.nextInt(2)== 1) i++;
                else j++;
            }else{
                j++;
            }
        }
        i = 0; j = 0;
        while(i<500 && j <500){ //Fák
            if(r.nextInt(2)==1 && !isBusy[i][j]){
                map.addField(new PlayField(i,j,TREE));
                isBusy[i][j] = true;
                if(r.nextInt(2)== 1) i++;
                else j++;
            }else{
                j++;
            }
        }
        i = 0; j = 0;
        while(i<500 && j <500){ //Még random utak, ezek ilyen szakaszok lesznek, hátha...
            if(r.nextInt(2)==1 && !isBusy[i][j]){
                map.addField(new PlayField(i,j,PATH));
                isBusy[i][j] = true;
                if(r.nextInt(2)== 1) i++;
                else j++;
            }else{
                j++;
            }
        }
        i = 0; j = 0;
        while(i<500 && j <500){ //Városok
            if(r.nextInt(2)==1 && !isBusy[i][j]){
                map.addField(new PlayField(i,j,TOWN));
                isBusy[i][j] = true;
                if(r.nextInt(2)== 1) i++;
                else j++;
            }else{
                j++;
            }
        }
        i = 0; j = 0;
        while(i<500 && j <500){ //Még füvek
            if(r.nextInt(2)==1 && !isBusy[i][j]){
                map.addField(new PlayField(i,j,GRASS));
                isBusy[i][j] = true;
                if(r.nextInt(2)== 1) i++;
                else j++;
            }else{
                j++;
            }
        }
        for(i=0;i<500;i+=2){
            for(j = 0; j<500;j+=3){
                if(!isBusy[i][j]){
                    map.addField(new PlayField(i,j,GRASS));
                    isBusy[i][j] = true;
                }
            }
        }
        for(i=1;i<500;i+=3){
            for(j = 1; j<500;j+=2){
                if(!isBusy[i][j]){
                    map.addField(new PlayField(i,j,TREE));
                    isBusy[i][j] = true;
                }
            }
        }
        //A kimaradt helyekre kősziklák, amelyek barlangként is szolgálhatnak
        for(i=0;i<500;i++){
            for(j = 0; j<500;j++){
                if(!isBusy[i][j]){
                    map.addField(new PlayField(i,j,ROCK));
                    isBusy[i][j] = true;
                }
            }
        }

    return map;
    }
    
    private Map loadMap(String filename){
        Map map = new Map();
        try(BufferedReader br = new BufferedReader(new FileReader("src\\saves\\savegames\\"+filename+"\\"+filename+".game-map"))){
            for(int i=0;i<500;i++){
                String row = br.readLine();
                String tmp[] = row.split(" ");
                for(int j=0;j<500;j++){
                    map.addField(new PlayField(i, j, FieldType.valueOf(tmp[j])));
                }
            }
            br.close();
        }catch(Exception e){
            error(5); //Hiba betöltésnél
        }
        
        
        return map;
    }
    
    private ArrayList<NPC> createNPC(){
        Random r = new Random();
        
        ArrayList<NPC> npcs = new ArrayList<>();
        for(PlayField field : map.getMap()){
            switch(field.getType()){
                case GRASS:{
                    if(r.nextInt(10)>8){
                        npcs.add(new NPC("Kígyó",3,3,30,0,field.getX(),field.getY(), KIGYO, false, false));
                    }
                    break;
                }
                case TREE:{
                    if(r.nextInt(10)>8){
                        npcs.add(new NPC("Medve",8,2,100,0,field.getX(),field.getY(),MEDVE, false, false));
                    }
                    else if(r.nextInt(20)>17){
                        npcs.add(new NPC("Ogre",25,5,150,0,field.getX(),field.getY(),OGRE, false, false));
                    }
                    break;
                }
                case ROCK:{
                    if(r.nextInt(20)%4 == 0){
                        if(r.nextInt(3)==2){
                            npcs.add(new NPC("Varázsló goblin",4,0,50,20,field.getX(),field.getY(),GOBLIN, false, false));
                        }
                        else{
                            npcs.add(new NPC("Harcos goblin",20,1,30,0,field.getX(),field.getY(),GOBLIN, false, false));
                        }
                    }
                    else if(r.nextInt(20)>13){
                        npcs.add(new NPC("Imp",10,1,35,0,field.getX(),field.getY(),IMP, false, false));
                    }
                    else if(r.nextInt(50)>45){
                        npcs.add(new NPC("Ork",15,4,50,0,field.getX(),field.getY(),ORK, false, false));
                    }
                    else if(r.nextInt(100)==99){
                        npcs.add(new NPC("Sárkány",100,60,500,0,field.getX(),field.getY(),SARKANY, false, false));
                    }
                    break;
                }
                case PATH:{
                    if(r.nextInt(3)==2){
                        npcs.add(new NPC("Útonálló",5,2,35,0,field.getX(),field.getY(),EMBER,false, false));
                    }
                    else{
                        npcs.add(new NPC("Semleges személy",2,2,20,0,field.getX(),field.getY(),EMBER,true, false));
                    }
                    break;
                }
                case TOWN:{
                    npcs.add(new NPC("Kereskedő",1,1,1,0,field.getX(),field.getY(),EMBER,true, false));
                    break;
                }
            } 
        }
        
        return npcs;
    }
    
    private ArrayList<NPC> loadNPC(String filename){
        ArrayList<NPC> npcs = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src\\saves\\savegames\\"+filename+"\\"+filename+".npc"))){
            String row;
            while((row = br.readLine())!=null){
                String[] tmp = row.split(" ");
                npcs.add(new NPC(tmp[0],Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),
                        Integer.parseInt(tmp[3]),Integer.parseInt(tmp[4]),
                        Integer.parseInt(tmp[5]),Integer.parseInt(tmp[6]),
                        NPCType.valueOf(tmp[7]),Boolean.parseBoolean(tmp[8]),
                        Boolean.parseBoolean(tmp[9])));
            }
            br.close();
        }catch(Exception e){
            error(6); //Hiba betöltésnél
        }
        
        return npcs;
    }
    
    private ItemList createItems(){
        ItemList items = new ItemList();
        
        //random itemek
        for(PlayField field : map.getMap()){
            int random = new Random().nextInt(50);
            if(random==49) items.addItem("Acélvért", VERT, 1,30,field.getX(),field.getY(),10,1,false, 300);
            else if (random >46) items.addItem("Ezüstvért", VERT, 1, 25, field.getX(),field.getY(),6,1,false,250);
            else if (random >43) items.addItem("Sisak", SISAK, 1, 5, field.getX(), field.getY(), 3, 1, false, 100);
            else if (random >38) items.addItem("Bronzvért", VERT, 1, 3, field.getX(),field.getY(),3,1,false,50);
            else if (random >35) items.addItem("Kard", KARD, 10, 1, field.getX(), field.getY(),1,1,false, 100);
            else if (random >31) items.addItem("Varázseszköz",VARAZSLAT,7,3,field.getX(),field.getY(),1,3,false,50);
            else if (random >28) items.addItem("Íj",IJ,6,1,field.getX(),field.getY(),1,1,false,60);
            else if (random >25) items.addItem("Tőr",TOR,4,1,field.getX(),field.getY(),1,1,false,40);
            else if (random >22) items.addItem("Balta", BALTA,2,1,field.getX(),field.getY(),1,1,false,20);
        }
        
        return items;
    }
    
    private ItemList loadItems(String filename, String suffix){
        ItemList items = new ItemList();
        
        try(BufferedReader br = new BufferedReader(new FileReader("src\\saves\\savegames\\"+filename+"\\"+filename+"."+suffix))){
            String row;
            while((row = br.readLine())!=null){
                String[] tmp = row.split(" ");
                items.addItem(tmp[0],ItemType.valueOf(tmp[1]),
                        Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3]),
                        Integer.parseInt(tmp[4]),Integer.parseInt(tmp[5]),
                        Integer.parseInt(tmp[6]),Integer.parseInt(tmp[7]),
                        Boolean.parseBoolean(tmp[8]),Integer.parseInt(tmp[9]));
            }
            br.close();
        }catch(Exception e){
            if(suffix.equals("items-map")) error(7); //Hiba betöltésnél
            else error(8);
        }
        
        return items;
    }
    
    private Player loadPlayer(String filename){
        Player player = null;
        try(BufferedReader br = new BufferedReader(new FileReader("src\\saves\\savegames\\"+filename+"\\"+filename+".game-map"))){
            String in = br.readLine();
            br.close();
            String[] tmp = in.split(" ");
            player = new Player(tmp[0],Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),
                        Integer.parseInt(tmp[3]),Integer.parseInt(tmp[4]),
                        Integer.parseInt(tmp[5]),Integer.parseInt(tmp[6]),
                        NPCType.valueOf(tmp[7]),Boolean.parseBoolean(tmp[8]),
                        Boolean.parseBoolean(tmp[9]));
        }catch(Exception e){
            error(9); //Hiba betöltésnél
        }
        
        return player;
    }
         
    private void createPlayer(){
        frame.setVisible(false);
        JFrame createPlayer = new JFrame("Játékos készítése");
        createPlayer.setSize(400,300);
        createPlayer.setResizable(false);
        createPlayer.setVisible(true);
        createPlayer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        createPlayer.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                closeGame();
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = width / 20; gbc.ipady = heigth / 20;
        gbc.fill = gbc.HORIZONTAL;
        gbc.insets = new Insets(6,6,6,6);
        
        x = 0; y = 0;
        
        gbc.gridwidth = 2;
        JLabel label = new JLabel("Úgy látom, még nincs főhősöd. Készítsünk el egyet gyorsan!");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.gridwidth = 1;
        label = new JLabel("Játékos neve:");
        gbc.gridx = x++; gbc.gridy = y;
        panel.add(label,gbc);
        
        JTextField jatekosNeve = new JTextField();
        gbc.gridwidth = 1;
        gbc.gridx = x--; gbc.gridy = y++;
        panel.add(jatekosNeve,gbc);
        
        gbc.gridwidth = 2;
        
        JButton rendben = new JButton("Elfogadom");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(rendben,gbc);
        rendben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!(jatekosNeve.getText().equals(""))){
                    String nehezsegTipp = 
                            (nehezseg.equals("Barátságos") ? 
                            "Ahogy nézem, barátságos nehézségi szintet állítottál be. "
                            + "Megnyugtatlak: ezen a szinten egyik ellenségnek sem lesz ereje. :)"
                            + " Mindenkit lekaszabolhatsz, ahogy csak akarsz. Gyakorlásnak tökéletes" :
                            (nehezseg.equals("Könnyű") ? 
                            "Könnyű nehézség? Előnnyel indulsz az ellenségekhez képest. "
                            + "A legkeményebbeket leszámítva mindenkit megversz." :
                            (nehezseg.equals("Közepes") ? 
                            "Közepes nehézségi szintről indítod a játékot. Nem kapsz semmi "
                            + "előnyt az ellenségekhez képest, és ők is alapértelmezett erőről indulnak. "
                            + "Sok sikert!" : 
                            (nehezseg.equals("Nehéz") ? 
                            "Bátor dolog nehéz fokozaton indulni. A saját statisztikáid "
                            + "alapértelmezettek lesznek, de az ellenségek kicsit "
                            + "erősebbek lesznek. Kösd fel a nadrágot!" :
                            "Extrém? Ez igen, igazi hőssel találkozhatunk. "
                            + "Az ellenfelek még erősebbek, mint alapból, illetve a te erőid is gyengítve vannak. "
                            + "Nézzük, ki lesz az úr a háznál!"))));
                    JOptionPane.showMessageDialog(createPlayer,"Üdvözlünk a játékban. A játékot a WASD gombokkal tudod "
                            + "irányítani, csak vízszintesen és függőlegesen. Átlósan nem tudsz mozogni.\n"
                            + nehezsegTipp);
                    int x = -1, y = -1;
                    for(PlayField field : map.getMap()){
                        if(field.getX()>245&&field.getX()<255&&
                                field.getY()>245&&field.getY()<2551&&
                                field.getType()==PATH){
                            x = field.getX();
                            y = field.getY();
                        }
                    }
                    if(x == -1 || y == -1){
                        x = 0; y = 0;
                    }
                    Player createdPlayer = new Player(
                            jatekosNeve.getText().toString(),
                            3,
                            1,
                            150,
                            10,
                            x,y,PLAYER,true,false);
                    initializePlayer(createdPlayer);
                    createPlayer.dispose();
                    frame.setVisible(true);
                    createWindow();
                }
                else{
                    JOptionPane.showMessageDialog(createPlayer,"A játékos neve nem lehet üres!");
                }
            }
        });
        
        createPlayer.add(panel);
        createPlayer.revalidate();
        createPlayer.repaint();
    }
    
    private void initializePlayer(Player player){
        this.player = player;
    }
    
    private void saveGame(String filename){
        pleaseWait("Játékmentés folyamatban","A játék elmentése folyamatban van. Kérjük, várj...");
        //Elmentjük a mapot
        File theDir = new File("src\\saves\\savegames\\"+filename);
        if(!theDir.exists())
            theDir.mkdir();
        try(PrintWriter pw = new PrintWriter(new File("src\\saves\\savegames\\"+filename+"\\"+filename+".game-map"))){
            for(int x = 0; x<500;x++){
                for(int y = 0; y<500;y++){
                    boolean over = false;
                    for(int z = 0; z<map.getMap().size()&&!over;z++){
                        if(map.getMap().get(z).getX()==x && map.getMap().get(z).getY() == y){
                            pw.print(map.getMap().get(z).getType().toString());
                            over = true;
                        }
                    }
                }
                pw.println();
            }
            pw.close();
        }catch(Exception e){ 
            error(10); }
        //Most jönnek az itemek
        try(PrintWriter pw = new PrintWriter(new File("src\\saves\\savegames\\"+filename+"\\"+filename+".items-map"))){
            for(Item item : items.getItems()){
                pw.println(item.toString());
            }
            pw.close();
        }catch(Exception e){
            error (11);
        }
        //Majd a játékos itemjei
        try(PrintWriter pw = new PrintWriter(new File("src\\saves\\savegames\\"+filename+"\\"+filename+".items-own"))){
            for(Item item : player.getItems().getItems()){
                pw.println(item.toString());
            }
            pw.close();
        }catch(Exception e){
            error (12);
        }
        //Az NPC-k
        try(PrintWriter pw = new PrintWriter(new File("src\\saves\\savegames\\"+filename+"\\"+filename+".npc"))){
            for(NPC npc : npcs){
                pw.println(npc.toString());
            }
            pw.close();
        }catch(Exception e){
            error (13);
        }
        //Végül a játékos adatai
        try(PrintWriter pw = new PrintWriter(new File("src\\saves\\savegames\\"+filename+"\\"+filename+".npc"))){
            pw.println(player.toString());
            pw.close();
        }catch(Exception e){
            error (13);
        }
        
        try(PrintWriter pw = new PrintWriter(new File("src\\saves\\saves.txt"))){
            pw.print(filename);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            pw.println(" " + dateFormat.format(date));
            pw.close();
        }catch(Exception e){
            error (14);
        }
        
        
        super.pw.dispose();
    }
    
    private void drawArea(){
        int playerPosX = player.getX();
        int playerPosY = player.getY();
        
        int lowerX = playerPosX - 4, higherX = playerPosX + 4,
            lowerY = playerPosY - 4, higherY = playerPosY + 4;
        visibleArea = getArea(lowerX, higherX, lowerY, higherY);
        
        String k = "";
    }
    
    private PlayField[][] getArea(int LX,int HX,int LY,int HY){
        PlayField[][] playfield = new PlayField[9][9];
        int x = 0, y = 0;
        for(int a = LX; a <=HX; a++){
            for(int b = LY; b<=HY; b++){
                boolean wait = false;
                while(!wait){
                    for(int c = 0; c<map.getMap().size()&&!wait;c++){
                        if(map.getMap().get(c).getX()==a&&map.getMap().get(c).getY()==b){
                            playfield[x][y] = map.getMap().get(c);
                            y++;
                            if(y==9){
                                y=0; x++;
                            }
                            wait = true;
                        }
                    }

                }
            }
        }
        return playfield;
    }
}