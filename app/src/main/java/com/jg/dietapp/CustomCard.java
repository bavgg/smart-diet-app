package com.jg.dietapp;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

public class CustomCard extends MaterialCardView {

    private boolean isClicked = false;

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_card, this, true);
        MaterialCardView card = findViewById(R.id.card);
        ImageView icon = findViewById(R.id.icon);
        TextView head = findViewById(R.id.head);
        TextView body = findViewById(R.id.body);

        int defaultBackgroundColor = card.getCardBackgroundColor().getDefaultColor();
        int defaultStrokeColor = card.getStrokeColor();


//        card.setOnClickListener(v -> {
//
//            if(isClicked) {
//                card.setBackgroundColor(defaultBackgroundColor);
//                card.setStrokeColor(defaultStrokeColor);
//            } else {
//                int color = ContextCompat.getColor(context, R.color.primary);
//                card.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryTint));
//                card.setStrokeColor(color);
//            }
//
//
//
//            isClicked = !isClicked;
//            System.out.println(isClicked);
//        });

        // Get custom attributes
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomCard);
        String headAttr = attributes.getString(R.styleable.CustomCard_head);
        String bodyAttr = attributes.getString(R.styleable.CustomCard_body);
        int iconAttr = attributes.getResourceId(R.styleable.CustomCard_cardIcon, 0);
        attributes.recycle();

        if (headAttr != null) {
            head.setText(headAttr);
        }

        if (bodyAttr != null) {
            body.setText(bodyAttr);
        }

        if (iconAttr != 0) {
            icon.setImageResource(iconAttr);
        }
    }


}

