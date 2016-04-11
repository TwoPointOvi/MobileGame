package com.example.MobileGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by eovill on 15/02/2016.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    public boolean pausedGame;
    public GameActivity gameActivity;

    private Background background;
    private Ship ship;
    private BarrierManager barrierManager;

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
        barrierManager = new BarrierManager(BitmapFactory.decodeResource(getResources(), R.drawable.barier), this);
        barrierManager.setScreen(screenWidth, screenHeight);

        ship = new Ship(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), screenWidth, screenHeight);
        ArrayList<Bitmap> animation = new ArrayList<Bitmap>();
        animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom1));
        animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom2));
        animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom3));
        animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom4));
        ship.setDestroyedAnimation(animation);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ship.up = true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ship.up = false;
        }

        //return super.onTouchEvent(event);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!pausedGame) {
            if (canvas!=null) {
                background.draw(canvas);
                ship.draw(canvas);
                barrierManager.draw(canvas);
            }
        }
    }

    public void update(float DELTA_T){
        ship.update(DELTA_T);
        if (!ship.death) {
            background.update(DELTA_T);
            barrierManager.update(DELTA_T);

            for (int i = 0; i < barrierManager.topWalls.size(); i++) {
                ArrayList<Point> temp = new ArrayList<Point>(barrierManager.topWalls.get(i).GetArray());
                ArrayList<Point> temp2 = new ArrayList<Point>(barrierManager.bottomWalls.get(i).GetArray());
                if ((ship.bump(temp.get(0), temp.get(1), temp.get(2), temp.get(3))) ||
                        (ship.bump(temp2.get(0), temp2.get(1), temp2.get(2), temp2.get(3)))) {
                    ship.death = true;
                    //MediaPlayer mp = MediaPlayer.create(gameActivity, R.raw.boom);
                    //mp.start();
                    //Message msg = barrierManager.game_panel.game.handler.obtainMessage();
                    //msg.what = 1;
                    //barrierManager.gameSurfaceView.gameActivity.handler.sendMessage(msg);
                }
            }
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
