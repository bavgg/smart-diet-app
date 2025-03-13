package com.jg.dietapp.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.jg.dietapp.R;



public class CustomExerciseList extends MaterialCardView {
    public CustomExerciseList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_exercise_list, this, true);

        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        ImageView exerciseImage = findViewById(R.id.exercise_image);
        TextView exerciseNameText = findViewById(R.id.exercise_name_text);
        TextView caloriesBurnedText = findViewById(R.id.calories_burned_text);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomExerciseList);
        int exerciseImageAttr = attributes.getResourceId(R.styleable.CustomExerciseList_exerciseImage, 0);
        String exerciseNameAttr = attributes.getString(R.styleable.CustomExerciseList_exerciseName);
        String caloriesBurnedAttr = attributes.getString(R.styleable.CustomExerciseList_caloriesBurned);
        attributes.recycle();

        if (exerciseImageAttr != 0) {
            exerciseImage.setImageResource(exerciseImageAttr);
        }
        if (exerciseNameAttr != null) {
            exerciseNameText.setText(exerciseNameAttr);
        }
        if (caloriesBurnedAttr != null) {
            caloriesBurnedText.setText(caloriesBurnedAttr);
        }
    }
}

