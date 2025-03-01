package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.sharedDataDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DialogWeightA extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_weight_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NumberPicker kgPicker = view.findViewById(R.id.kgPicker);
        NumberPicker kgPickerDecimal = view.findViewById(R.id.kgPickerDecimal);


        double lbs = sharedDataDialog.getLbs();

//        convert lbs to kg
        double kg = lbs * 0.453592;


        int kgWhole = (int) kg;
        int kgDecimal = (int) ((kg - kgWhole) * 10);

        kgPicker.setMinValue(30);
        kgPicker.setMaxValue(200);
        kgPicker.setValue(kgWhole);

        kgPicker.setOnValueChangedListener((picker, oldVal, kgVal) -> {
            int kgDecimalVal = kgPickerDecimal.getValue();
            double kgs = kgVal + (kgDecimalVal / 10.0f);
            double lbsV = kgs * 2.20462;
            int lbsWhole = (int) lbsV;
            int lbsDecimal = (int) ((lbsV - lbsWhole) * 10);

            sharedDataDialog.setLbs(lbsWhole, lbsDecimal);
        });

        kgPickerDecimal.setMinValue(0);
        kgPickerDecimal.setMaxValue(9);
        kgPickerDecimal.setValue(kgDecimal);

        kgPickerDecimal.setOnValueChangedListener((picker, oldVal, kgDecimalVal) -> {
            int kgWholeVal = kgPicker.getValue();
            sharedDataDialog.setLbs(kgWholeVal, kgDecimalVal);
        });


    }
}
