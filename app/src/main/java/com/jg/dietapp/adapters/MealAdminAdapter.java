package com.jg.dietapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jg.dietapp.R;
import com.jg.dietapp.models.Meal;

import java.util.List;

public class MealAdminAdapter extends RecyclerView.Adapter<MealAdminAdapter.MealViewHolder> {
    private final List<Meal> mealList;


    public MealAdminAdapter( List<Meal> mealList) {
        this.mealList = mealList;
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
        holder.mealNameText.setText(meal.getName());
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealNameText;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealNameText = itemView.findViewById(R.id.meal_name_text);

        }
    }
}
