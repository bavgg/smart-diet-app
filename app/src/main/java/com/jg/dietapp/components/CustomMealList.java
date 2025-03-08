package com.jg.dietapp.components;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.jg.dietapp.R;

public class CustomMealList extends MaterialCardView {
    public CustomMealList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_meal_list, this, true);

        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        ImageView mealImage = findViewById(R.id.meal_image);
        TextView mealName = findViewById(R.id.meal_name_text);
        TextView calorieCountText = findViewById(R.id.calorie_count_text);

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
}

