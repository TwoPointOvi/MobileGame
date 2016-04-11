package com.example.MobileGame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by eovill on 15/02/2016.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    public boolean pausedGame;
    public GameActivity gameActivity;

    private Background background;

    public float shipSpeed;
    private int screenWidth;
    private int screenHeight;

    public GameSurfaceView(Context context, GameActivity game, int screenHeight, int screenWidth) {
        super(context);
        getHolder().addCallback(this);
        gameActivity = game;
        thread = new MainThread(getHolder(), this);

        shipSpeed = screenWidth / 2.f;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        pausedGame = false;

        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.game_back), screenWidth, screenHeight, this);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!pausedGame) {
            if (canvas!=null) {
                background.draw(canvas);
            }
        }
    }

    public void update(float DELTA_T){
        background.update(DELTA_T);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
}
