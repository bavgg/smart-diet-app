package com.jg.dietapp.dialogs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jg.dietapp.R;
import com.jg.dietapp.utils.Utils;

public class DialogRecipe extends BottomSheetDialogFragment {
    private static final String ARG_MEAL_NAME = "meal_name";
    private static final String ARG_IMAGE_NAME = "image_name";

    public static DialogRecipe newInstance(String mealName) {
        DialogRecipe dialogRecipe = new DialogRecipe();
        Bundle args = new Bundle();
        args.putString(ARG_MEAL_NAME, mealName);
        dialogRecipe.setArguments(args);
        return dialogRecipe;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assign UI
        TextView mealNameText = view.findViewById(R.id.meal_name);
        ImageView mealImage = view.findViewById(R.id.meal_image);



        // Get arguments
        String mealName = getArguments() != null ? getArguments().getString(ARG_MEAL_NAME) : "No Name";
        Bitmap imageBitmap = Utils.loadImageFromInternalStorage(view.getContext(), "images", mealName + ".jpg");

        System.out.println(mealName + ".jpg");

        // Set UI value
        mealNameText.setText(mealName);
        mealImage.setImageBitmap(imageBitmap);


        // Listen for button

    }
}
