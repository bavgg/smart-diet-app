package com.jg.dietapp;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.prefs.AuthPrefs;
import com.jg.dietapp.prefs.FirebaseDataPrefs;




public class LoginLoadingActivity extends AppCompatActivity {
    FirebaseDataPrefs firebaseDataPrefs;
    AuthPrefs authPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_loading);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        authPrefs = new AuthPrefs(this);

        // Get email and password from Intent
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        if (dbHelper.loginUser(email, password)) {
            authPrefs.setLoggedIn(true);
            Toast.makeText(LoginLoadingActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginLoadingActivity.this, LoadingActivity.class));
            finish();
        } else {
            Toast.makeText(LoginLoadingActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginLoadingActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

}
