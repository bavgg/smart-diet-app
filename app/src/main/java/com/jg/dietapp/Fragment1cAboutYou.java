package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.decreaseProgress;
import static com.jg.dietapp.MainActivity.increaseProgress;
import static com.jg.dietapp.MainActivity.userInput;

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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Fragment1cAboutYou extends Fragment {
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


        setupPressBackListener();
        setupClickListeners(view);
    }

    private void setupClickListeners(View view) {
        sexSelector = view.findViewById(R.id.sexSelector);
        ageSelector = view.findViewById(R.id.ageSelector);
        heightSelector = view.findViewById(R.id.heightSelector);
        weightSelector = view.findViewById(R.id.weightSelector);
        continueButton = view.findViewById(R.id.continueButton);

        AtomicReference<String> sexInfo = new AtomicReference<>("");
        AtomicInteger ageInfo = new AtomicInteger();
        AtomicInteger heightInfo = new AtomicInteger();
        AtomicReference<Double> weightInfo = new AtomicReference<>((double) 0);

        sexSelector.setOnClickListener(v -> {
            DialogSex sexDialog = new DialogSex();
            sexDialog.setSexSelectionListener(sex -> {
                sexSelector.setSelectValue(sex);
                continueButton.setEnabled(isFilled());
                sexInfo.set(sex);
            });
            sexDialog.show(getParentFragmentManager(), "SexDialog");
        });

        heightSelector.setOnClickListener(v -> {

            DialogHeight heightDialog = new DialogHeight();
            heightDialog.setOnOkClickListener((height, unit) -> {
                if(Objects.equals(unit, "cm")){
                    heightSelector.setSelectValue(height + " cm");
                }else if(Objects.equals(unit, "ft")){
                    heightSelector.setSelectValue(height + " ft");
                }
                heightInfo.set(height);
                continueButton.setEnabled(isFilled());
            });
            heightDialog.show(getParentFragmentManager(), "HeightDialog");
        });

        ageSelector.setOnClickListener(v -> {
            DialogAge ageDialog = new DialogAge();
            ageDialog.setSexSelectionListener(age -> {
                ageSelector.setSelectValue(age + "years");
                continueButton.setEnabled(isFilled());
                ageInfo.set(age);
            });
            ageDialog.show(getParentFragmentManager(), "AgeDialog");
        });

        weightSelector.setOnClickListener(v -> {
            DialogWeight weightDialog = new DialogWeight();
            weightDialog.setOnOkClickListener((weight, unit) -> {
                if(Objects.equals(unit, "kg")){
                    double kg = Utils.lbsToKg(weight);
                    weightSelector.setSelectValue(kg + " kg");
                }else if(Objects.equals(unit, "lbs")){
                    weightSelector.setSelectValue(weight + " lbs");
                }
                continueButton.setEnabled(isFilled());
                weightInfo.set(weight);
            });
            weightDialog.show(getParentFragmentManager(), "WeightDialog");

        });

        continueButton.setOnClickListener(v -> {


            if(Objects.equals(sexInfo.get(), "Male")){
                userInput.setSex(EnumSex.MALE);
            } else {
                userInput.setSex(EnumSex.FEMALE);
            }
            userInput.setAge(ageInfo.get());
            userInput.setHeight(heightInfo.get());
            userInput.setWeight(weightInfo.get());

            increaseProgress();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1dActivityLevel())
                    .addToBackStack(null)
                    .commit();
        });
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
