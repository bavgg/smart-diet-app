package com.jg.dietapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.jg.dietapp.R;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.viewmodel.NutritionViewModel;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> mealList;
    private NutritionViewModel mealViewModel;

    public MealAdapter(List<Meal> mealList, NutritionViewModel mealViewModel, LifecycleOwner lifecycleOwner) {
        this.mealList = mealList;
        this.mealViewModel = mealViewModel;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_meal_list, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.mealName.setText(meal.getName());
        holder.mealCalories.setText(meal.getCalories() + " ");

        // Prevent incorrect state reuse
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(meal.isSelected());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            meal.setSelected(isChecked);
            System.out.println(isChecked);

            int calories = isChecked ? (int) meal.getCalories() : -(int) meal.getCalories();
            int protein = isChecked ? meal.getProtein() : -meal.getProtein();
            int carbs = isChecked ? meal.getCarbs() : -meal.getCarbs();
            int fats = isChecked ? meal.getFats() : -meal.getFats();

            mealViewModel.updateNutrition(calories, protein, carbs, fats);
        });
    }


    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, mealCalories;
        CheckBox checkBox;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name_text);
            mealCalories = itemView.findViewById(R.id.calorie_count_text);
            checkBox = itemView.findViewById(R.id.checkbox);
//            mealDietType = itemView.findViewById(R.id.textMealDietType);
        }
    }
}
