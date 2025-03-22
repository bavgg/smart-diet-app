package com.jg.dietapp.dialogs;

import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jg.dietapp.R;
import com.jg.dietapp.utils.Utils;


import java.io.IOException;



public class DialogExercise extends BottomSheetDialogFragment {

    private static final String ARG_EXERCISE_NAME = "exercise_name";

    public static DialogExercise newInstance(String exerciseName) {
        DialogExercise fragment = new DialogExercise();
        Bundle args = new Bundle();
        args.putString(ARG_EXERCISE_NAME, exerciseName);
        fragment.setArguments(args);
        return fragment;
    }

    // Initialize UI





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assign UI
        TextView exerciseNameText = view.findViewById(R.id.exercise_name_text);
        ImageView exerciseImage = view.findViewById(R.id.exercise_image);


        // Get arguments
        // Get arguments
        String exerciseName = getArguments() != null ? getArguments().getString(ARG_EXERCISE_NAME) : "No Name";
        Bitmap imageBitmap = Utils.loadImageFromInternalStorage(view.getContext(), "images", exerciseName + ".jpg");


        // Set UI value
        exerciseNameText.setText(exerciseName);
        if(imageBitmap != null) {
            exerciseImage.setImageBitmap(imageBitmap);
        }



        // Listen for button

    }



}