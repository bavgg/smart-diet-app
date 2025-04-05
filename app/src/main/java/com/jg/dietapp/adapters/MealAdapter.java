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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.jg.dietapp.R;
import com.jg.dietapp.dialogs.DialogRecipe;
import com.jg.dietapp.models.GeneratedMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.prefs.LoadPrefs;
import com.jg.dietapp.utils.Utils;
import com.jg.dietapp.viewmodel.CurrentNutritionViewModel;

import java.util.List;
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> mealList;
    private CurrentNutritionViewModel currentNutritionViewModel;
    private LoadPrefs firebaseDataPrefs;
    private FragmentManager fragmentManager;

    public MealAdapter(Context context, List<Meal> mealList, CurrentNutritionViewModel nutritionViewModel, FragmentManager fragmentManager) {
        this.mealList = mealList;
        this.currentNutritionViewModel = nutritionViewModel;
        this.firebaseDataPrefs = new LoadPrefs(context);
        this.fragmentManager = fragmentManager;
    }

    public void setMeals(List<Meal> meals) {
        this.mealList = meals;
        notifyDataSetChanged(); // Refresh UI when data changes
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

        Glide.with(holder.itemView.getContext())
                .load(Utils.getImageFile(holder.itemView.getContext(), meal.getImageName()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mealImage);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedMealsID.add(mealId);
            } else {
                selectedMealsID.remove(Integer.valueOf(mealId));
            }

            firebaseDataPrefs.saveSelectedMealsID(selectedMealsID);

            int calories = isChecked ? (int) meal.getCalories() : (int) -meal.getCalories();
            int protein = isChecked ? meal.getProtein() : -meal.getProtein();
            int carbs = isChecked ? meal.getCarbs() : -meal.getCarbs();
            int fats = isChecked ? meal.getFats() : -meal.getFats();

            currentNutritionViewModel.updateNutrition(calories, protein, carbs, fats);
        });

        holder.card.setOnClickListener(v -> {
            DialogRecipe dialogRecipe = DialogRecipe.newInstance(meal.getName());
            dialogRecipe.show(fragmentManager, "DialogRecipe");
        });
    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, mealCalories;
        CheckBox checkBox;
        ImageView mealImage;
        MaterialCardView card;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name_text);
            mealCalories = itemView.findViewById(R.id.calorie_count_text);
            checkBox = itemView.findViewById(R.id.checkbox);
            mealImage = itemView.findViewById(R.id.meal_image);
            card = itemView.findViewById(R.id.card);
        }
    }
}

