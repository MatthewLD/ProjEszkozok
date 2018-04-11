package view.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import view.View;
import view.ViewMainMenu;
import static view.error.ViewError.error;

public class ViewLoadGame extends View {
    public ViewLoadGame(){
        super.createBasics();
        createWindow();
    }
    
    private void createWindow(){
        ArrayList<String[]> saves = read();
        
        if(saves.isEmpty()){
            label = new JLabel("Nincs elérhető játékmentés.");
            gbc.gridx = x; gbc.gridy = y++;
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label,gbc);
            
            button = new JButton("Vissza");
            gbc.gridx = x; gbc.gridy = y++;
            panel.add(button,gbc);
            button.addActionListener(actionEvent-> {
               frame.getContentPane().removeAll();
               new ViewMainMenu();
            });
        }else{
            label = new JLabel("Játékállás betöltése.");
            gbc.gridx = x; gbc.gridy = y++;
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label,gbc);
            
            for(String[] array : saves){
                label = new JLabel(array[0]);
            }
        }
        
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    private ArrayList<String[]> read(){
        try(BufferedReader br = new BufferedReader(new FileReader("saves.txt"))){
            ArrayList<String[]> retVal = new ArrayList<>();
            String row;
            while((row=br.readLine())!=null)
                try(BufferedReader readSave = new BufferedReader(new FileReader("savegames\\"+row+"\\"+row+".save"))){
                    String[] tmp = new String[8];
                    String row2; int i=0;tmp[i++]=row;
                    while((row2=readSave.readLine())!=null) tmp[i++] = row2;
                    retVal.add(tmp);
                }
            return retVal;
        }catch(Exception e){error(3);}
        return null;
    }
}
