package com.example.MobileGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by eovill on 11/04/2016.
 */
public class Background {

    Bitmap backgroundBitmap;
    int x, y;
    int screenWidth;
    int countBackground;
    GameSurfaceView gameSurfaceView;

    /*
    Class constructor
     */
    public Background(Bitmap bitmap, int screenW, int screenH, GameSurfaceView gameSurfaceView) {
        this.backgroundBitmap = bitmap;
        this.screenWidth = screenW;
        this.x = this.y = 0;
        this.countBackground = screenWidth/backgroundBitmap.getWidth() + 1;
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, screenW, screenH, true);
        this.gameSurfaceView = gameSurfaceView;
    }

    /*
    Draw method
     */
    public void draw(Canvas canvas) {
        for (int i = 0; i < countBackground+1; i++) {
            if (canvas != null) {
                canvas.drawBitmap(backgroundBitmap, backgroundBitmap.getWidth() * i + x, y, null);
            }
        }
        if (Math.abs(x) > backgroundBitmap.getWidth()) {
            x = x + backgroundBitmap.getWidth();
        }
    }

    /*
    Update the background
     */
    public void update(float DELTA_T) {
        x = (int) (x - gameSurfaceView.shipSpeed * DELTA_T);
    }

}
