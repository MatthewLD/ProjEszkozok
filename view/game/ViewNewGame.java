package view.game;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import view.View;
import view.ViewMainMenu;

public class ViewNewGame extends View{
    public ViewNewGame(){
        super.createBasics();
        createWindow();
    }
    
    private void createWindow(){
        gbc.insets = new Insets(0,0,0,0);
        gbc.ipadx /= 2; gbc.ipady /=2;
        gbc.gridwidth = 2;
        
        label = new JLabel("Új játék kezdése");
        gbc.gridx = x; gbc.gridy = y++;
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label,gbc);
        
        label = new JLabel("Válassz nehézségi szintet, majd játékmódot!");
        gbc.gridx = x; gbc.gridy = y++;
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label,gbc);
        
        gbc.gridwidth = 1;
        gbc.weightx = 2.0;
        bG = new ButtonGroup();

        rbutton = new JRadioButton("Hagyjatok békén...");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(rbutton,gbc);
        bG.add(rbutton);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Az ellenség nem vesz komolyan)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton easy = new JRadioButton("Gomolyfelhő");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(easy,gbc);
        bG.add(easy);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Lehetőleg ne öld meg magad.)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton medium = new JRadioButton("Adjatok neki");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(medium, gbc);
        bG.add(medium);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Az ellenség előbb támad, aztán kérdez)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton hard = new JRadioButton("Kicsit se kímélj!");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(hard, gbc);
        bG.add(hard);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Nem gyengéknek való vidék)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton extreme = new JRadioButton("Rémálom");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(extreme, gbc);
        bG.add(extreme);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Csak igazi kalandoroknak javasolt)");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.gridwidth = 2;
        
        button = new JButton("Játék indítása");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selected =
                        (rbutton.isSelected() ? "Barátságos" :
                        (easy.isSelected() ? "Könnyű" : 
                        (medium.isSelected() ? "Közepes" :
                        (hard.isSelected() ? "Nehéz" :
                        (extreme.isSelected() ? "Extrém" :
                        null)))));
                if(selected == null){
                     JOptionPane.showMessageDialog(frame,"Nem választottál nehézségi szintet!");
                }
                else{
                    pleaseWait("Elkészítés","Kérlek, várj. A játék elkészítése folyamatban...");
                    
                    frame.getContentPane().removeAll();
                    new ViewGame(selected,null);

                    pw.dispose();
                }
            }
        });
        
        button = new JButton("Vissza");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
           frame.getContentPane().removeAll();
           new ViewMainMenu();
        });
        
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
