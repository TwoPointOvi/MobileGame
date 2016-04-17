package com.example.MobileGame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by eovill on 15/02/2016.
 */
public class GameActivity extends Activity {

    final static int UPDATE_SCORE = 0;
    final static int DEATH = 1;
    final static int LOSE = 2;

    RelativeLayout relMainGame;

    View pauseButton;
    View pauseMenu;
    View loseDialog;

    MediaPlayer backMusic;

    GameSurfaceView gameSurfaceView;

    TextView textScore;
    public int score = 0;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_SCORE){

                updateScore();
            }
            if (msg.what == DEATH){
                postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Message msg = handler.obtainMessage();
                        msg.what = LOSE;
                        handler.sendMessage(msg);

                    }
                }, 3000);
            }
            if (msg.what == LOSE){
                lose();
            }

            super.handleMessage(msg);
        }

    };

    /*
    OnClickListener for the 'Continue' button at the pause menu
    Sets visibilities to assets in the GameActivity
     */
    OnClickListener ContinueListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!backMusic.isPlaying()) {
                backMusic.start();
            }
            pauseMenu.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            gameSurfaceView.pausedGame = false;
        }
    };

    /*
    OnClickListener for the 'Main Menu' button at the pause menu
    Ends the GameActivity to return to the MainMenuActivity
     */
    OnClickListener ToMainMenuListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (backMusic.isPlaying()) {
                backMusic.stop();
            }
            gameSurfaceView.thread.setRunning(false);
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
            if (backMusic.isPlaying()) {
                backMusic.pause();
            }
            pauseButton.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);
            gameSurfaceView.pausedGame = true;

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
        gameSurfaceView = new GameSurfaceView(getApplicationContext(), this, heightPixels, widthPixels);
        relMainGame.addView(gameSurfaceView);

        //Make the score view
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundResource(R.drawable.button);
        relativeLayout.setGravity(Gravity.CENTER);
        relMainGame.addView(relativeLayout,400,150);
        relativeLayout.setX(0);
        textScore = new TextView(this);
        Typeface Custom = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
        textScore.setTypeface(Custom);
        textScore.setTextColor(Color.YELLOW);
        textScore.setText("Score: " + score);
        relativeLayout.addView(textScore);

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
        TextView contText = (TextView)pauseMenu.findViewById(R.id.continueText);
        ImageView mainMenuTo = (ImageView)pauseMenu.findViewById(R.id.imgToMain);
        TextView mainMenuText = (TextView)pauseMenu.findViewById(R.id.mainMenuText);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
        contText.setTypeface(customFont);
        mainMenuText.setTypeface(customFont);

        cont.setOnClickListener(ContinueListener);
        mainMenuTo.setOnClickListener(ToMainMenuListener);

        //Adds lose screen dynamically
        loseDialog= myInflater.inflate(R.layout.lose, null, false);
        relMainGame.addView(loseDialog);
        TextView loseText = (TextView)loseDialog.findViewById(R.id.textLose);
        TextView mainMenuText2 = (TextView)loseDialog.findViewById(R.id.endMainMenu);
        loseText.setTypeface(customFont);
        mainMenuText2.setTypeface(customFont);

        ImageView loseToMain = (ImageView) loseDialog.findViewById(R.id.toMain);

        loseToMain.setOnClickListener(ToMainMenuListener);
        loseDialog.setVisibility(View.GONE);

        //Loads and plays background music
        backMusic = MediaPlayer.create(GameActivity.this, R.raw.music);
        backMusic.setVolume(0.3f, 0.3f);
        backMusic.setLooping(true);
        backMusic.start();
    }

    @Override
    public void onBackPressed() {
        if (backMusic.isPlaying()) {
            backMusic.pause();
        }
        pauseButton.setVisibility(View.GONE);
        pauseMenu.setVisibility(View.VISIBLE);
        gameSurfaceView.pausedGame = true;
    }

    @Override
    protected void onResume() {
        if (!backMusic.isPlaying()) {
            backMusic.start();
        }

        super.onResume();
    }

    @Override
    protected void onStop() {
        if (backMusic.isPlaying()) {
            backMusic.stop();
        }
        pauseButton.setVisibility(View.GONE);
        pauseMenu.setVisibility(View.VISIBLE);
        gameSurfaceView.pausedGame = true;

        super.onStop();
    }

    @Override
    protected void onPause() {
        if (backMusic.isPlaying()) {
            backMusic.pause();
        }

        pauseButton.setVisibility(View.GONE);
        pauseMenu.setVisibility(View.VISIBLE);
        gameSurfaceView.pausedGame = true;

        super.onPause();
    }

    public void updateScore() {
        score += 10;
        textScore.setText("Score: " + score);
        MediaPlayer mp = MediaPlayer.create(GameActivity.this, R.raw.bloodsplatters);
        mp.start();
    }

    public void lose() {
        if (backMusic.isPlaying()) {
            backMusic.stop();
        }

        //Save the score of the game if it was higher than the previous score
        SharedPreferences preferences = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore = preferences.getInt("scoreKey", 0);
        if (score > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("scoreKey", score);
            editor.commit();
        }

        gameSurfaceView.pausedGame = true;
        pauseButton.setVisibility(View.GONE);
        loseDialog.setVisibility(View.VISIBLE);
    }
}