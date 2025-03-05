package com.jg.dietapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.jg.dietapp.fragments.main.Fragment1aStart;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.shared.UserInput;
import com.jg.dietapp.shared.SharedDataDialog;
import com.jg.dietapp.shared.SharedPrefsHelper;

public class MainActivity extends AppCompatActivity {

    private static int progress = 0;
    public static SharedDataDialog sharedDataDialog = new SharedDataDialog();
    public static SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();
    public static UserInput userInput = new UserInput();
    static LinearProgressIndicator progressIndicator;

    @Override
    public void onStart(){
        super.onStart();

        UserInput user = sharedPrefsHelper.getUser(this);

        if(user.getUserSubmitted()) {
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

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


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