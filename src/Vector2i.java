/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joe
 */
public class Vector2i {
    
    int x, y;

    Vector2i() {
        x = 0;
        y = 0;
    }

    Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    Vector2i(float x, float y) {
        this.x = Math.round(x);
        this.y = Math.round(y);
    }

    Vector2i(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    public String toString() {
        return "Vector2i[x = " + x + ", y = " + y + "]";
    }

    public static Vector2i add(Vector2i a, Vector2i b) {
        return new Vector2i(a.x + b.x, a.y + b.y);
    }

    public static Vector2i sub(Vector2i a, Vector2i b) {
        return new Vector2i(a.x - b.x, a.y - b.y);
    }

    public static Vector2i scale(Vector2i a, int b) {
        return new Vector2i(a.x * b, a.y * b);
    }

    public static Vector2i addScaled(Vector2i a, Vector2i b, int c) {
        return add(a, scale(b, c));
    }

    public static int magSqr(Vector2i a) {
        return a.x * a.x + a.y * a.y;
    }

    public static int dot(Vector2i a, Vector2i b) {
        return a.x * b.x + a.y * b.y;
    }

    public static int mag(Vector2i a) {
        return (int) Math.round(Math.sqrt(magSqr(a)));
    }

    public static Vector2i normal(Vector2i a) {
        return new Vector2i(scale(a, 1 / mag(a)));
    }
}