
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Program written by Joseph Straceski.
 *
 * @author Joseph Straceski Contact at web: <https://github.com/Crepox>
 * e-mail: IpwndU360@gmail.com
 */
public class Window extends JFrame implements KeyListener {

    boolean[] keys = new boolean[256];;
    JPanel screen = new JPanel();;
    Engine e;

    public Window(String s) {
        super(s);
        add(screen);
        e = new Engine(this); 
        addKeyListener(this);
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
