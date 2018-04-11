package view.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import view.View;
import view.ViewMainMenu;

public class ViewOptions extends View{
    public ViewOptions(){
        super.createBasics();
        createWindow();
    }
    
    private void createWindow(){
        label = new JLabel("Beállítások");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(label,gbc);
        
        String[] choices = {"Teljes képernyő","Ablakmód"}; 
        comboBox = new JComboBox(choices);
        comboBox.setEditable(false);
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(comboBox,gbc);
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        if(fullscreen){
            comboBox.setSelectedItem("Teljes képernyő");
        }
        else{
            comboBox.setSelectedItem("Ablakmód");
        }
        
        String[] resolutions = {"640x480","800x600","1024x768","1280x720","1280x800","1280x1024","1366x768","1440x900","1600x900","1600x1200","1680x1050","1920x1080"};
        JComboBox resCombo = new JComboBox(resolutions);
        resCombo.setEditable(false);
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(resCombo,gbc);
        ((JLabel)resCombo.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        resCombo.setSelectedItem(width + "x" + heigth);
        if(fullscreen){
            resCombo.setEnabled(false);
        }
        
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(comboBox.getSelectedItem().toString().equals("Teljes képernyő")){
                    resCombo.setEnabled(false);
                }else{
                    resCombo.setEnabled(true);
                }
            }
        });
        
        button = new JButton("Beállítások mentése");
        gbc.gridx = x; gbc.gridy = y++;
        panel.add(button,gbc);
        button.addActionListener(actionEvent->{
            boolean fullscreen = comboBox.getSelectedItem().toString().equals("Teljes képernyő");
            changeResolution(
                        (fullscreen),
                        Integer.parseInt(resCombo.getSelectedItem().toString().split("x")[0]),
                        Integer.parseInt(resCombo.getSelectedItem().toString().split("x")[1]));
            frame.dispose();
            createFrame();
            new ViewOptions();
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
