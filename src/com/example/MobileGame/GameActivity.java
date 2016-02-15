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

    /*
    OnClickListener for the 'Continue' button at the pause menu
    Sets visibilities to assets in the GameActivity
     */
    OnClickListener ContinueListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseMenu.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        }
    };

    /*
    OnClickListener for the 'Main Menu' button at the pause menu
    Ends the GameActivity to return to the MainMenuActivity
     */
    OnClickListener ToMainMenuListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            GameActivity.this.finish();

        }
    };

    /*
    OnClickListener for the 'Pause(II)' button at the pause menu
    Sets visibilities to assets in the GameActivity
     */
    OnClickListener PauseClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseButton.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);

            // Logic to pause game
        }
    };

    /*
    App works properly on Android versions HONEYCOMB (4.0) and after
     */
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
        //Adds pause button dynamically
        pauseButton = myInflater.inflate(R.layout.pause, null, false);
        pauseButton.setX(widthPixels-250);
        pauseButton.setY(0);
        relMainGame.addView(pauseButton);
        pauseButton.setOnClickListener(PauseClick);
        pauseButton.getLayoutParams().height = 250;
        pauseButton.getLayoutParams().width = 250;
        //Adds the pause menu dynamically
        pauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
        relMainGame.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);
        ImageView cont = (ImageView)pauseMenu.findViewById(R.id.imgCont);
        ImageView mainMenuTo = (ImageView)pauseMenu.findViewById(R.id.imgToMain);
        cont.setOnClickListener(ContinueListener);
        mainMenuTo.setOnClickListener(ToMainMenuListener);
    }
}