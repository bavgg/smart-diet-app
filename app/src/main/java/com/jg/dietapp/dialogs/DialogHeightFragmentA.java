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

public class DialogHeightFragmentA extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_height_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NumberPicker cmPicker = view.findViewById(R.id.cmPicker);

        cmPicker.setMinValue(122);  // Minimum age
        cmPicker.setMaxValue(302); // Maximum age
        cmPicker.setValue(sharedDataDialog.getCmValue());

        cmPicker.setOnValueChangedListener((picker, oldVal, cm) -> {
            int feet = (int) (cm / 30.48); // Get whole feet
            double remainingCm = cm - (feet * 30.48);
            int inches = (int) Math.round(remainingCm / 2.54);

            sharedDataDialog.setCmValue(cm);
        });


    }
}
