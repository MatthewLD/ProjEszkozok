package view.error;

import javax.swing.JOptionPane;
import view.View;

public final class ViewError extends View{
    public static void error(int errorCode){
        switch(errorCode){
            case 1:{
                JOptionPane.showMessageDialog(null,"Hiba történt a játék inicializálása során. A program most leáll!","Hiba!",0);
                System.exit(0);
                /*
                A játék indításánál, mikor ellenőrzi, hogy milyen felbontás, vagy teljes képernyő-e.
                Esetek:
                    - A fájl nem létezik.
                    - A fájl módosítva lett olyan formában, amelyet a programkód nem tud lekezelni.
                */
            }
            case 2:{
                JOptionPane.showMessageDialog(null,"Hiba történt a felbontás megváltoztatása közben. A program most leáll!","Hiba!",0);
                System.exit(0);
                /*
                A beállításokból, miközben a felbontást szeretné az ember megváltoztatni.
                Esetek:
                    - A fájl nem létezik.
                    - A fájl módosítva lett olyan formában, amelyet a programkód nem tud lekezelni.
                */
            }
            case 3:{
                JOptionPane.showMessageDialog(null,"Hiba történt a játékmentések betöltése közben. A program most leáll!","Hiba!",0);
                System.exit(0);
                /*
                A játékmentések betöltésénél, mikor a lehetséges mentéseket akarja betölteni a játék.
                Esetek:
                    - A fájl nem létezik.
                    - A fájl módosítva lett olyan formában, amelyet a programkód nem tud lekezelni.
                */
            }
        }
    }
}
