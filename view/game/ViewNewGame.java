package view.game;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import view.View;
import view.ViewMainMenu;

public class ViewNewGame extends View{
    public ViewNewGame(){
        super.createBasics();
        createWindow();
    }
    
    private void createWindow(){
        gbc.insets = new Insets(1,1,1,1);
        gbc.ipadx = 1; gbc.ipady = 1;
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

        rbutton = new JRadioButton("Hagyjatok békén...");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(rbutton,gbc);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Az ellenség addig nem vesz komolyan,\nmíg meg nem támadod őket)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton easy = new JRadioButton("Gomolyfelhő");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(easy,gbc);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Lehetőleg ne öld meg magad.)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton medium = new JRadioButton("Adjatok neki");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(medium, gbc);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Az ellenség előbb támad, aztán kérdez)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton hard = new JRadioButton("Egyáltalán ne kímélj!");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(hard, gbc);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Nem gyengéknek való vidék)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        gbc.weightx = 2.0; x--;
        
        JRadioButton extreme = new JRadioButton("Rémálom");
        gbc.gridx = x; gbc.gridy = y;
        panel.add(extreme, gbc);
        
        gbc.weightx = 1.0;
        
        label = new JLabel("(Csak igazi kalandoroknak javasolt)");
        gbc.gridx = ++x; gbc.gridy = y++;
        panel.add(label,gbc);

        gbc.gridwidth = 2; x--;
        gbc.fill = GridBagConstraints.CENTER;
        
        label = new JLabel("Játékmód kiválasztása");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        JRadioButton story = new JRadioButton("Történet mód");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(story,gbc);
        
        JRadioButton sandbox = new JRadioButton("Szabad játék");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(sandbox,gbc);
        
        button = new JButton("Játék indítása");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
            
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
