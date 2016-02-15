package com.example.MobileGame;

import android.app.Activity;
import android.content.Intent;
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
}
