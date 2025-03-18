package com.jg.dietapp.dialogs;

import static com.jg.dietapp.UserInputActivity.dialogViewModel;

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

public class DialogHeight extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    // Interface for handling OkClick Button
    private DialogHeight.OkClickListener okClickListener;
    public interface OkClickListener {
        void onOkClick(int height, String unit);
    }
    public void setOnOkClickListener(DialogHeight.OkClickListener listener) {
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
                .replace(R.id.fragment_container, new DialogHeightFragmentA())
                .commit();

        cm.setOnClickListener(v -> {
            cmFeet.set("cm");
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DialogHeightFragmentA())
                    .commit();
            cm.setStrokeColor(Color.BLACK);
            ft.setStrokeColor(Color.WHITE);
            ft.setCardBackgroundColor(Color.TRANSPARENT);

        });

        ft.setOnClickListener(v -> {
            cmFeet.set("ft");
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DialogHeightFragmentB())
                    .commit();
            ft.setStrokeColor(Color.BLACK);
            cm.setStrokeColor(Color.WHITE);
            cm.setCardBackgroundColor(Color.TRANSPARENT);

        });

        Button okButton = view.findViewById(R.id.okButton);

        okButton.setOnClickListener(v -> {

            int cValue = dialogViewModel.getCmValue();
            if(Objects.equals(cmFeet.get(), "cm")){
//                String cmText = cValue + " cm";
                okClickListener.onOkClick(cValue, "cm");
            }else if(Objects.equals(cmFeet.get(), "ft")) {

//                int feet = (int) (cValue / 30.48); // Convert cm to feet
//                int inches = (int) Math.ceil((cValue % 30.48) / 2.54);
//                String ftInText = feet + " ft " + inches + " in";

                okClickListener.onOkClick(cValue, "ft");
            }
            dismiss();
        });


    }
}
