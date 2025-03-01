package com.jg.dietapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {

    private int progress = 0;
    public static SharedViewModel sharedViewModel = new SharedViewModel();
    public static SharedDataUser userData = new SharedDataUser();
    LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressIndicator = findViewById(R.id.progressIndicator);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment1aStart())
                .commit();

    }

    public void updateProgress(int value) {
        progress += value;
        progressIndicator.setProgress(progress);
    }
}