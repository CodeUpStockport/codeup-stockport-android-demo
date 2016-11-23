package me.abala.codeup.androiddemo.activity;

import android.app.Activity;
import android.os.Bundle;

import me.abala.codeup.androiddemo.R;

/**
 * Activity used to load image URLs.
 */
public class LoaderActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_loader);
    }
}
