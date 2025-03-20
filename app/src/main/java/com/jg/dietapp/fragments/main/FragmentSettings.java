package com.jg.dietapp.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.jg.dietapp.LoginActivity;
import com.jg.dietapp.MainActivity;
import com.jg.dietapp.UserInputActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.FirebaseUtils;

public class FragmentSettings extends Fragment {
    Button resetButton, logoutButton;
    FirebaseDataPrefs firebaseDataPrefs;
    FirebaseUtils firebaseUtils;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetButton = view.findViewById(R.id.resetButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        firebaseDataPrefs = new FirebaseDataPrefs(view.getContext());
        firebaseUtils = new FirebaseUtils(view.getContext());

        resetButton.setOnClickListener(v -> {

            firebaseUtils.clearUserData();
            firebaseDataPrefs.clearAllData();


            Intent intent = new Intent(getContext(), UserInputActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            firebaseUtils.syncGeneratedData();
            firebaseUtils.syncPreferencesToFirebase();
            firebaseDataPrefs.clearAllData();

            FirebaseAuth.getInstance().signOut(); // Sign out the user

            // Show a toast message for confirmation
            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Redirect user to LoginActivity and clear back stack
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


    }
}
