package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

public class SexDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private SexSelectionListener sexSelectionListener;

    public interface SexSelectionListener {
        void onSexSelected(String sex);
    }

    public void setSexSelectionListener(SexSelectionListener listener) {
        this.sexSelectionListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_sex, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialCardView male = view.findViewById(R.id.male);
        male.setOnClickListener(v -> {
            sexSelectionListener.onSexSelected("Male");
            dismiss();
        });

        MaterialCardView female = view.findViewById(R.id.female);
        female.setOnClickListener(v -> {
            sexSelectionListener.onSexSelected("Female");
            dismiss();
        });
    }
}
