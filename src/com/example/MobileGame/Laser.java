package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eovill on 13/04/2016.
 */
public class Laser {

    private Bitmap laserBitmap;
    private int x, y;
    public boolean visible;
    BarrierManager barrierManager;

    public Laser(Bitmap bitmap, int x, int y) {
        this.laserBitmap = bitmap;
        this.x = x;
        this.y = y;
        visible = true;
    }

    public void setBarrierManager(BarrierManager barrierManager) {
        this.barrierManager = barrierManager;
    }

    public boolean bump (Point OTL, Point OTR, Point OBR, Point OBL){
        Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();

        ArrayList<Point> PointList = new ArrayList<Point>();
        PointList.add(OTL);
        PointList.add(OTR);
        PointList.add(OBR);
        PointList.add(OBL);

        getPoint(TL,TR,BL,BR);

        for (int i = 0; i < PointList.size(); i++){
            if (BR.x >= PointList.get(i).x)
                if (TL.x <= PointList.get(i).x)
                    if(PointList.get(i).y >= TL.y)
                        if(PointList.get(i).y <= BR.y)
                            return true;
        }
        PointList.clear();
        PointList.add(TL);
        PointList.add(TR);
        PointList.add(BR);
        PointList.add(BL);
        for (int i = 0; i < PointList.size(); i++){
            if (OBR.x >= PointList.get(i).x)
                if (OTL.x <= PointList.get(i).x)
                    if(PointList.get(i).y >= OTL.y)
                        if(PointList.get(i).y <= OBR.y)
                            return true;
        }

        return false;
    }

    private void getPoint(Point TL, Point TR, Point BL, Point BR) {
        TL.x = x - laserBitmap.getWidth() / 2;
        TL.y = y - laserBitmap.getHeight() / 2;

        TR.x = x + laserBitmap.getWidth() / 2;
        TR.y = y - laserBitmap.getHeight() / 2;

        BL.x = x - laserBitmap.getWidth() / 2;
        BL.y = y + laserBitmap.getHeight() / 2;

        BR.x = x + laserBitmap.getWidth() / 2;
        BR.y = y + laserBitmap.getHeight() / 2;

    }

    public ArrayList<Point> GetArray() {
        Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();
        TL.x = x - laserBitmap.getWidth() / 2;
        TL.y = y - laserBitmap.getHeight() / 2;

        TR.x = x + laserBitmap.getWidth() / 2;
        TR.y = y - laserBitmap.getHeight() / 2;

        BL.x = x - laserBitmap.getWidth() / 2;
        BL.y = y + laserBitmap.getHeight() / 2;

        BR.x = x + laserBitmap.getWidth() / 2;
        BR.y = y + laserBitmap.getHeight() / 2;

        ArrayList<Point> temp = new ArrayList<Point>();
        temp.add(TL);
        temp.add(TR);
        temp.add(BR);
        temp.add(BL);
        return temp;
    }

    public void update(float DELTA_T) {

        x += barrierManager.gameSurfaceView.shipSpeed * DELTA_T;
        if (x > barrierManager.gameSurfaceView.getWidth()) {
            visible = false;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(laserBitmap, x - (laserBitmap.getWidth() / 2), y - (laserBitmap.getHeight() / 2), null);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


}
