package com.jg.dietapp.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.HomeActivity;
import com.jg.dietapp.MainActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.shared.SharedPrefsHelper;
import com.jg.dietapp.shared.SharedPrefsMeals;
import com.jg.dietapp.shared.SharedPrefsNutrients;

public class FragmentSettings extends Fragment {
    Button resetButton;
    SharedPrefsHelper sharedPrefsHelper;
    SharedPrefsMeals sharedPrefsMeals;
    SharedPrefsNutrients sharedPrefsNutrients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetButton = view.findViewById(R.id.resetButton);
        sharedPrefsHelper = new SharedPrefsHelper();
        sharedPrefsMeals = new SharedPrefsMeals(getContext());
        sharedPrefsNutrients = new SharedPrefsNutrients(getContext());


        resetButton.setOnClickListener(v -> {
            sharedPrefsHelper.clearUser(getContext());
            sharedPrefsMeals.clearSelectedMeals();
            sharedPrefsNutrients.clearSelectedMeals(getContext());

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

    }
}
