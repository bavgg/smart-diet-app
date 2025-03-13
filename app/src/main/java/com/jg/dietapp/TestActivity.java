package com.jg.dietapp;


import static com.jg.dietapp.utils.Utils.copyAssetToInternalStorage;
import static com.jg.dietapp.utils.Utils.loadImageFromAssets;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

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

        try {
            String[] assetFiles = this.getAssets().list(""); // Root of assets folder
            if (assetFiles != null) {
                for (String file : assetFiles) {
                    if (file.toLowerCase().endsWith(".jpg")) { // Filter only .jpg files
                        System.out.println("JPG File: " + file);
                        copyAssetToInternalStorage(this, "pre-images", file);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}