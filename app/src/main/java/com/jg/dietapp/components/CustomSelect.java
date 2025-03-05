package com.jg.dietapp.components;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.jg.dietapp.R;

public class CustomSelect extends MaterialCardView {

    TextView selectValue;

    public CustomSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_select, this, true);

        ImageView icon = findViewById(R.id.icon);
        TextView title = findViewById(R.id.title);
        selectValue = findViewById(R.id.selectValue);

        // Get attributes
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSelect);
        int iconAttr = attributes.getResourceId(R.styleable.CustomSelect_icon, 0);
        String titleAttr = attributes.getString(R.styleable.CustomSelect_title);
        String selectValueAttr = attributes.getString(R.styleable.CustomSelect_selectValue);
        attributes.recycle();

        if (titleAttr != null) {
            title.setText(titleAttr);
        }

        if (selectValue != null) {
            selectValue.setText(selectValueAttr);
        }

        if (iconAttr != 0) {
            icon.setImageResource(iconAttr);
        }

    }

    public void setSelectValue(String newText) {
        selectValue.setText(newText);
    }

    public String getSelectValue() {
        return selectValue.getText().toString();
    }

}

