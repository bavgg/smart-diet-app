package com.jg.dietapp.fragments.user_input;

import static com.jg.dietapp.UserInputActivity.decreaseProgress;
import static com.jg.dietapp.UserInputActivity.increaseProgress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.components.CustomCard;
import com.jg.dietapp.enums.EnumGoal;
import com.jg.dietapp.UserInputActivity;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.R;

public class FragmentGoal extends Fragment {
    CustomCard fatLossCard, muscleGainCard, weightMaintenanceCard;
    UserInput userInput = UserInputActivity.userInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fatLossCard = view.findViewById(R.id.fatLossCard);
        muscleGainCard = view.findViewById(R.id.muscleGainCard);
        weightMaintenanceCard = view.findViewById(R.id.weightMaintenanceCard);

        setupBackPressedListener();

        fatLossCard.setOnClickListener(v -> {
            userInput.setGoal(EnumGoal.LOSE_WEIGHT);
            increaseProgress();
            fatLossCard.setCardElevation(10f);

            nextFragment(new FragmentAboutYou());
        });

        muscleGainCard.setOnClickListener(v -> {
            userInput.setGoal(EnumGoal.GAIN_MUSCLE);
            increaseProgress();
            muscleGainCard.setCardElevation(10f);

            nextFragment(new FragmentAboutYou());
        });

        weightMaintenanceCard.setOnClickListener(v -> {
            userInput.setGoal(EnumGoal.MAINTAIN_WEIGHT);
            increaseProgress();
            weightMaintenanceCard.setCardElevation(10f);

            nextFragment(new FragmentAboutYou());
        });

    }

    private void nextFragment(Fragment nextFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupBackPressedListener() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                decreaseProgress();
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
