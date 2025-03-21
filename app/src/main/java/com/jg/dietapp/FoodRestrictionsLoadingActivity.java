package com.jg.dietapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.FirebaseUtils;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.FirebaseUtils;

public class FoodRestrictionsLoadingActivity extends AppCompatActivity {
    FirebaseUtils firebaseUtils;
    FirebaseDataPrefs firebaseDataPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);

        firebaseUtils = new FirebaseUtils(this);
//        firebaseDataPrefs = new FirebaseDataPrefs(this);


        firebaseUtils.syncUserInput().addOnCompleteListener(task -> {
            Intent intent = new Intent(this, YouAreAllSetActivity.class);
            startActivity(intent);
            finish();
        });
//        firebaseUtils.loadPreferencesFromFirebase(() -> {
//            UserInput user = firebaseDataPrefs.getUser();
//
//            if (user != null && user.getUserSubmitted()) {
//                Intent intent = new Intent(FoodRestrictionsLoadingActivity.this, YouAreAllSetActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//                Intent intent = new Intent(FoodRestrictionsLoadingActivity.this, YouAreAllSetActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }
}
