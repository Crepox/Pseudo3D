
import java.awt.Graphics;
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
public class Engine_Old implements Runnable {

    Window w;
    JPanel s;

    float width;
    float height;
    float distance;
    float angleX, angleY;
    float angleXvel = 0, angleYvel = 0;
    float maxRvel = 0.6f;

    boolean[] keys;
    Vector3f a, b, shift;
    ArrayList<Vector3f> points = new ArrayList<Vector3f>();
    ArrayList<Line> lines = new ArrayList<Line>();

    float speed = 0.001f;
    int radius = 5;

    public Engine_Old(Window window) {
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
                Logger.getLogger(Engine_Old.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (keys[KeyEvent.VK_ESCAPE]) {
                break;
            }

            if (keys[KeyEvent.VK_A]) {
                shift.x -= speed;
            }

            if (keys[KeyEvent.VK_D]) {
                shift.x += speed;
            }

            if (keys[KeyEvent.VK_W]) {
                shift.z -= speed;
            }

            if (keys[KeyEvent.VK_S]) {
                shift.z += speed;
            }

            if (keys[KeyEvent.VK_Q]) {
                angleXvel += speed;
            }

            if (keys[KeyEvent.VK_E]) {
                angleXvel -= speed;
            }

            if (keys[KeyEvent.VK_R]) {
                angleYvel -= speed;
            }

            if (keys[KeyEvent.VK_F]) {
                angleYvel += speed;
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

        addCube(new Vector3f(0, 0, 3), 1);
        //addFace(new Vector3f(0, -1, 1.5f), 2, 0.5f);
        //addFace(new Vector3f(0, -1, 1.5f), 1, 0.5f);
        //addFace(new Vector3f(1.5f, -1, 1.5f), 0.5f, 0.5f);

        //addFace(new Vector3f(0, 1, 0), 2);
        Vector3f c = new Vector3f(0, 1, -2);
        Vector3f d = new Vector3f(2, 1, 0);
        Vector3f b = new Vector3f(-2, 1, 0);
        Vector3f a = new Vector3f(0, 1, 2);

        points.add(a);
        points.add(b);
        points.add(c);
        points.add(d);

        lines.add(new Line(a, b));
        lines.add(new Line(b, c));
        lines.add(new Line(c, d));
        lines.add(new Line(d, a));

        width = s.getWidth() / 2;
        height = s.getHeight() / 2;
        shift = new Vector3f(0, 0, 0);
        angleX = 0;
    }

    public void render() {

        BufferedImage i = new BufferedImage(s.getWidth(),
                s.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = i.getGraphics();
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, w.getWidth(), w.getHeight());
//        g.setColor(Color.BLACK);

        float radX = (float) Math.toRadians(angleX);
        float radY = (float) Math.toRadians(angleY);

        float cy = (float) Math.cos(radY);
        float cx = (float) Math.cos(radX);
        float sy = (float) Math.sin(radY);
        float sx = (float) Math.sin(radX);
        
        float cycx = cy * cx, cxsy = cx * sy,
                sxcy = sx * cy, sxsy = sx * sy;

        Vector3f forward = new Vector3f(-sxcy, sy, cycx);
        Vector3f up = new Vector3f(sxsy, cy, -cxsy);
        Vector3f right = new Vector3f(cx, 0.0f, sx);

        for (Vector3f p : points) {

            Vector2i p2 = modifyVector(right, up, forward, p);

            //int ar = Math.round(radius/p.z);
            int ar = radius;

            if (p2 != null) {
                g.drawOval(p2.x + s.getWidth() / 2 - ar,
                        p2.y + s.getHeight() / 2 - ar,
                        ar * 2,
                        ar * 2);
            }
        }

        for (Line l : lines) {

            Vector2i p1 = modifyVector(right, up, forward, l.a);
            Vector2i p2 = modifyVector(right, up, forward, l.b);

            boolean r = false;
            
            if (p1 == null && p2 != null) {
                r = true;
                p1 = modifyVector2(right, up, forward, l.a);
            } else if (p1 != null && p2 == null) {
                r = true;
                p2 = modifyVector2(right, up, forward, l.b);
            }

            if (p1 != null && p2 != null) {

                g.drawLine(p1.x + s.getWidth() / 2,
                        p1.y + s.getHeight() / 2,
                        p2.x + s.getWidth() / 2,
                        p2.y + s.getHeight() / 2);
            }
        }

        g.drawLine(s.getWidth() - 50,
                s.getHeight() - 50,
                Math.round(forward.x * 20) + s.getWidth() - 50,
                Math.round(forward.z * 20) + s.getHeight() - 50);

        g.drawLine(s.getWidth() - 50,
                s.getHeight() - 50,
                Math.round(right.x * 20) + s.getWidth() - 50,
                Math.round(right.z * 20) + s.getHeight() - 50);

        g.drawLine(s.getWidth() - 50,
                s.getHeight() - 50,
                Math.round(up.x * 20) + s.getWidth() - 50,
                Math.round(up.z * 20) + s.getHeight() - 50);

        s.getGraphics().drawImage(i, 0, 0, null);
    }

    public Vector2i modifyVector(Vector3f x, Vector3f y, Vector3f z, Vector3f a) {

        Vector3f v = Vector3f.add(a, shift);

        float nx = Vector3f.dot(v, x);
        float ny = Vector3f.dot(v, y);
        float nz = Vector3f.dot(v, z);

        float ox = (nx) * (width / nz);
        float oy = (ny) * (height / nz);

        if (nz <= 0) {
            return null;
        }

        return new Vector2i(ox, oy);
    }
    public Vector2i modifyVector2(Vector3f x, Vector3f y, Vector3f z, Vector3f a) {

        Vector3f v = Vector3f.add(a, shift);

        float nx = Vector3f.dot(v, x);
        float ny = Vector3f.dot(v, y);
        float nz = Vector3f.dot(v, z);

        float ox = (nx) * (width / nz);
        float oy = (ny) * (height / nz);

        if (nz <= 0) {
            ox = (nx) * (width / 0.000001f);
            oy = (ny) * (height / 0.000001f);
        }

        return new Vector2i(ox, oy);
    }

    public void addCube(Vector3f a, float s) {
        Vector3f btr = new Vector3f(a.x + s, a.y + s, a.z + s);
        Vector3f bbr = new Vector3f(a.x + s, a.y - s, a.z + s);
        Vector3f btl = new Vector3f(a.x - s, a.y + s, a.z + s);
        Vector3f bbl = new Vector3f(a.x - s, a.y - s, a.z + s);
        Vector3f ftr = new Vector3f(a.x + s, a.y + s, a.z - s);
        Vector3f fbr = new Vector3f(a.x + s, a.y - s, a.z - s);
        Vector3f ftl = new Vector3f(a.x - s, a.y + s, a.z - s);
        Vector3f fbl = new Vector3f(a.x - s, a.y - s, a.z - s);

        points.add(btr);
        points.add(bbr);
        points.add(btl);
        points.add(bbl);
        points.add(ftr);
        points.add(fbr);
        points.add(ftl);
        points.add(fbl);

        //back face
        lines.add(new Line(btr, btl));
        lines.add(new Line(btl, bbl));
        lines.add(new Line(bbl, bbr));
        lines.add(new Line(bbr, btr));

        //front face
        lines.add(new Line(ftr, ftl));
        lines.add(new Line(ftl, fbl));
        lines.add(new Line(fbl, fbr));
        lines.add(new Line(fbr, ftr));

        //connectors
        lines.add(new Line(ftr, btr));
        lines.add(new Line(ftl, btl));
        lines.add(new Line(fbl, bbl));
        lines.add(new Line(fbr, bbr));

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
