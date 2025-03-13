package com.jg.dietapp;


import static com.jg.dietapp.utils.Utils.loadImageFromAssets;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

//
//    @Override
//    public void onStart(){
//        super.onStart();
//
//
//    }
    ImageView testImage;

    // Entry point
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test);

        System.out.println(getFilesDir());
        testImage = findViewById(R.id.test_image);

        testImage.setImageBitmap(loadImageFromAssets(this, "abuos.jpg"));



    }


}