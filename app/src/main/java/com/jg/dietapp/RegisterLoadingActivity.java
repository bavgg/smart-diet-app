package com.jg.dietapp;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.prefs.FirebaseDataPrefs;




public class RegisterLoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_loading);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get email and password from Intent
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");


        boolean success = dbHelper.registerUser(email, password);
        System.out.println("Success: " + success);
        if (success) {
            Toast.makeText(RegisterLoadingActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterLoadingActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(RegisterLoadingActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterLoadingActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    }

}
