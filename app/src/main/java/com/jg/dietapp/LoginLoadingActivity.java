package com.jg.dietapp;

import android.content.Intent;
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
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.FirebaseUtils;




public class LoginLoadingActivity extends AppCompatActivity {
    FirebaseUtils firebaseUtils;
    FirebaseDataPrefs firebaseDataPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_loading);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Get email and password from Intent
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        if (email != null && password != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginLoadingActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginLoadingActivity.this, LoadingActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginLoadingActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginLoadingActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
        } else {
            Toast.makeText(this, "Missing credentials", Toast.LENGTH_SHORT).show();
        }
    }

}
