package com.jg.dietapp.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jg.dietapp.R;

public class DialogAge extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    private DialogAge.AgeSelectionListener ageSelectionListener;

    public interface AgeSelectionListener {
        void onAgeSelected(int age);
    }

    public void setSexSelectionListener(DialogAge.AgeSelectionListener listener) {
        this.ageSelectionListener = listener;
    }

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
        
        Button okButton = view.findViewById(R.id.okButton);
        
        okButton.setOnClickListener(v -> {
            int selectedAge = agePicker.getValue();
            ageSelectionListener.onAgeSelected(selectedAge);
            dismiss();  
        });


    }
}
