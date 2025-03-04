package com.jg.dietapp;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class CustomMealList extends MaterialCardView {

    TextView selectValue;
    RadioButton radioButton;

    public CustomMealList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_meal_list, this, true);

        radioButton = findViewById(R.id.radioButton);
        ImageView mealImage = findViewById(R.id.meal_image);
        TextView mealName = findViewById(R.id.meal_name);
        TextView calorieCountText = findViewById(R.id.calorie_count_text);

        // Get attributes
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomMealList);

        int mealImageAttr = attributes.getResourceId(R.styleable.CustomMealList_mealImage, 0);
        String mealNameAttr = attributes.getString(R.styleable.CustomMealList_mealName);
        String calorieAttr = attributes.getString(R.styleable.CustomMealList_calorie);
        attributes.recycle();

        if (mealImageAttr != 0) {
            mealImage.setImageResource(mealImageAttr);
        }
        if (mealNameAttr != null) {
            mealName.setText(mealNameAttr);
        }

        if (calorieAttr != null) {
            calorieCountText.setText(calorieAttr);
        }
    }

    public void setRadioButton() {
        boolean isRadioButtonChecked = radioButton.isChecked();
        radioButton.setChecked(!isRadioButtonChecked);
    }



}

