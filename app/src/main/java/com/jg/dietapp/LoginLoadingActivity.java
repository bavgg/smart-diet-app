package com.jg.dietapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jg.dietapp.data.DAOUser;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.prefs.AuthPrefs;
import com.jg.dietapp.prefs.LoadPrefs;

import java.util.concurrent.TimeUnit;


public class LoginLoadingActivity extends AppCompatActivity {
    AuthPrefs authPrefs;
    LoadPrefs firebaseDataPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_loading);

        firebaseDataPrefs = new LoadPrefs(this);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        DAOUser daoUser = new DAOUser(dbHelper);
        authPrefs = new AuthPrefs(this);




        // Get email and password from Intent
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        int userId = daoUser.loginUser(email, password);
        // Success login
        if (userId != -1) {
            // Set login prefs to true
            authPrefs.setLoggedIn(true);
            authPrefs.setUserId(userId);

            // Delay 3 seconds
            // Delay action without blocking UI
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Toast.makeText(LoginLoadingActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginLoadingActivity.this, LoadingActivity.class));
                finish();
            }, 3000);



//            // Check if user inputs are already submitted
//            UserInput user = firebaseDataPrefs.getUser();
//            if (user != null && user.getUserSubmitted()) {
//                Toast.makeText(LoginLoadingActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginLoadingActivity.this, LoadingActivity.class));
//                finish();
//            }else {
//                Toast.makeText(LoginLoadingActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginLoadingActivity.this, UserInputActivity.class));
//                finish();
//            }


        } else {
            // Failed login
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Toast.makeText(LoginLoadingActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginLoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, 3000);

        }
    }
}

