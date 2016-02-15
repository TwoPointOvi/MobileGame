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

    private Alien alien;

    private int screenWidth;
    private int screenHeight;

    public GameSurfaceView(Context context, GameActivity game, int screenHeight, int screenWidth) {
        super(context);
        getHolder().addCallback(this);
        gameActivity = game;
        thread = new MainThread(getHolder(), this);

        alien = new Alien(screenWidth/2, screenHeight/2, 70.0f, 70.0f, BitmapFactory.decodeResource(getResources(), R.drawable.boom));

        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        pausedGame = false;

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
                alien.draw(canvas);
            }
        }
    }

    public void update(float DELTA_T){
        alien.update(DELTA_T);

        //TODO: Incomplete Method of Checking collision
        if (alien.getPos().x < 0 || alien.getPos().x > screenWidth) {
            alien.getVel().y *= -1;
        }
        if (alien.getPos().y < 0 || alien.getPos().y > screenHeight) {
            alien.getVel().x *= -1;
        }
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
