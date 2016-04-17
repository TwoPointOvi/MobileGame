package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eovill on 11/04/2016.
 */
public class Barrier {

    private Bitmap barrierBitmap;
    private int x, y;

    BarrierManager BM;
    boolean doit;

    /*
    Constructor of the class
     */
    public Barrier(Bitmap barrierBitmap, int x, int y){
        this.barrierBitmap = barrierBitmap;
        this.x = x;
        this.y = y;
    }

    /*
    Set the barrier manager to manage the spawn of barriers on screen
     */
    public void setManager(BarrierManager barrierManager) {
        BM = barrierManager;
    }

    /*
    Get the image bitmap of the object
     */
    public Bitmap getBitmap() {
        return barrierBitmap;
    }

    public void setY(int y) {
        this.y = y;

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(barrierBitmap, x - (barrierBitmap.getWidth() / 2), y - (barrierBitmap.getHeight() / 2), null);
    }

    public void update(float DELTA_T, boolean bol) {
        if (x < -barrierBitmap.getWidth()) {
            if (bol) {
                if (Math.abs(BM.targetY - BM.dpos) < 50) {
                    doit = true;
                }
                if ((BM.targetY == -1) || doit){
                    BM.targetY = new Random().nextInt(BM.screenHeight - BM.dl/2) + BM.dl/4;
                }
                if (BM.dpos < BM.targetY)
                    BM.dpos = BM.dpos + new Random().nextInt(15);
                else
                    BM.dpos = BM.dpos - new Random().nextInt(15);
                    y = BM.dpos - BM.dl/2 -barrierBitmap.getHeight()/2;
            }
            else {
                y = BM.dpos + BM.dl/2 + barrierBitmap.getHeight()/2;
            }
            x=(int) (x + barrierBitmap.getWidth() * (BM.topWalls.size() - 1));
        }

        x = (int) (x - BM.gameSurfaceView.shipSpeed * DELTA_T);
    }

    /*
    Get the collision points
     */
    public ArrayList<Point> GetArray() {
        Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();
        TL.x = x - barrierBitmap.getWidth() / 2;
        TL.y = y - barrierBitmap.getHeight() / 2;

        TR.x = x + barrierBitmap.getWidth() / 2;
        TR.y = y - barrierBitmap.getHeight() / 2;

        BL.x = x - barrierBitmap.getWidth() / 2;
        BL.y = y + barrierBitmap.getHeight() / 2;

        BR.x = x + barrierBitmap.getWidth() / 2;
        BR.y = y + barrierBitmap.getHeight() / 2;

        ArrayList<Point> temp = new ArrayList<Point>();
        temp.add(TL);
        temp.add(TR);
        temp.add(BR);
        temp.add(BL);
        return temp;
    }
}
