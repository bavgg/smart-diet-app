package com.jg.dietapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class AboutYouFragment extends Fragment {
//    MaterialCardView  sexSelector, ageSelector, heightSelector, weightSelector;
    Button continueButton;
    CustomSelect sexSelector, ageSelector, heightSelector, weightSelector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aboutyou, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Update progress if the fragment is inside MainActivity
//        if (getActivity() instanceof MainActivity) {
//            ((MainActivity) getActivity()).updateProgress(25);
//        }



        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateProgress(-25);
                }
                getParentFragmentManager().popBackStack();
            }
        });

        // Setup click listeners for sex & age selection
        setupClickListeners(view);
    }

    private void setupBackPressHandler() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateProgress(-25);
                }
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void setupClickListeners(View view) {
        sexSelector = view.findViewById(R.id.sexSelector);
        ageSelector = view.findViewById(R.id.ageSelector);
        heightSelector = view.findViewById(R.id.heightSelector);
        weightSelector = view.findViewById(R.id.weightSelector);
        continueButton = view.findViewById(R.id.continueButton);






        // Show SexDialog when clicked
        sexSelector.setOnClickListener(v -> {
            SexDialog sexDialog = new SexDialog();
            sexDialog.setSexSelectionListener(sex -> {
                sexSelector.setSelectValue(sex);
                continueButton.setEnabled(isFilled());
            });
            sexDialog.show(getParentFragmentManager(), "SexDialog");
        });

        // Show AgeDialog when clicked
//        ageSelector.setOnClickListener(v -> new AgeDialog().show(getParentFragmentManager(), "AgeDialog"));
        heightSelector.setOnClickListener(v -> {

            HeightDialog heightDialog = new HeightDialog();
            heightDialog.setHeightSelectionListener(height -> {
                heightSelector.setSelectValue(height);
                continueButton.setEnabled(isFilled());
            });
            heightDialog.show(getParentFragmentManager(), "HeightDialog");
        });

        ageSelector.setOnClickListener(v -> {
            AgeDialog ageDialog = new AgeDialog();
            ageDialog.setSexSelectionListener(age -> {
                ageSelector.setSelectValue(age);
                continueButton.setEnabled(isFilled());
            });
            ageDialog.show(getParentFragmentManager(), "AgeDialog");
        });

        weightSelector.setOnClickListener(v -> {
            WeightDialog weightDialog = new WeightDialog();
            weightDialog.setHeightSelectionListener(weight -> {
                weightSelector.setSelectValue(weight);
                continueButton.setEnabled(isFilled());
            });
            weightDialog.show(getParentFragmentManager(), "WeightDialog");

        });

        continueButton.setOnClickListener(v -> {

            Utils.LOGGER.info("Hello");
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ActivityLevelFragment())
                    .addToBackStack(null) // Optional: Allows back navigation
                    .commit();
        });
    }

    private boolean isFilled() {
        String sex = sexSelector.getSelectValue();
        String age = ageSelector.getSelectValue();
        String height = heightSelector.getSelectValue();
        String weight = weightSelector.getSelectValue();

        return !Objects.equals(sex, "Select") &&
                !Objects.equals(age, "Select") &&
                !Objects.equals(height, "Select") &&
                !Objects.equals(weight, "Select");

    }

}
