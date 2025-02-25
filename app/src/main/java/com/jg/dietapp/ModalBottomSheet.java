package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(),
                com.google.android.material.R.style.Widget_MaterialComponents_BottomSheet_Modal);

        bottomSheetDialog.setContentView(R.layout.modal_bottom_sheet_content);

        // Set behavior attributes after layout is set
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog sheetDialog = (BottomSheetDialog) dialog;
            View bottomSheet = sheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setPeekHeight(123);  // Set custom peek height
            }
        });

        return bottomSheetDialog;
    }
}

