package com.jg.dietapp;

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

public class Fragment1cAboutYou extends Fragment {
    SharedDataUser userData = MainActivity.userData;
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

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateProgress(-25);
                }
                getParentFragmentManager().popBackStack();
            }
        });

        setupPressBackListener();
        setupClickListeners(view);
    }

    private void setupClickListeners(View view) {
        sexSelector = view.findViewById(R.id.sexSelector);
        ageSelector = view.findViewById(R.id.ageSelector);
        heightSelector = view.findViewById(R.id.heightSelector);
        weightSelector = view.findViewById(R.id.weightSelector);
        continueButton = view.findViewById(R.id.continueButton);


        sexSelector.setOnClickListener(v -> {
            DialogSex sexDialog = new DialogSex();
            sexDialog.setSexSelectionListener(sex -> {
                sexSelector.setSelectValue(sex);
                continueButton.setEnabled(isFilled());
            });
            sexDialog.show(getParentFragmentManager(), "SexDialog");
        });

        heightSelector.setOnClickListener(v -> {

            DialogHeight heightDialog = new DialogHeight();
            heightDialog.setOnOkClickListener(height -> {
                heightSelector.setSelectValue(height);
                continueButton.setEnabled(isFilled());
            });
            heightDialog.show(getParentFragmentManager(), "HeightDialog");
        });

        ageSelector.setOnClickListener(v -> {
            DialogAge ageDialog = new DialogAge();
            ageDialog.setSexSelectionListener(age -> {
                ageSelector.setSelectValue(age);
                continueButton.setEnabled(isFilled());
            });
            ageDialog.show(getParentFragmentManager(), "AgeDialog");
        });

        weightSelector.setOnClickListener(v -> {
            DialogWeight weightDialog = new DialogWeight();
            weightDialog.setOnOkClickListener(weight -> {
                weightSelector.setSelectValue(weight);
                continueButton.setEnabled(isFilled());
            });
            weightDialog.show(getParentFragmentManager(), "WeightDialog");

        });

        continueButton.setOnClickListener(v -> {
            String sex = sexSelector.getSelectValue();
            String age = ageSelector.getSelectValue();
            String height = heightSelector.getSelectValue();
            String weight = weightSelector.getSelectValue();

            userData.setSex(sex);
            userData.setAge(age);
            userData.setHeight(height);
            userData.setWeight(weight);

            updateProgress();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1dActivityLevel())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void updateProgress() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateProgress(25);
        }
    }

    private void setupPressBackListener() {
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
