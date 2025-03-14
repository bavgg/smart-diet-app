package com.jg.dietapp.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import com.jg.dietapp.R;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.utils.Utils;

import java.util.List;

public class MealAdminAdapter extends RecyclerView.Adapter<MealAdminAdapter.MealViewHolder> {
    private final List<Meal> mealList;
    private final List<Bitmap> imageBitmaps;


    public MealAdminAdapter(List<Meal> mealList, List<Bitmap> imageBitmaps) {
        this.mealList = mealList;
        this.imageBitmaps = imageBitmaps;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_meal_admin, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        Bitmap imageBitmap = imageBitmaps.get(position);
        holder.mealNameText.setText(meal.getName());
        holder.mealImage.setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealNameText;
        ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealNameText = itemView.findViewById(R.id.meal_name_text);
            mealImage = itemView.findViewById(R.id.meal_image);


        }
    }
}
