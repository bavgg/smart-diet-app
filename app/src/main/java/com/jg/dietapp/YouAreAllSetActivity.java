package com.jg.dietapp;

import static com.jg.dietapp.UserInputActivity.userInput;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.FirebaseUtils;

public class YouAreAllSetActivity extends AppCompatActivity {
    Button nextButton;
    FirebaseUtils firebaseUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_you_are_all_set);

        FirebaseDataPrefs firebaseDataPrefs = new FirebaseDataPrefs(this);
//        firebaseUtils = new FirebaseUtils(this);

        nextButton = findViewById(R.id.nextButton);

//
////        userInput.setUserSubmitted(true);
////
////        firebaseDataPrefs.saveUser(userInput);
//        System.out.println("You are all set: FirebaseDataPrefs: " + firebaseDataPrefs.getUser());
//        firebaseUtils.syncUserInput();
//
//        Log.i("TAG", "FragmentYouAreAllSet FirebaseDataPrefs: " + new Gson().toJson(firebaseDataPrefs));

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}


