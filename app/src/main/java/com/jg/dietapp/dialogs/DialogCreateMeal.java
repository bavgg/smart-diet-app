package com.jg.dietapp.dialogs;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.jg.dietapp.R;

import java.util.Objects;

public class DialogCreateMeal extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    private Button createMealButton;
    private TextInputEditText mealNameText, caloriesText, proteinText, carbsText, fatsText, servingsText, prepTimeText;
    private RadioGroup dietTypeRadioGroup, cultureRadioGroup, regionRadioGroup, mealTimeRadioGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createMealButton = view.findViewById(R.id.createMealButton);
        mealNameText = view.findViewById(R.id.mealNameText);
        caloriesText = view.findViewById(R.id.caloriesText);
        proteinText = view.findViewById(R.id.proteinText);
        carbsText = view.findViewById(R.id.carbsText);
        fatsText = view.findViewById(R.id.fatsText);
        servingsText = view.findViewById(R.id.servingsText);
        prepTimeText = view.findViewById(R.id.prepTimeText);
        dietTypeRadioGroup = view.findViewById(R.id.dietRadioGroup);
        cultureRadioGroup = view.findViewById(R.id.cultureRadioGroup);
        regionRadioGroup = view.findViewById(R.id.regionRadioGroup);
        mealTimeRadioGroup = view.findViewById(R.id.mealTimeRadioGroup);


        TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        mealNameText.addTextChangedListener(inputWatcher);
        caloriesText.addTextChangedListener(inputWatcher);
        proteinText.addTextChangedListener(inputWatcher);
        carbsText.addTextChangedListener(inputWatcher);
        fatsText.addTextChangedListener(inputWatcher);
        servingsText.addTextChangedListener(inputWatcher);
        prepTimeText.addTextChangedListener(inputWatcher);

        dietTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());
        cultureRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());
        regionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());
        mealTimeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());

        checkFields(); // Initial check
    }

    private void checkFields() {
        boolean allFieldsFilled = !Objects.requireNonNull(mealNameText.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(caloriesText.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(proteinText.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(carbsText.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(fatsText.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(servingsText.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(prepTimeText.getText()).toString().isEmpty() &&
                dietTypeRadioGroup.getCheckedRadioButtonId() != -1 &&
                cultureRadioGroup.getCheckedRadioButtonId() != -1 &&
                regionRadioGroup.getCheckedRadioButtonId() != -1 &&
                mealTimeRadioGroup.getCheckedRadioButtonId() != -1;

        createMealButton.setEnabled(allFieldsFilled);

        if(allFieldsFilled) {
            createMealButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.primary));
            createMealButton.setTextColor(Color.WHITE);
        }

    }
}
