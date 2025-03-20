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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.jg.dietapp.R;
import com.jg.dietapp.models.Exercise;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.Utils;
import com.jg.dietapp.viewmodel.CurrentNutritionViewModel;

import java.util.List;



public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exerciseList;
//    private CurrentNutritionViewModel nutritionViewModel;
//    private FirebaseDataPrefs firebaseDataPrefs;

    public ExerciseAdapter(Context context, List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
//        this.nutritionViewModel = nutritionViewModel;
//        this.firebaseDataPrefs = new FirebaseDataPrefs(context);

    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_exercise_list, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        // Set UI
        holder.exerciseNameText.setText(exercise.getName());
        holder.caloriesBurnedText.setText(exercise.getCaloriesBurned() + " ");
        // Set image
        Glide.with(holder.itemView.getContext())
                .load(Utils.getImageFile(holder.itemView.getContext(), exercise.getImageName()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.exerciseImage);

        // Listener
        holder.card.setOnClickListener(v -> {

        });

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseNameText, caloriesBurnedText;
        MaterialCardView card;
        ImageView exerciseImage;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameText = itemView.findViewById(R.id.exercise_name_text);
            caloriesBurnedText = itemView.findViewById(R.id.calories_burned_text);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
            card = itemView.findViewById(R.id.card);
        }
    }
}
