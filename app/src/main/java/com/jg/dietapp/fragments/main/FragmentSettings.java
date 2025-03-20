package com.jg.dietapp.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.UserInputActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.prefs.FirebaseDataPrefs;

public class FragmentSettings extends Fragment {
    Button resetButton;
    FirebaseDataPrefs firebaseDataPrefs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetButton = view.findViewById(R.id.resetButton);
        firebaseDataPrefs = new FirebaseDataPrefs(view.getContext());

        resetButton.setOnClickListener(v -> {
            firebaseDataPrefs.clearUser();
            firebaseDataPrefs.clearSelectedMeals();
            firebaseDataPrefs.clearNutritionData();

            Intent intent = new Intent(getContext(), UserInputActivity.class);
            startActivity(intent);
        });

    }
}
