package view;

import javax.swing.JButton;
import view.config.ViewOptions;
import view.game.ViewLoadGame;
import view.game.ViewNewGame;

public class ViewMainMenu extends View {
    public ViewMainMenu(){
        if(first_run) super.atFirstRun();
        super.createBasics();
        createWindow();
    }
    
    private void createWindow(){     
        x = 1; y = 0;
        
        button = new JButton("Új játék kezdése");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
            frame.getContentPane().removeAll();
            new ViewNewGame();
        });
        
        button = new JButton("Mentett játékállás betöltése");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
            frame.dispose();
            new ViewLoadGame();
        });
        
        button = new JButton("Beállítások");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
            frame.getContentPane().removeAll();
            new ViewOptions();
        });
        
        button = new JButton("Játék bezárása");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
            super.closeGame();
        });
        
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}