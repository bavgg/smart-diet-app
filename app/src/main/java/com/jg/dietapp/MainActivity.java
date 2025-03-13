package com.jg.dietapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.jg.dietapp.fragments.main.FragmentStart;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.shared.UserInput;
import com.jg.dietapp.shared.SharedDataDialog;
import com.jg.dietapp.shared.SharedUserPrefs;

public class MainActivity extends AppCompatActivity {

    private static int progress = 0;
    public static SharedDataDialog sharedDataDialog = new SharedDataDialog();
    public static SharedUserPrefs sharedUserPrefs;
    public static UserInput userInput = new UserInput();
    static LinearProgressIndicator progressIndicator;

    @Override
    public void onStart(){
        super.onStart();

        sharedUserPrefs = new SharedUserPrefs(this);

        // Initialize database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        databaseHelper.seedDatabase(db);

        UserInput user = sharedUserPrefs.getUser();

        if(user.getUserSubmitted()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        Log.d("MainActivity", "User input: " + user);
    }

    // Entry point
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize progress indicator component
        progressIndicator = findViewById(R.id.progressIndicator);

        // Load fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FragmentStart())
                .commit();

    }

    public static void increaseProgress() {
        progress += 1;
        progressIndicator.setProgress(progress);
    }

    public static void decreaseProgress() {
        progress -= 1;
        progressIndicator.setProgress(progress);
    }


}