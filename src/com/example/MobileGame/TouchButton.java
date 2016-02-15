package com.example.MobileGame;

import android.view.View;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * Created by eovill on 15/02/2016.
 */
public class TouchButton implements OnTouchListener {
    ImageView  imageButton;

    public TouchButton(ImageView imageButton) {
        this.imageButton = imageButton;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageButton.setImageResource(R.drawable.button_s);
                break;
            case MotionEvent.ACTION_UP:
                imageButton.setImageResource(R.drawable.button);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                imageButton.setImageResource(R.drawable.button);
                break;
            default:

                break;
        }
        return false;
    }
}
