package com.jg.dietapp.adapters;

import static com.jg.dietapp.fragments.main.FragmentPlan.selectedMealsID;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jg.dietapp.R;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.Utils;
import com.jg.dietapp.viewmodel.CurrentNutritionViewModel;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> mealList;
    private CurrentNutritionViewModel nutritionViewModel;
    private FirebaseDataPrefs firebaseDataPrefs;

    public MealAdapter(Context context, List<Meal> mealList, CurrentNutritionViewModel nutritionViewModel, LifecycleOwner lifecycleOwner) {
        this.mealList = mealList;
        this.nutritionViewModel = nutritionViewModel;
        this.firebaseDataPrefs = new FirebaseDataPrefs(context);

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

        int mealId = meal.getId();
        boolean isSelected = selectedMealsID.contains(mealId);

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(isSelected);

        // Use Glide for lazy loading of images
        Glide.with(holder.itemView.getContext())
                .load(Utils.getImageFile(holder.itemView.getContext(), meal.getImageName())) // Get image file path
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache for better performance
                .into(holder.mealImage);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                System.out.println(mealId);
                selectedMealsID.add(Integer.valueOf(mealId));
            } else {
                selectedMealsID.remove(Integer.valueOf(mealId));
            }

            firebaseDataPrefs.saveSelectedMealsID(selectedMealsID);

            int calories = isChecked ? (int) meal.getCalories() : -(int) meal.getCalories();
            int protein = isChecked ? meal.getProtein() : -meal.getProtein();
            int carbs = isChecked ? meal.getCarbs() : -meal.getCarbs();
            int fats = isChecked ? meal.getFats() : -meal.getFats();

            nutritionViewModel.updateNutrition(calories, protein, carbs, fats);
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, mealCalories;
        CheckBox checkBox;
        ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name_text);
            mealCalories = itemView.findViewById(R.id.calorie_count_text);
            checkBox = itemView.findViewById(R.id.checkbox);
            mealImage = itemView.findViewById(R.id.meal_image);
        }
    }
}
