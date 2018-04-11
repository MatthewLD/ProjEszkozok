package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import static view.error.ViewError.error;

public abstract class View {
    protected static JFrame frame;
    
    protected static boolean first_run = true;
    protected static boolean fullscreen;
    protected static int width;
    protected static int heigth;
    
    protected JPanel panel;
    protected GridBagConstraints gbc;
    protected JLabel label;
    protected JButton button;
    protected JRadioButton rbutton;
    protected ButtonGroup rbuttongroup;
    protected JComboBox comboBox;
    
    protected int x = 1;
    protected int y = 1;
    
    
    protected void atFirstRun(){
        try (BufferedReader br = new BufferedReader(new FileReader("src\\view\\config\\config.txt"))){
            String row; boolean over = false;
            while((row=br.readLine())!=null&&!over){
                if(row.split(":")[0].equals("Fullscreen")){
                    if(row.split(":")[1].equals("true")){
                        fullscreen = true;
                        width = -1;
                        heigth = -1;
                        over = true;
                    }
                    else{
                        fullscreen = false;
                        width = Integer.parseInt(br.readLine().split(":")[1]);
                        heigth = Integer.parseInt(br.readLine().split(":")[1]);
                        over = true;
                    }
                }
            }
            br.close();
            first_run = false;
        }
        catch(IOException|NumberFormatException e){
            error(1);
        }
        createFrame();
    }
    
    protected void createFrame(){
        frame = new JFrame("Szerepjáték");
        if(fullscreen){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        } else{
            frame.setSize(width,heigth);
            frame.setResizable(false);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(
                    dim.width/2-frame.getSize().width/2,
                    dim.height/2-frame.getSize().height/2);
        }
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                closeGame();
            }
        });
    }
    
    protected void createBasics(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        gbc = new GridBagConstraints();
        if(fullscreen){
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            gbc.ipadx = (dim.width/2-frame.getSize().width/2)/10;
            gbc.ipady = (dim.height/2-frame.getSize().height/2)/10;
        }
        else{
            gbc.ipadx = width / 10; gbc.ipady = heigth / 10;
        }
        gbc.fill = gbc.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);
    }
    
    protected void closeGame(){
        String[] options = {"Igen","Nem"};
        if(JOptionPane.showOptionDialog(
                frame,
                "Biztos be akarod zárni a játékot?",
                "Játék bezárása.",
                JOptionPane.YES_NO_OPTION,
                0,
                null,
                options,
                null)
                    == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    protected void changeResolution(boolean full_or_not, int width, int heigth){
        if(full_or_not){
            fullscreen =  full_or_not;
            View.heigth = (width = -1);
        }else{
            fullscreen = false;
            View.width = width;
            View.heigth = heigth;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\view\\config\\config.txt"));
            ArrayList<String> file = new ArrayList<>();
            String row;
            while((row=br.readLine())!=null) file.add(row);
            br.close();
            boolean over = false;
            for(int i=0;i<file.size()&&!over;i++){
                if(file.get(i).split(":")[0].equals("Fullscreen")){
                    if(fullscreen){
                        file.add(i,"Fullscreen:true");
                        file.remove(i+1);
                        file.add(i+1,"ResWidth:-1");
                        file.remove(i+2);
                        file.add(i+2,"ResHeigth:-1");
                        file.remove(i+3);
                    }
                    else{
                        file.add(i,"Fullscreen:false");
                        file.remove(i+1);
                        file.add(i+1,"ResWidth:"+width);
                        file.remove(i+2);
                        file.add(i+2,"ResHeigth:"+heigth);
                        file.remove(i+3);
                    }
                    over = true;
                }
            }
            PrintWriter bw = new PrintWriter(new FileWriter("src\\view\\config\\config.txt"));
            file.forEach((out_write) -> {bw.println(out_write);});
            bw.close();
        }
        catch(IOException | NumberFormatException e){
            error(2);
        }
    }
}
