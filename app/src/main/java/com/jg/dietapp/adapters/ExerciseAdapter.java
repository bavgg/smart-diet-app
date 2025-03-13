package com.jg.dietapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.jg.dietapp.R;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.SharedPrefsMeals;
import com.jg.dietapp.viewmodel.NutritionViewModel;

import java.util.List;



public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Meal> mealList;
    private NutritionViewModel nutritionViewModel;
    private SharedPrefsMeals sharedPrefsMeals;

    public ExerciseAdapter(Context context, List<Meal> mealList, NutritionViewModel nutritionViewModel, LifecycleOwner lifecycleOwner) {
        this.mealList = mealList;
        this.nutritionViewModel = nutritionViewModel;
        this.sharedPrefsMeals = new SharedPrefsMeals(context);

    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_exercise_list, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Meal meal = mealList.get(position);
//        holder.mealName.setText(meal.getName());
//        holder.mealCalories.setText(meal.getCalories() + " ");
//
//        int mealId = meal.getId();
//        boolean isSelected = selectedMeals.contains(mealId);
//
//        holder.checkBox.setOnCheckedChangeListener(null);
//        holder.checkBox.setChecked(isSelected);
//
//        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                System.out.println(mealId);
//                selectedMeals.add(Integer.valueOf(mealId));
//            } else {
//                selectedMeals.remove(Integer.valueOf(mealId));
//            }
//
//            sharedPrefsMeals.saveSelectedMeals(selectedMeals);
//
//            int calories = isChecked ? (int) meal.getCalories() : -(int) meal.getCalories();
//            int protein = isChecked ? meal.getProtein() : -meal.getProtein();
//            int carbs = isChecked ? meal.getCarbs() : -meal.getCarbs();
//            int fats = isChecked ? meal.getFats() : -meal.getFats();
//
//            nutritionViewModel.updateNutrition(calories, protein, carbs, fats);
//        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseNameText, caloriesBurnedText;
        CheckBox checkBox;
//        ImageView

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
//            mealName = itemView.findViewById(R.id.meal_name_text);
//            mealCalories = itemView.findViewById(R.id.calorie_count_text);
//            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
