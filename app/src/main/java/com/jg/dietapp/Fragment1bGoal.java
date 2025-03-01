package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1bGoal extends Fragment {
    CustomCard fatLossCard, muscleGainCard, weightMaintenanceCard;
    SharedDataUser userData = MainActivity.userData;

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
            userData.setGoal("fat loss");
            updateProgress();
            fatLossCard.setCardElevation(10f);

            nextFragment(new Fragment1cAboutYou());
        });

        muscleGainCard.setOnClickListener(v -> {
            userData.setGoal("muscle gain");
            updateProgress();
            muscleGainCard.setCardElevation(10f);

            nextFragment(new Fragment1cAboutYou());
        });

        weightMaintenanceCard.setOnClickListener(v -> {
            userData.setGoal("weight maintenance");
            updateProgress();
            weightMaintenanceCard.setCardElevation(10f);

            nextFragment(new Fragment1cAboutYou());
        });

    }

    private void updateProgress() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateProgress(25);
        }
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
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateProgress(-25);
                }
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
