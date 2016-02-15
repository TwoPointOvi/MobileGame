package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by eovill on 15/02/2016.
 */
public class Alien {
    private Bitmap sprite;
    private Vector2D pos;
    private Vector2D vel;

    public Alien(float sx, float sy, float vx, float vy, Bitmap resource) {
        pos = new Vector2D(sx, sy);
        vel = new Vector2D(vx, vy);
        sprite = resource;
    }

    public Bitmap getBitmap() {
        return sprite;
    }

    public void update(float DELTA_T) {
        getPos().addScaled(vel, DELTA_T);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, pos.x - (sprite.getWidth() / 2), pos.y - (sprite.getHeight() / 2), null);
    }

    public Vector2D getPos() {
        return pos;
    }

    public Vector2D getVel() {
        return vel;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    public void setVel(Vector2D vel) {
        this.vel = vel;
    }
}
