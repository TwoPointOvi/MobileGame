package com.example.MobileGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    RelativeLayout button;
    ImageView imageButton;
    TextView textView;
    TextView titleView;
    TextView textHighScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (RelativeLayout) findViewById(R.id.button_start);
        imageButton = (ImageView) findViewById(R.id.img_button);
        textView = (TextView) findViewById(R.id.text_start);
        titleView = (TextView) findViewById(R.id.title_name);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
        textView.setTypeface(customFont);
        titleView.setTypeface(customFont);


        //Get and draw the high score
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("scoreKey", 0);

        textHighScore = (TextView) findViewById(R.id.text_highscore);
        textHighScore.setTypeface(customFont);
        textHighScore.setTextColor(Color.YELLOW);
        textHighScore.setText("High Score: " + highScore);

        button.setOnTouchListener(new TouchButton(imageButton));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call GameActivity to start the game
                Intent myIntent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("scoreKey", 0);

        textHighScore = (TextView) findViewById(R.id.text_highscore);
        textHighScore.setTypeface(customFont);
        textHighScore.setTextColor(Color.YELLOW);
        textHighScore.setText("High Score: " + highScore);
        super.onResume();
    }
}
