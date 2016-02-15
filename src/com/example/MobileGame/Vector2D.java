package com.example.MobileGame;

import java.io.Serializable;

/**
 * Created by eovill on 15/02/2016.
 */
public class Vector2D implements Serializable{
    public float x, y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double mag() {
        return Math.hypot(x, y);
    }

    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void addScaled(Vector2D v, double fac) {
        this.x += v.x * fac;
        this.y += v.x * fac;
    }

    public void mult(double fac) {
        this.x *= fac;
        this.y *= fac;
    }

    public void rotate(double angle) {
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        float nx = x * cos - y * sin;
        float ny = x * cos + y * sin;
        this.x = nx;
        this.y = ny;
    }

    public double distance(Vector2D v) {
        return Math.hypot(x - v.x, y - v.y);
    }
}
