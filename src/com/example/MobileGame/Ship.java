package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by eovill on 11/04/2016.
 */
public class Ship {

    private Bitmap shipBitmap;
    private int x, y;
    private int speed;
    private int increment;
    int screenWidth, screenHeight;
    public ArrayList<Bitmap> destroyed;
    boolean death;
    boolean up;
    float verticalSpeed;

    float animTime = 0;
    float totalAnimTime = 1;
    float numFrames;



    public Ship(Bitmap bitmap, int screenWidth, int screenHeight) {
        this.shipBitmap = bitmap;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = screenWidth / 8;
        this.y = screenHeight / 2;

        this.speed = 1;
        this.increment = 0;
        this.verticalSpeed = 0;

        this.death = false;
        this.up = false;
    }

    public void setDestroyedAnimation(ArrayList<Bitmap> animation) {
        destroyed = new ArrayList<Bitmap>(animation);
        numFrames = destroyed.size();
    }

    public void draw(Canvas canvas) {
        if (!death) {
            canvas.drawBitmap(shipBitmap, x - shipBitmap.getWidth()/2, y - shipBitmap.getHeight()/2, null);
        }
        else {
            int index = (int) (animTime / totalAnimTime * numFrames);
            if (index < numFrames) {
                canvas.drawBitmap(destroyed.get(index), x - shipBitmap.getWidth() / 2, y - shipBitmap.getHeight() / 2, null);
            }
        }
    }

    public void update(float DELTA_T) {
        if (death) {
            animTime += DELTA_T;
        }
        else {
            verticalSpeed += screenHeight/2 * DELTA_T;
            if (up) {
                verticalSpeed -= screenHeight * DELTA_T * 2;
            }
            y += verticalSpeed * DELTA_T;

            if (y - (shipBitmap.getHeight() / 2) > screenWidth) {
                y = 0 - (shipBitmap.getHeight() / 2);
            }
        }
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
        TL.x = x - shipBitmap.getWidth() / 2;
        TL.y = y - shipBitmap.getHeight() / 2;

        TR.x = x + shipBitmap.getWidth() / 2;
        TR.y = y - shipBitmap.getHeight() / 2;

        BL.x = x - shipBitmap.getWidth() / 2;
        BL.y = y + shipBitmap.getHeight() / 2;

        BR.x = x + shipBitmap.getWidth() / 2;
        BR.y = y + shipBitmap.getHeight() / 2;

    }

    public int getX() {
        return x + shipBitmap.getWidth();
    }

    public int getY() {
        return y + shipBitmap.getHeight() / 2;
    }

}
