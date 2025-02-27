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

import java.util.Arrays;

public class HeightDialogFragmentB extends Fragment {
    private SharedViewModel sharedViewModel;
    NumberPicker ftPicker, inPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_height_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        MaterialCardView cm = view.findViewById(R.id.cm);
//        MaterialCardView ft = view.findViewById(R.id.ft);
        ftPicker = view.findViewById(R.id.ftPicker);
        inPicker = view.findViewById(R.id.inPicker);

//        Integer cmValue = 165;



        SharedViewModel sharedViewModel = MainActivity.sharedViewModel;

        ftPicker.setOnValueChangedListener((picker, oldVal, feet) -> {
            int inches = inPicker.getValue();
            int cm = (int) Math.round((feet * 30.48) + (inches * 2.54));
            System.out.println(cm);
            sharedViewModel.setCmValue(cm);
        });

        inPicker.setOnValueChangedListener((picker, oldVal, inches) -> {
            int feet = ftPicker.getValue();
            int cm = (int) Math.round((feet * 30.48) + (inches * 2.54));
            System.out.println(cm);
            sharedViewModel.setCmValue(cm);
        });

        int cValue = sharedViewModel.getCmValue();
        System.out.println(cValue);

        int feet = (int) (cValue / 30.48); // Convert cm to feet
        int inches = (int) Math.ceil((cValue % 30.48) / 2.54);

        System.out.println(feet);
        System.out.println(inches);


//        int[] feetInches = sharedViewModel.getFeetInchValue().getValue();
//        if(feetInches != null) {
//            System.out.println("Hooray" + feetInches[0] + feetInches[1]);
////            ftPicker.setValue(99);
////            inPicker.setValue(feetInches[1]);
//        }
//        System.out.println(Arrays.toString(sharedViewModel.getFeetInchValue().getValue()));
//
        ftPicker.setMinValue(4);
        ftPicker.setMaxValue(9);
//        ftPicker.setValue(4);

        inPicker.setMinValue(0);
        inPicker.setMaxValue(11);

        ftPicker.setValue(feet);
        inPicker.setValue(inches);
//
//
//
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
