package com.example.MobileGame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;

/**
 * Created by eovill on 15/02/2016.
 */
public class GameActivity extends Activity {

    RelativeLayout relMainGame;

    View pauseButton;
    View pauseMenu;


    OnClickListener ContinueListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseMenu.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        }
    };

    OnClickListener ToMainMenuListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            GameActivity.this.finish();

        }
    };

    OnClickListener PauseClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseButton.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);

            // Logic to pause game
        }
    };


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        relMainGame = (RelativeLayout) findViewById(R.id.main_game_rel);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int heightPixels = dm.heightPixels;
        final int widthPixels = dm.widthPixels;

        LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

        pauseButton = myInflater.inflate(R.layout.pause, null, false);
        pauseButton.setX(widthPixels-250);
        pauseButton.setY(0);
        relMainGame.addView(pauseButton);
        pauseButton.setOnClickListener(PauseClick);
        pauseButton.getLayoutParams().height = 250;
        pauseButton.getLayoutParams().width = 250;

        pauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
        relMainGame.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);
        ImageView cont = (ImageView)pauseMenu.findViewById(R.id.imgCont);
        ImageView mainMenuTo = (ImageView)pauseMenu.findViewById(R.id.imgToMain);
        cont.setOnClickListener(ContinueListener);
        mainMenuTo.setOnClickListener(ToMainMenuListener);
    }
}