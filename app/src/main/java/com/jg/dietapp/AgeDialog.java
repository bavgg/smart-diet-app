package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AgeDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_age, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NumberPicker agePicker = view.findViewById(R.id.agePicker);
        agePicker.setMinValue(1);  // Minimum age
        agePicker.setMaxValue(120); // Maximum age
        agePicker.setValue(25);  // Default selected age


    }
}
