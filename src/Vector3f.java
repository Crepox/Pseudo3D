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
public class Vector3f {
    float x, y, z;
    int xi, yi; 
    boolean b;

    Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public String toString() {
        return "Vector3f[x = " + x + ", y = " + y + ", z = " + z + "]";
    }

    public static Vector3f add(Vector3f a, Vector3f b) {
        return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vector3f sub(Vector3f a, Vector3f b) {
        return new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vector3f scale(Vector3f a, float b) {
        return new Vector3f(a.x * b, a.y * b, a.z * b);
    }

    public static Vector3f addScaled(Vector3f a, Vector3f b, float c) {
        return add(a, scale(b, c));
    }

    public static float magSqr(Vector3f a) {
        return a.x * a.x + a.y * a.y + a.z * a.z;
    }

    public static float dot(Vector3f a, Vector3f b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static float mag(Vector3f a) {
        return (float) Math.sqrt(magSqr(a));
    }

    public static Vector3f normal(Vector3f a) {
        return new Vector3f(scale(a, 1 / mag(a)));
    }
    
}
