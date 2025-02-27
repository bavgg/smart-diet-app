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

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class HeightDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    // Interface for handling OkClick Button
    private HeightDialog.OkClickListener okClickListener;
    public interface OkClickListener {
        void onOkClick(String weight);
    }
    public void setOnOkClickListener(HeightDialog.OkClickListener listener) {
        this.okClickListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_height, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialCardView cm = view.findViewById(R.id.cm);
        MaterialCardView ft = view.findViewById(R.id.ft);

        AtomicReference<String> cmFeet = new AtomicReference<>("cm");

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HeightDialogFragmentA())
                .commit();

        cm.setOnClickListener(v -> {
            cmFeet.set("cm");
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HeightDialogFragmentA())
                    .commit();
            cm.setStrokeColor(Color.BLACK);
            ft.setStrokeColor(Color.WHITE);
            ft.setCardBackgroundColor(Color.TRANSPARENT);

        });

        ft.setOnClickListener(v -> {
            cmFeet.set("ft");
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HeightDialogFragmentB())
                    .commit();
            ft.setStrokeColor(Color.BLACK);
            cm.setStrokeColor(Color.WHITE);
            cm.setCardBackgroundColor(Color.TRANSPARENT);

        });

        Button okButton = view.findViewById(R.id.okButton);

        SharedViewModel sharedViewModel = MainActivity.sharedViewModel;

        okButton.setOnClickListener(v -> {

            int cValue = sharedViewModel.getCmValue();
            if(Objects.equals(cmFeet.get(), "cm")){
                String cmText = cValue + " cm";
                okClickListener.onOkClick(cmText);
            }else if(Objects.equals(cmFeet.get(), "ft")) {

                int feet = (int) (cValue / 30.48); // Convert cm to feet
                int inches = (int) Math.ceil((cValue % 30.48) / 2.54);
                String ftInText = feet + " ft " + inches + " in";

                okClickListener.onOkClick(ftInText);
            }
            dismiss();
        });


    }
}
