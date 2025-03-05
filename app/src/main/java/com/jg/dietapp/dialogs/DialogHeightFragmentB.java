package com.jg.dietapp.dialogs;

import static com.jg.dietapp.MainActivity.sharedDataDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.R;

public class DialogHeightFragmentB extends Fragment {
    NumberPicker ftPicker, inPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_height_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ftPicker = view.findViewById(R.id.ftPicker);
        inPicker = view.findViewById(R.id.inPicker);

        ftPicker.setOnValueChangedListener((picker, oldVal, feet) -> {
            int inches = inPicker.getValue();
            int cm = (int) Math.round((feet * 30.48) + (inches * 2.54));
            System.out.println(cm);
            sharedDataDialog.setCmValue(cm);
        });

        inPicker.setOnValueChangedListener((picker, oldVal, inches) -> {
            int feet = ftPicker.getValue();
            int cm = (int) Math.round((feet * 30.48) + (inches * 2.54));
            System.out.println(cm);
            sharedDataDialog.setCmValue(cm);
        });

        int cValue = sharedDataDialog.getCmValue();
        System.out.println(cValue);

        int feet = (int) (cValue / 30.48); // Convert cm to feet
        int inches = (int) Math.ceil((cValue % 30.48) / 2.54);

        System.out.println(feet);
        System.out.println(inches);


        ftPicker.setMinValue(4);
        ftPicker.setMaxValue(9);

        inPicker.setMinValue(0);
        inPicker.setMaxValue(11);

        ftPicker.setValue(feet);
        inPicker.setValue(inches);

    }
}
