package com.jg.dietapp.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.jg.dietapp.models.Meal;

import java.util.List;



public class ImageBitmapsViewModel extends ViewModel {
    private List<Bitmap> imageBitmaps;

    public void setImageBitmaps(List<Bitmap> imageBitmaps) {

        this.imageBitmaps = imageBitmaps;
    }

    public List<Bitmap> getImageBitmaps() {
        return imageBitmaps;
    }
}
