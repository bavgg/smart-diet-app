package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class AboutYouFragment extends Fragment {
    MaterialCardView sexSelector, ageSelector, heightSelector, weightSelector;
    TextView sexSelectorValue, ageSelectorValue, heightSelectorValue, weightSelectorValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aboutyou, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Update progress if the fragment is inside MainActivity
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateProgress(25);
        }

        // Setup back press behavior
        setupBackPressHandler();

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
        sexSelectorValue = view.findViewById(R.id.sexSelectorValue);
        ageSelector = view.findViewById(R.id.ageSelector);

        // Show SexDialog when clicked
        sexSelector.setOnClickListener(v -> {
            SexDialog sexDialog = new SexDialog();
            sexDialog.setSexSelectionListener(sex -> {
                sexSelectorValue.setText(sex);
            });
            sexDialog.show(getParentFragmentManager(), "SexDialog");
        });

        // Show AgeDialog when clicked
        ageSelector.setOnClickListener(v -> new AgeDialog().show(getParentFragmentManager(), "AgeDialog"));
    }
//
//    private void validateAndContinue() {
//        String name = nameInput.getText().toString().trim();
//        String age = ageInput.getText().toString().trim();
//
//        if (TextUtils.isEmpty(name)) {
//            nameInput.setError("Name is required");
//            return;
//        }
//
//        if (TextUtils.isEmpty(age)) {
//            ageInput.setError("Age is required");
//            return;
//        }
//
//        // Store input data
//        formData.put("name", name);
//        formData.put("age", age);
//
//        // Proceed to next step (e.g., dismiss and open next fragment)
//        Toast.makeText(getContext(), "Form Submitted!", Toast.LENGTH_SHORT).show();
//        dismiss(); // Close the bottom sheet
//    }
}
