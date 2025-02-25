package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class GoalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Close Bottom Sheet on Button Click
        MaterialCardView card1 = view.findViewById(R.id.card1);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateProgress(25);
        }

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateProgress(-25);
                }
                getParentFragmentManager().popBackStack();
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card1.setCardElevation(10f);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AboutYouFragment())
                        .addToBackStack(null) // Optional: Allows back navigation
                        .commit();
            }
        });

        // Close Bottom Sheet on Button Click
//        Button startNow = view.findViewById(R.id.startNow);

//        startNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new GoalFragment())
//                        .addToBackStack(null) // Optional: Allows back navigation
//                        .commit();
//            }
//        });

    }
}
