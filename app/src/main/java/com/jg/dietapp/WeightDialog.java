package com.jg.dietapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class WeightDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    private WeightDialog.WeightSelectionListener weightSelectionListener;

    public interface WeightSelectionListener {
        void onWeightSelected(String height);
    }

    public void setHeightSelectionListener(WeightDialog.WeightSelectionListener listener) {
        this.weightSelectionListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_weight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialCardView kg = view.findViewById(R.id.kg);
        MaterialCardView lbs = view.findViewById(R.id.lbs);

        AtomicReference<String> kgLbs = new AtomicReference<>("kg");

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new WeightDialogA())
                .commit();

        kg.setOnClickListener(v -> {
            kgLbs.set("kg");
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WeightDialogA())
                    .commit();
            kg.setStrokeColor(Color.BLACK);
            lbs.setStrokeColor(Color.WHITE);
            lbs.setCardBackgroundColor(Color.TRANSPARENT);

        });

        lbs.setOnClickListener(v -> {
            kgLbs.set("lbs");
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WeightDialogB())
                    .commit();
            lbs.setStrokeColor(Color.BLACK);
            kg.setStrokeColor(Color.WHITE);
            kg.setCardBackgroundColor(Color.TRANSPARENT);

        });

        Button okButton = view.findViewById(R.id.okButton);

        SharedViewModel sharedViewModel = MainActivity.sharedViewModel;

        okButton.setOnClickListener(v -> {

            double lbsValue = sharedViewModel.getLbs();
            DecimalFormat df = new DecimalFormat("#.#");

            if(Objects.equals(kgLbs.get(), "kg")){
                System.out.println("Kilogram");
                Double kg1 = Utils.lbsToKg(lbsValue);
                String kgString = df.format(kg1);

                String a = kgString + " kg";
                weightSelectionListener.onWeightSelected(a);
            }else if(Objects.equals(kgLbs.get(), "lbs")) {
                System.out.println("Pounds");

                String lbsString = df.format(lbsValue);

                String b = lbsString + " lbs ";
                weightSelectionListener.onWeightSelected(b);
            }
            dismiss();
        });


    }
}
