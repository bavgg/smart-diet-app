package com.jg.dietapp.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.FirebaseDataPrefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseUtils {

    private final FirebaseDataPrefs prefs;
    private final DatabaseReference databaseRef;

    public FirebaseUtils(Context context) {
        this.prefs = new FirebaseDataPrefs(context);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            this.databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("preferences");
        } else {
            this.databaseRef = null;
            Log.w("SYNC", "No authenticated user. Skipping Firebase initialization.");
        }
    }

    // Sync local data to Firebase
    public void syncPreferencesToFirebase() {
        if (databaseRef == null) return;

        Map<String, Object> preferences = Map.of(
                "kcal", prefs.getKcal(),
                "protein", prefs.getProtein(),
                "carbs", prefs.getCarbs(),
                "fat", prefs.getFat(),
                "base_calories", prefs.getBaseCalories(),
                "user_inputs", new Gson().toJson(prefs.getUser()),
                "selected_meals_id", prefs.getSelectedMealsID(),
                "breakfast_meals", new Gson().toJson(prefs.getBreakfastMeals()),
                "lunch_meals", new Gson().toJson(prefs.getLunchMeals()),
                "dinner_meals", new Gson().toJson(prefs.getDinnerMeals())
        );

        databaseRef.setValue(preferences)
                .addOnSuccessListener(aVoid -> Log.d("SYNC", "Preferences uploaded successfully"))
                .addOnFailureListener(e -> Log.e("SYNC", "Failed to sync preferences", e));
    }

    // Load data from Firebase and update SharedPreferences
    public void loadPreferencesFromFirebase() {
        if (databaseRef == null) return;

        databaseRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot dataSnapshot = task.getResult();

                prefs.setKcal(getIntValue(dataSnapshot, "kcal"));
                prefs.setProtein(getIntValue(dataSnapshot, "protein"));
                prefs.setCarbs(getIntValue(dataSnapshot, "carbs"));
                prefs.setFat(getIntValue(dataSnapshot, "fat"));
                prefs.setBaseCalories(getIntValue(dataSnapshot, "base_calories"));

                // Deserialize JSON strings into objects
                prefs.setUser(getObjectFromJson(dataSnapshot, "user_inputs", UserInput.class));
                prefs.setSelectedMealsID(getListFromJson(dataSnapshot, "selected_meals_id", Integer.class));
                prefs.setBreakfastMeals(getListFromJson(dataSnapshot, "breakfast_meals", Meal.class));
                prefs.setLunchMeals(getListFromJson(dataSnapshot, "lunch_meals", Meal.class));
                prefs.setDinnerMeals(getListFromJson(dataSnapshot, "dinner_meals", Meal.class));

                Log.d("SYNC", "Preferences loaded from Firebase and saved locally!");
            } else {
                Log.e("SYNC", "Failed to load preferences from Firebase.");
            }
        });
    }

    private int getIntValue(DataSnapshot dataSnapshot, String key) {
        Integer value = dataSnapshot.child(key).getValue(Integer.class);
        return value != null ? value : 0;
    }

    private <T> T getObjectFromJson(DataSnapshot dataSnapshot, String key, Class<T> clazz) {
        String json = dataSnapshot.child(key).getValue(String.class);
        return json != null ? new Gson().fromJson(json, clazz) : null;
    }

    private <T> List<T> getListFromJson(DataSnapshot dataSnapshot, String key, Class<T> clazz) {
        String json = dataSnapshot.child(key).getValue(String.class);
        if (json == null) return new ArrayList<>();
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(json, type);
    }
}
