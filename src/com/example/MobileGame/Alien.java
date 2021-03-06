package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eovill on 15/02/2016.
 */
public class Alien {
    private Bitmap alienBitmap;
    private int x, y;
    BarrierManager barrierManager;

    /*
    Constructor of the class
     */
    public Alien(Bitmap bitmap, int x, int y) {
        alienBitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    /*
    Return the bitmap image of the object
     */
    public Bitmap getBitmap() {
        return alienBitmap;
    }

    /*
    Set the barrier manager to manage alien spawn in between the barriers
     */
    public void setBarrierManager(BarrierManager barrierManager) {
        this.barrierManager = barrierManager;
    }

    /*
    An array of the points of the bitmap
     */
    public ArrayList<Point> GetArray() {
        Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();
        TL.x = x - alienBitmap.getWidth() / 2;
        TL.y = y - alienBitmap.getHeight() / 2;

        TR.x = x + alienBitmap.getWidth() / 2;
        TR.y = y - alienBitmap.getHeight() / 2;

        BL.x = x - alienBitmap.getWidth() / 2;
        BL.y = y + alienBitmap.getHeight() / 2;

        BR.x = x + alienBitmap.getWidth() / 2;
        BR.y = y + alienBitmap.getHeight() / 2;

        ArrayList<Point> temp = new ArrayList<Point>();
        temp.add(TL);
        temp.add(TR);
        temp.add(BR);
        temp.add(BL);
        return temp;
    }

    /*
    Update the object movement by DELTA_T
     */
    public void update(float DELTA_T) {
        //Check is the object has dissapeared from the screen
        if (x < -barrierManager.gameSurfaceView.screenWidth/4)
        {
            x = barrierManager.gameSurfaceView.screenWidth + alienBitmap.getWidth();
            Random r = new Random();
            y = r.nextInt(barrierManager.dl) + barrierManager.dpos - barrierManager.dl/2;
        }

        x -= barrierManager.gameSurfaceView.shipSpeed * DELTA_T;
    }

    /*
    Draw method
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(alienBitmap, x - (alienBitmap.getWidth() / 2), y - (alienBitmap.getHeight() / 2), null);
    }

    /*
    Set value of x
     */
    public void setX(int x) {
        this.x = x;
    }

    /*
    Set value of y
     */
    public void setY(int y) {
        this.y = y;
    }
}
