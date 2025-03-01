package com.jg.dietapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {

    private int progress = 0;
    public static SharedViewModel sharedViewModel = new SharedViewModel();
    public static UserData userData = new UserData();
    LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressIndicator = findViewById(R.id.progressIndicator);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new StartFragment())
                .commit();

    }

    public void updateProgress(int value) {
        progress += value;
        progressIndicator.setProgress(progress);
    }
}