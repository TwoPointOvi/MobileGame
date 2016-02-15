package com.example.MobileGame;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (RelativeLayout) findViewById(R.id.button_start);
        imageButton = (ImageView) findViewById(R.id.img_button);
        textView = (TextView) findViewById(R.id.text_start);

        button.setOnTouchListener(new TouchButton(imageButton));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
