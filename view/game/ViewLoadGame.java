package view.game;

import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
            
            gbc.gridwidth = 1;
        }else{
            x = 0;
            label = new JLabel("Játékállás betöltése.");
            gbc.gridx = x; gbc.gridy = y++;
            gbc.gridwidth = 5;
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label,gbc);
            
            gbc.insets = new Insets(2,2,2,2);
            gbc.gridwidth = 1; 
            
            for(String[] array : saves){
                label = new JLabel(array[0]);
                gbc.gridx = x++; gbc.gridy = y;
                panel.add(label,gbc);
                
                label = new JLabel(array[1]);
                gbc.gridx = x++; gbc.gridy = y;
                panel.add(label,gbc);
                
                button = new JButton("Betölt");
                gbc.gridx = x++; gbc.gridy = y;
                panel.add(button,gbc);
                button.addActionListener(actionEvent->{
                    //Itt indítja majd el a játékot.
                    
                    pleaseWait("Betöltés","Kérlek, várj. A játék betöltése folyamatban...");
                    
                    frame.getContentPane().removeAll();
                    new ViewGame(null, array[0]);
                    
                    pw.dispose();
                    //JOptionPane.showMessageDialog(null,"Itt majd betölti a játékot!","Hiba!",0);
                });
                
                button = new JButton("Törlés");
                gbc.gridx = x++; gbc.gridy = y;
                panel.add(button,gbc);
                button.addActionListener(actionEvent->{
                    String[] options = {"Igen","Nem"};
                    if(JOptionPane.showOptionDialog(
                        frame,
                        "Biztos törölni szeretnéd a játékállást? Ezzel az állapot végleg elvész!",
                        "Játékmentés törlése.",
                        JOptionPane.YES_NO_OPTION,
                        0,
                        null,
                        options,
                        null)
                            == JOptionPane.YES_OPTION){
                        
                        try(BufferedReader br = new BufferedReader(new FileReader("src\\saves\\saves.txt")))
                        {
                            File file = new File("src\\saves\\savegames\\"+array[0]+"\\"+array[0]+".save");
                            file.delete();
                            String row;
                            ArrayList<String> toWriteOut = new ArrayList<>();
                            while((row=br.readLine())!=null) if(!row.equals(array[0])) toWriteOut.add(row);
                            file = new File("src\\saves\\saves.txt");
                            file.delete();
                            PrintWriter bw = new PrintWriter(new FileWriter("src\\saves\\saves.txt"));
                            for(String out : toWriteOut) bw.println(out);
                            bw.close();
                        }catch(Exception e){error(3);}
                        frame.getContentPane().removeAll();
                        new ViewLoadGame();
                    }
                });
                
                x = 0; y++;
            }
            x = 1;
            gbc.gridwidth = 3;
        }
        button = new JButton("Vissza");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent-> {
            frame.getContentPane().removeAll();
            new ViewMainMenu();
        });
        
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    private ArrayList<String[]> read(){
        try(BufferedReader br = new BufferedReader(new FileReader("src\\saves\\saves.txt"))){
            ArrayList<String[]> names = new ArrayList<>();
            String row;
            while((row = br.readLine())!=null){
                names.add(row.split(" "));
            }
            return names;
        }
        catch(Exception e){error(3);}
        return null;
    }
}
