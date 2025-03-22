package com.jg.dietapp;

import static android.content.ContentValues.TAG;
import static com.jg.dietapp.utils.Utils.loadImagesFromAssetToInternalStorage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.ConfigurationPrefs;
import com.jg.dietapp.prefs.FirebaseDataPrefs;

public class LoadingActivity extends AppCompatActivity {
    FirebaseDataPrefs firebaseDataPrefs;

    ConfigurationPrefs configurationPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);

        firebaseDataPrefs = new FirebaseDataPrefs(this);

        configurationPrefs = new ConfigurationPrefs(this);

        // ASYNCHRONOUS
        // Load images from assets to internal storage

        if(!configurationPrefs.isAssetLoaded()) {
            loadImagesFromAssetToInternalStorage(this);
            configurationPrefs.setAssetLoaded(true);
        }

        UserInput user = firebaseDataPrefs.getUser();
        if (user != null && user.getUserSubmitted()) {
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(LoadingActivity.this, UserInputActivity.class));
        }
        finish();



    }
}
