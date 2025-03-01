package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class DialogWeightB extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_weight_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NumberPicker lbsPicker = view.findViewById(R.id.lbsPicker);
        NumberPicker lbsPickerDecimal = view.findViewById(R.id.lbsPickerDecimal);

        SharedViewModel sharedViewModel = MainActivity.sharedViewModel;

        double lbs = sharedViewModel.getLbs();
        int lbsWhole = (int) lbs;
        int lbsDecimal = (int) ((lbs - lbsWhole) * 10);

        lbsPicker.setMinValue(66);
        lbsPicker.setMaxValue(442);
        lbsPicker.setValue(lbsWhole);

        lbsPickerDecimal.setMinValue(0);
        lbsPickerDecimal.setMaxValue(9);
        lbsPickerDecimal.setValue(lbsDecimal);

        lbsPicker.setOnValueChangedListener((picker, oldVal, lbsVal) -> {
            sharedViewModel.setLbs(lbsVal, lbsPickerDecimal.getValue());
        });

        lbsPickerDecimal.setOnValueChangedListener((picker, oldVal, lbsDecimalVal) -> {
            sharedViewModel.setLbs(lbsPicker.getValue(), lbsDecimalVal);
        });


    }
}
