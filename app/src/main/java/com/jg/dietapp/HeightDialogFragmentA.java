package com.jg.dietapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;

public class HeightDialogFragmentA extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_height_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        MaterialCardView cm = view.findViewById(R.id.cm);
//        MaterialCardView ft = view.findViewById(R.id.ft);
        NumberPicker cmPicker = view.findViewById(R.id.cmPicker);

        SharedViewModel sharedViewModel = MainActivity.sharedViewModel;

        cmPicker.setMinValue(122);  // Minimum age
        cmPicker.setMaxValue(302); // Maximum age
        cmPicker.setValue(sharedViewModel.getCmValue());

        cmPicker.setOnValueChangedListener((picker, oldVal, cm) -> {
            int feet = (int) (cm / 30.48); // Get whole feet
            double remainingCm = cm - (feet * 30.48);
            int inches = (int) Math.round(remainingCm / 2.54);

            sharedViewModel.setCmValue(cm);
        });

//        sharedViewModel.getCmValue().observe(getViewLifecycleOwner(), cmValue -> {
//            System.out.println("hello kat");
//
//
//            cmPicker.setValue(cmValue);
//        });



//        cm.setOnClickListener(v -> {
//            getChildFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new HeightDialogFragmentA())
//                    .commit();
//            cm.setStrokeColor(Color.BLACK);
//            ft.setStrokeColor(Color.WHITE);
//            ft.setCardBackgroundColor(Color.TRANSPARENT);
//
//        });
//
//        ft.setOnClickListener(v -> {
//            getChildFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new HeightDialogFragmentB())
//                    .commit();
//            ft.setStrokeColor(Color.BLACK);
//            cm.setStrokeColor(Color.WHITE);
//            cm.setCardBackgroundColor(Color.TRANSPARENT);
//
//        });

    }
}
