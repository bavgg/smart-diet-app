package com.jg.dietapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button registerButton;
    private TextInputEditText emailTextField, passwordTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        emailTextField = findViewById(R.id.emailTextField);
        passwordTextField = findViewById(R.id.passwordTextField);
        registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(v -> {
            String email = emailTextField.getText().toString().trim();
            String password = passwordTextField.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }


            Intent intent = new Intent(RegisterActivity.this, RegisterLoadingActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);


        });


    }
}
