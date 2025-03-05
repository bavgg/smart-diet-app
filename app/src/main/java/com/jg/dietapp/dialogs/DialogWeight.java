package com.jg.dietapp.dialogs;

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
import com.jg.dietapp.R;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DialogWeight extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    // Interface for handling OkClick Button
    private DialogWeight.OkClickListener okClickListener;
    public interface OkClickListener {
        void onOkClick(Double weight, String unit);
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

        MaterialCardView kg = view.findViewById(R.id.kg);
        MaterialCardView lbs = view.findViewById(R.id.lbs);
        Button okButton = view.findViewById(R.id.okButton);


        AtomicReference<String> selectedUnit = new AtomicReference<>("kg");

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DialogWeightA())
                .commit();

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

        okButton.setOnClickListener(v -> {

            double lbsValue = sharedDataDialog.getLbs();

            if(Objects.equals(selectedUnit.get(), "kg")){


                okClickListener.onOkClick(lbsValue, "kg");
            }else if(Objects.equals(selectedUnit.get(), "lbs")) {


                okClickListener.onOkClick(lbsValue, "lbs");
            }
            dismiss();
        });


    }
}
