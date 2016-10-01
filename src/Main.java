
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Program written by Joseph Straceski.
 * @author Joseph Straceski 
 * Contact at web: <https://github.com/Crepox>
 * e-mail: IpwndU360@gmail.com
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Window w = new Window("Pseudo3D");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setBounds(0, 0, 800, 600);
        w.setVisible(true);
        new Thread(w.e).start();
    }

}
