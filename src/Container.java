
import java.util.ArrayList;

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
public class Container {
    ArrayList<Container> children = new ArrayList<Container>();
    ArrayList<Line> lines = new ArrayList<Line>();
    ArrayList<Vector3f> points = new ArrayList<Vector3f>();
    Vector3f rot = new Vector3f();
    Vector3f position = new Vector3f();
}
