package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by eovill on 11/04/2016.
 */
public class BarrierManager {

    Bitmap barrierBitmap;
    int shipHeight;
    int num;
    int screenHeight;
    int dl;
    int targetY = -1;
    int dpos;
    public GameSurfaceView gameSurfaceView;

    ArrayList<Barrier> topWalls;
    ArrayList<Barrier>  bottomWalls;

    public BarrierManager(Bitmap bitmap, GameSurfaceView gameSurfaceView) {
        this.barrierBitmap = bitmap;
        this.gameSurfaceView = gameSurfaceView;
    }

    void setShipHeight(int height){
        shipHeight = height;
    }

    public void setScreen(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;

        num = (screenWidth)/barrierBitmap.getWidth()+5;
        topWalls = new ArrayList<Barrier>();
        bottomWalls = new ArrayList<Barrier>();
        for (int i = 0; i < num + 1; i++){
            Barrier BB = new Barrier(barrierBitmap, screenWidth + 200 + barrierBitmap.getWidth() * i, 0);
            BB.setManager(this);
            topWalls.add(BB);
            Barrier BBB = new Barrier(barrierBitmap, screenWidth + 200 + barrierBitmap.getWidth() * i, 0);
            BBB.setManager(this);
            bottomWalls.add(BBB);
        }
        generate();
    }

    private void generate() {
        int h = barrierBitmap.getHeight() / 2;
        dl = screenHeight;
        dpos =screenHeight/2;
        int new_dl = screenHeight * 3/5;
        int inc =  (dl - new_dl) / num;
        for (int i = 0; i < num + 1; i++){
            dl = dl - inc;
            h = topWalls.get(i).getBitmap().getHeight() / 2;
            topWalls.get(i).setY(dpos - dl / 2 - h);
            bottomWalls.get(i).setY(dpos + dl / 2 + h);
        }
    }

    public void draw(Canvas canvas){
        for (int i=0;i < num+1; i++){
            topWalls.get(i).draw(canvas);
            bottomWalls.get(i).draw(canvas);
        }
    }

    public void update(float DELTA_T){
        for (int i=0; i < num+1; i++){
            topWalls.get(i).update(DELTA_T, true);
            bottomWalls.get(i).update(DELTA_T, false);
        }
    }
}
