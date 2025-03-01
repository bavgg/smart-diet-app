package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.sharedDataDialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DialogWeight extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    // Interface for handling OkClick Button
    private DialogWeight.OkClickListener okClickListener;
    public interface OkClickListener {
        void onOkClick(String weight);
    }
    public void setOnOkClickListener(DialogWeight.OkClickListener listener) {
        this.okClickListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_weight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the views
        MaterialCardView kg = view.findViewById(R.id.kg);
        MaterialCardView lbs = view.findViewById(R.id.lbs);
        Button okButton = view.findViewById(R.id.okButton);

        // Initialize the shared view model

        // Initialize the selected unit to kg
        AtomicReference<String> selectedUnit = new AtomicReference<>("kg");

        // Loading the first dialog fragment for kg
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DialogWeightA())
                .commit();

        // Event listeners for selecting which unit to use and switching between dialog fragments
        kg.setOnClickListener(v -> {
            selectedUnit.set("kg");

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DialogWeightA())
                    .commit();


            kg.setStrokeColor(Color.BLACK);
            lbs.setStrokeColor(Color.WHITE);
            lbs.setCardBackgroundColor(Color.TRANSPARENT);

        });
        lbs.setOnClickListener(v -> {
            selectedUnit.set("lbs");

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DialogWeightB())
                    .commit();

            lbs.setStrokeColor(Color.BLACK);
            kg.setStrokeColor(Color.WHITE);
            kg.setCardBackgroundColor(Color.TRANSPARENT);

        });

        // Listener to pass the selected weight from the dialog to the parent fragment when the OK button is clicked
        okButton.setOnClickListener(v -> {

            double lbsValue = sharedDataDialog.getLbs();
            DecimalFormat df = new DecimalFormat("#.#");

            if(Objects.equals(selectedUnit.get(), "kg")){
                Double kg1 = Utils.lbsToKg(lbsValue);
                String kgString = df.format(kg1);

                String a = kgString + " kg";
                okClickListener.onOkClick(a);
            }else if(Objects.equals(selectedUnit.get(), "lbs")) {

                String lbsString = df.format(lbsValue);

                String b = lbsString + " lbs ";
                okClickListener.onOkClick(b);
            }
            dismiss();
        });


    }
}
