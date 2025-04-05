package com.jg.dietapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.jg.dietapp.prefs.AuthPrefs;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button loginButton;
    TextView registerLink;

    private TextInputEditText emailEditText, passwordEditText;
    private AuthPrefs authPrefs;

    @Override
    public void onStart(){
        super.onStart();

        authPrefs = new AuthPrefs(this);

        // Redirect if user is already logged in
        if (authPrefs.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        emailEditText = findViewById(R.id.emailTextField);
        passwordEditText = findViewById(R.id.passwordTextField);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(LoginActivity.this, LoginLoadingActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);

        });




    }


}
