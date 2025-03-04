package com.jg.dietapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static int progress = 0;
    public static SharedDataDialog sharedDataDialog = new SharedDataDialog();
    public static ModelUserInput userInput = new ModelUserInput();
    static LinearProgressIndicator progressIndicator;
    private DatabaseHelper databaseHelper;


    @Override
    public void onStart(){
        super.onStart();

        if(userInput.getUserSubmitted()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ✅ Initialize DatabaseHelper (this triggers the seeding)
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase(); // Ensures database is created


        progressIndicator = findViewById(R.id.progressIndicator);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment1aStart())
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