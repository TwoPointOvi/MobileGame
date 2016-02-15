package com.example.MobileGame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by eovill on 15/02/2016.
 */
public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameSurfaceView gameSurfaceView;
    private boolean running;
    float DELTA_T;

    public MainThread(SurfaceHolder holder, GameSurfaceView gameSurfaceView) {
        this.surfaceHolder = holder;
        this.gameSurfaceView = gameSurfaceView;
        DELTA_T = 0;
    }

    void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        //super.run();

        Canvas canvas;

        while (running) {
            if (!gameSurfaceView.pausedGame) {
                long startDraw = System.currentTimeMillis();
                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gameSurfaceView.update(DELTA_T);
                        gameSurfaceView.draw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }


                long endDraw = System.currentTimeMillis();
                DELTA_T = (endDraw - startDraw)/1000.f;
            }
        }
    }
}
