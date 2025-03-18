package com.jg.dietapp;

import static com.jg.dietapp.utils.Utils.loadImagesFromAssetToInternalStorage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.jg.dietapp.fragments.main.FragmentStart;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.prefs.ConfigurationPrefs;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.viewmodel.DialogViewModel;
import com.jg.dietapp.prefs.UserInputsPrefs;

public class UserInputActivity extends AppCompatActivity {

    private static int progress = 0;
    public static DialogViewModel dialogViewModel = new DialogViewModel();
    public static UserInputsPrefs sharedUserPrefs;
    public static UserInput userInput = new UserInput();
    static LinearProgressIndicator progressIndicator;

    @Override
    public void onStart(){
        super.onStart();

        sharedUserPrefs = new UserInputsPrefs(this);

        // Initialize database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Load userPrefs
        UserInput user = sharedUserPrefs.getUser();

        // Check user inputs
        if(user.getUserSubmitted()) {
            Intent intent = new Intent(UserInputActivity.this, MainActivity.class);
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

        // ASYNCHRONOUS
        // Load images from assets to internal storage
        ConfigurationPrefs configurationPrefs = new ConfigurationPrefs(this);
        if(!configurationPrefs.isAssetLoaded()) {
            loadImagesFromAssetToInternalStorage(this);
            configurationPrefs.setAssetLoaded(true);
        }

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