
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Engine implements Runnable {

    Window w;
    JPanel s;

    float width;
    float height;
    float distance;
    float angleX = 0, angleY = 0;
    float angleXvel = 0, angleYvel = 0;
    float maxRvel = 0.6f;
    float near = 3f;
    float nearToObject = -2f;
    float scaleFactor;
    float damping = 0.985f;

    boolean[] keys;
    Vector3f a, b, globalShift, globalVelocity;
    ArrayList<Vector3f> points = new ArrayList<Vector3f>();
    ArrayList<Line> lines = new ArrayList<Line>();
    ArrayList<Container> containers = new ArrayList<Container>();

    float acceleration = 0.0001f;
    float speed = 0.001f;
    int radius = 5;

    public Engine(Window window) {
        w = window;
        s = window.screen;
        keys = window.keys;
    }

    public void run() {
        init();
        while (true) {
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (keys[KeyEvent.VK_ESCAPE]) {
                break;
            }
            containers.get(1).rot.y += 0.001f;
            containers.get(1).children.get(0).children.get(0).position.z 
                    = 1.0f + (float)(Math.cos(containers.get(1).rot.y) * -0.5f); 
            containers.get(1).children.get(0).children.get(0).rot.z += 0.001f;
            containers.get(1).children.get(0).children.get(0).children.get(0).rot.z += 0.001f;
            
            if (keys[KeyEvent.VK_A]) {
                globalVelocity.z -= Math.cos(Math.toRadians(angleY + 90)) * acceleration;
                globalVelocity.x += Math.sin(Math.toRadians(angleY + 90)) * acceleration;
            }
            if (keys[KeyEvent.VK_D]) {
                globalVelocity.z -= Math.cos(Math.toRadians(angleY - 90)) * acceleration;
                globalVelocity.x += Math.sin(Math.toRadians(angleY - 90)) * acceleration;
            }
            if (keys[KeyEvent.VK_W]) {
                globalVelocity.z -= Math.cos(Math.toRadians(angleY)) * acceleration;
                globalVelocity.x += Math.sin(Math.toRadians(angleY)) * acceleration;
            }
            if (keys[KeyEvent.VK_S]) {
                globalVelocity.z -= Math.cos(Math.toRadians(angleY + 180)) * acceleration;
                globalVelocity.x += Math.sin(Math.toRadians(angleY + 180)) * acceleration;
            }
            globalVelocity.x *= damping;
            globalVelocity.z *= damping;
            
            globalShift.x += globalVelocity.x;
            globalShift.z += globalVelocity.z;
            

            if (keys[KeyEvent.VK_Q]) {
                angleYvel += speed;
            }

            if (keys[KeyEvent.VK_E]) {
                angleYvel -= speed;
            }

            if (keys[KeyEvent.VK_R]) {
                angleXvel -= speed;
            }

            if (keys[KeyEvent.VK_F]) {
                angleXvel += speed;
            }

            angleXvel = Math.min(angleXvel, maxRvel);
            angleYvel = Math.min(angleYvel, maxRvel);

            angleYvel *= 0.98;
            angleXvel *= 0.98;

            angleX += angleXvel;
            angleY += angleYvel;

            render();
        }
        System.exit(0);
    }

    public void init() {
        //points.add(new Vector3f(0, 0, 1));
        //points.add(new Vector3f(0, 0, 2));
        //lines.add(new Line(new Vector3f(0, 0, 1),new Vector3f(0, 0, 2)));

        Container container2 = new Container();
        container2.position = new Vector3f(0, 0, 3.0f);
//        container2.lines.add(new Line(new Vector3f(-1, -1,-1), new Vector3f(0,0,0)));
//        container2.lines.add(new Line(new Vector3f(0, -1,0), new Vector3f(0,0,0)));
//        container2.lines.add(new Line(new Vector3f(0, 0,-1), new Vector3f(0,0,0)));
//        container2.lines.add(new Line(new Vector3f(-1, 0, 0), new Vector3f(0,0,0)));
        
        addCube(container2, new Vector3f(0, 0, 0), 1.0f);
        

        Container container3 = new Container();
        container3.position = new Vector3f(0, 0, 0.5f);
        addCube(container3, new Vector3f(0, 0, 0), 0.2f);

        Container container4 = new Container();
        container4.position = new Vector3f(0, 0, 0.5f);
        addCube(container4, new Vector3f(0, 0, 0), 0.1f);
        container4.points.add(new Vector3f(0,0,2));
        container4.lines.add(new Line(new Vector3f(0,0,2), new Vector3f(0,0,0)));
        Container container5 = new Container();
        container5.position = new Vector3f(0,0,2);
        addCube(container5, new Vector3f(0, 0, 0), 0.05f);
        
        container5.lines.add(new Line(new Vector3f(0, 0.2f, 0), new Vector3f(0,0,0)));
        container5.lines.add(new Line(new Vector3f(0.2f, 0, 0), new Vector3f(0,0,0)));
        container5.lines.add(new Line(new Vector3f(0, -0.2f, 0), new Vector3f(0,0,0)));
        container5.lines.add(new Line(new Vector3f(-0.2f, 0, 0), new Vector3f(0,0,0)));
        
        container4.children.add(container5);
        container3.children.add(container4);
        container2.children.add(container3);
        
        
        
        //addFace(new Vector3f(0, -1, 1.5f), 2, 0.5f);
        //addFace(new Vector3f(0, -1, 1.5f), 1, 0.5f);
        //addFace(new Vector3f(1.5f, -1, 1.5f), 0.5f, 0.5f);
        //addFace(new Vector3f(0, 1, 0), 2);
        Vector3f c = new Vector3f(1, 1, -2);
        Vector3f d = new Vector3f(-1, 1, -2);
        Vector3f b = new Vector3f(1, 1, 2);
        Vector3f a = new Vector3f(-1, 1, 2);
        Vector3f e = new Vector3f(1, 1, 3);
        Vector3f f = new Vector3f(-1, 1, 3);

        Container container = new Container();

        container.points.add(a);
        container.points.add(b);
        container.points.add(c);
        container.points.add(d);
        container.points.add(e);
        container.points.add(f);

        //lines.add(new Line(a, b));
        container.lines.add(new Line(b, c));
        container.lines.add(new Line(e, b));
        //lines.add(new Line(c, d));
        container.lines.add(new Line(f, a));
        container.lines.add(new Line(d, a));

        containers.add(container);
        containers.add(container2);

        width = s.getWidth();
        height = s.getHeight();
        globalShift = new Vector3f(0, 0, 0);
        globalVelocity = new Vector3f(0, 0, 0);
        scaleFactor = width / 4;
        angleX = 0;
    }

    public void render() {

        BufferedImage i = new BufferedImage(s.getWidth(),
                s.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = i.getGraphics();

        double radX = Math.PI * angleX / 180.0;
        double radY = Math.PI * angleY / 180.0;

        Vector3f rot = new Vector3f((float) radX, (float) radY, 0f);
        ArrayList<Container> newList = new ArrayList<Container>();
        //newList = (ArrayList<Container>) containers.clone();

        for (Container container : containers) {
            container = updateContainer(container);
            shiftContainer(container, globalShift);
            rotateContainer(container, rot);
            newList.add(container);
        }

        for (Container container : newList) {
            drawContainer(g, container);
        }

        s.getGraphics().drawImage(i, 0, 0, null);
    }

    public void drawPoint(Graphics g, Vector3f v) {
        float zfactor = v.z + near + nearToObject;

        int vx, vy;

        if (zfactor <= 0) {
            return;
        }
        
        int newRadius = (int) ((radius * 2 / zfactor) + 0.5f);
        
        vx = (int) (((v.x * near * scaleFactor / zfactor) + width / 2) + 0.5);
        vy = (int) (((v.y * near * scaleFactor / zfactor) + height / 2) + 0.5);

        g.drawOval(vx - newRadius, vy - newRadius, newRadius * 2, newRadius * 2);
    }

    public void drawLine(Graphics g, Line l) {

        float az = l.a.z + near + nearToObject;
        float bz = l.b.z + near + nearToObject;
        float ax = l.a.x;
        float bx = l.b.x;
        float ay = l.a.y;
        float by = l.b.y;

        if (az <= 0 && bz <= 0) {
            return;
        } else if (az <= 0) {
            float abx = ax - bx;
            float aby = ay - by;
            float abz = az - bz;
            // (bz + abz*t = 0.01f);
            float t = (0.01f - bz) / abz;

            ay = by + aby * t;
            ax = bx + abx * t;
            az = 0.01f;
        } else if (bz <= 0) {
            float bax = bx - ax;
            float bay = by - ay;
            float baz = bz - az;
            // (az + baz*t = 0.01f);
            float t = (0.01f - az) / baz;

            by = ay + bay * t;
            bx = ax + bax * t;
            bz = 0.01f;
        }

        int vax = (int) (((ax * near * scaleFactor / az) + width / 2) + 0.5);
        int vay = (int) (((ay * near * scaleFactor / az) + height / 2) + 0.5);

        int vbx = (int) (((bx * near * scaleFactor / bz) + width / 2) + 0.5);
        int vby = (int) (((by * near * scaleFactor / bz) + height / 2) + 0.5);

        g.drawLine(vax, vay, vbx, vby);
    }

    float cy, cx, ncx, sy, sx, cxcy, cxsy, sxcy, sxsy, cz, sz;

    public void updateRot(Vector3f rot) {
        cx = (float) Math.cos(rot.x);
        cy = (float) Math.cos(rot.y);
        cz = (float) Math.cos(rot.z);
        sz = (float) Math.sin(rot.z);
        sx = (float) Math.sin(rot.x);
        sy = (float) Math.sin(rot.y);

//        cxcy = cx * cy;
//        cxsy = cx * sy;
//        sxcy = sx * cy;
//        sxsy = sx * sy;
    }

    public Vector3f rotPoint(Vector3f v) {

        float bx = +((cy * cz) * v.x) - ((sz * 1f) * v.y) + ((sy * 1f) * v.z) ;
        float by = -((sy * sx) * v.x) + (sz * v.x) + ((cx * cz) * v.y) + ((cy * sx) * v.z);
        float bz = -((sy * cx) * v.x) - ((sx * 1f) * v.y) + ((cy * cx) * v.z);

        //System.out.println(by);
        
        return new Vector3f(bx, by, bz);
    }

    public void shiftContainer(Container c, Vector3f shift) {
        for (int index = 0; index < c.points.size(); index++) {
            c.points.set(index, Vector3f.add(c.points.get(index), shift));
        }
        for (int index = 0; index < c.lines.size(); index++) {
            c.lines.get(index).a = Vector3f.add(c.lines.get(index).a, shift);
            c.lines.get(index).b = Vector3f.add(c.lines.get(index).b, shift);
        }
        for (int index = 0; index < c.children.size(); index++) {
            shiftContainer(c.children.get(index), shift);
        }
    }

    public void rotateContainer(Container c, Vector3f rot) {
        updateRot(rot);

        for (int index = 0; index < c.points.size(); index++) {
            c.points.set(index, rotPoint(c.points.get(index)));
        }
        for (int index = 0; index < c.lines.size(); index++) {
            c.lines.get(index).a = rotPoint(c.lines.get(index).a);
            c.lines.get(index).b = rotPoint(c.lines.get(index).b);
        }
        for (int index = 0; index < c.children.size(); index++) {
            rotateContainer(c.children.get(index), Vector3f.add(rot, c.rot));
        }
    }

    public Container updateContainer(Container c) {
        Container c2 = new Container();
        for (int index = 0; index < c.children.size(); index++) {
            Container container = c.children.get(index);
            container = updateContainer(container);
            rotateContainer(container, c.rot);
            c2.children.add(index, container); 
        }

        updateRot(c.rot);

        for (int index = 0; index < c.points.size(); index++) {
            Vector3f a = rotPoint(c.points.get(index));
            c2.points.add(index, a);
        }

        for (int index = 0; index < c.lines.size();      index++) {
            Line l = c.lines.get(index);
            Vector3f a = rotPoint(l.a);
            Vector3f b = rotPoint(l.b);
            c2.lines.add(index, new Line(a, b));
        }

        shiftContainer(c2, c.position);

        return c2;
    }

    public void drawContainer(Graphics g, Container c) {
        for (Vector3f point : c.points) {
            drawPoint(g, point);
        }
        for (Line line : c.lines) {
            drawLine(g, line);
        }
        for (Container container : c.children) {
            drawContainer(g, container);
        }
    }

    

    public Vector3f shiftPoint(Vector3f v, Vector3f s) {
        return new Vector3f(v.x + s.x, v.y + s.y, v.z + s.z);
    }

    public boolean isInside(Vector3f a) {
        return a.xi < width / 2
                && a.xi > 0
                && a.yi < height / 2
                && a.yi > 0;
    }

    public void addCube(Container c, Vector3f a, float s) {
        Vector3f btr = new Vector3f(a.x + s, a.y + s, a.z + s);
        Vector3f bbr = new Vector3f(a.x + s, a.y - s, a.z + s);
        Vector3f btl = new Vector3f(a.x - s, a.y + s, a.z + s);
        Vector3f bbl = new Vector3f(a.x - s, a.y - s, a.z + s);
        Vector3f ftr = new Vector3f(a.x + s, a.y + s, a.z - s);
        Vector3f fbr = new Vector3f(a.x + s, a.y - s, a.z - s);
        Vector3f ftl = new Vector3f(a.x - s, a.y + s, a.z - s);
        Vector3f fbl = new Vector3f(a.x - s, a.y - s, a.z - s);

        c.points.add(btr);
        c.points.add(bbr);
        c.points.add(btl);
        c.points.add(bbl);
        c.points.add(ftr);
        c.points.add(fbr);
        c.points.add(ftl);
        c.points.add(fbl);

        //back face
        c.lines.add(new Line(btr, btl));
        c.lines.add(new Line(btl, bbl));
        c.lines.add(new Line(bbl, bbr));
        c.lines.add(new Line(bbr, btr));

        //front face
        c.lines.add(new Line(ftr, ftl));
        c.lines.add(new Line(ftl, fbl));
        c.lines.add(new Line(fbl, fbr));
        c.lines.add(new Line(fbr, ftr));

        //connectors
        c.lines.add(new Line(ftr, btr));
        c.lines.add(new Line(ftl, btl));
        c.lines.add(new Line(fbl, bbl));
        c.lines.add(new Line(fbr, bbr));

    }

    public void addFace(Vector3f a, float x, float z) {
        Vector3f tr = new Vector3f(a.x - x, a.y, a.z + z);
        Vector3f tl = new Vector3f(a.x + x, a.y, a.z + z);
        Vector3f bl = new Vector3f(a.x + x, a.y, a.z - z);
        Vector3f br = new Vector3f(a.x - x, a.y, a.z - z);

        points.add(tr);
        points.add(br);
        points.add(tl);
        points.add(bl);

        //face
        lines.add(new Line(tr, tl));
        lines.add(new Line(tl, bl));
        lines.add(new Line(bl, br));
        lines.add(new Line(br, tr));

    }

    public void addFace(Vector3f a, float s) {
        Vector3f tr = new Vector3f(a.x - s, a.y, a.z + s);
        Vector3f tl = new Vector3f(a.x + s, a.y, a.z + s);
        Vector3f bl = new Vector3f(a.x + s, a.y, a.z - s);
        Vector3f br = new Vector3f(a.x - s, a.y, a.z - s);

        points.add(tr);
        points.add(br);
        points.add(tl);
        points.add(bl);

        //face
        lines.add(new Line(tr, tl));
        lines.add(new Line(tl, bl));
        lines.add(new Line(bl, br));
        lines.add(new Line(br, tr));

    }
}
