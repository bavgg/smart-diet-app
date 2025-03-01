package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.decreaseProgress;
import static com.jg.dietapp.MainActivity.increaseProgress;
import static com.jg.dietapp.MainActivity.userData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class Fragment1eDietaryPreferences extends Fragment {
    Button continueButton;
    CustomCard veganCard, ketoCard, paleoCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dietary_preferences, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        veganCard = view.findViewById(R.id.veganCard);
        ketoCard = view.findViewById(R.id.ketoCard);
        paleoCard = view.findViewById(R.id.paleoCard);

        setupPressBackListener();

        veganCard.setOnClickListener(v -> {
            veganCard.setSelected(true);
            ketoCard.setSelected(false);
            paleoCard.setSelected(false);

            userData.setDietPreference("vegan");
            nextFragment(new Fragment1eFoodRestrictions());
            increaseProgress();
        });

    }

    private void nextFragment(Fragment nextFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupPressBackListener() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                decreaseProgress();
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
