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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseUtils {

    private final FirebaseDataPrefs prefs;
    private final DatabaseReference databaseRef;
    private static final String TAG = "FirebaseUtils";

    public FirebaseUtils(Context context) {
        this.prefs = new FirebaseDataPrefs(context);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            this.databaseRef = FirebaseDatabase
                    .getInstance("https://smart-diet-app-96aee-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("users")
                    .child(userId)
                    .child("preferences");

            Log.d(TAG, "Database Reference URL: " + databaseRef.toString());
        } else {
            this.databaseRef = null;
            Log.w(TAG, "No authenticated user. Skipping Firebase initialization.");
        }
    }

    // Sync local data to Firebase
    public void syncPreferencesToFirebase() {
        if (databaseRef == null) return;

        Map<String, Object> preferences = new HashMap<>();
        preferences.put("kcal", prefs.getKcal());
        preferences.put("protein", prefs.getProtein());
        preferences.put("carbs", prefs.getCarbs());
        preferences.put("fat", prefs.getFat());
        preferences.put("user_inputs", new Gson().toJson(prefs.getUser()));
        preferences.put("selected_meals_id", new Gson().toJson(prefs.getSelectedMealsID()));

        databaseRef.updateChildren(preferences)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Preferences uploaded successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to sync preferences", e));
    }

    public void syncGeneratedData() {
        if (databaseRef == null) return;

        Map<String, Object> preferences = new HashMap<>();
        preferences.put("base_calories", prefs.getBaseCalories());
        preferences.put("breakfast_meals", new Gson().toJson(prefs.getBreakfastMeals()));
        preferences.put("lunch_meals", new Gson().toJson(prefs.getLunchMeals()));
        preferences.put("dinner_meals", new Gson().toJson(prefs.getDinnerMeals()));

        databaseRef.updateChildren(preferences)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Generated data uploaded successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to sync generated data", e));
    }

    public void syncUserInput() {
        if (databaseRef == null) return;

        Map<String, Object> preferences = new HashMap<>();
        preferences.put("user_inputs", new Gson().toJson(prefs.getUser()));

        databaseRef.updateChildren(preferences)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User input uploaded successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to sync user input", e));
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

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

                // Deserialize JSON strings into objects safely
                prefs.setUser(getObjectFromJson(dataSnapshot, "user_inputs", UserInput.class));
                prefs.setSelectedMealsID(getListFromJson(dataSnapshot, "selected_meals_id", Integer.class));
                prefs.setBreakfastMeals(getListFromJson(dataSnapshot, "breakfast_meals", Meal.class));
                prefs.setLunchMeals(getListFromJson(dataSnapshot, "lunch_meals", Meal.class));
                prefs.setDinnerMeals(getListFromJson(dataSnapshot, "dinner_meals", Meal.class));

                Log.d(TAG, "Preferences loaded from Firebase and saved locally!");
            } else {
                Log.e(TAG, "Failed to load preferences from Firebase.");
            }
        });
    }

    // Load data from Firebase and update SharedPreferences
    public void loadPreferencesFromFirebase(OnDataLoadedListener listener) {
        if (databaseRef == null) return;

        databaseRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot dataSnapshot = task.getResult();

                prefs.setKcal(getIntValue(dataSnapshot, "kcal"));
                prefs.setProtein(getIntValue(dataSnapshot, "protein"));
                prefs.setCarbs(getIntValue(dataSnapshot, "carbs"));
                prefs.setFat(getIntValue(dataSnapshot, "fat"));
                prefs.setBaseCalories(getIntValue(dataSnapshot, "base_calories"));

                prefs.setUser(getObjectFromJson(dataSnapshot, "user_inputs", UserInput.class));
                prefs.setSelectedMealsID(getListFromJson(dataSnapshot, "selected_meals_id", Integer.class));
                prefs.setBreakfastMeals(getListFromJson(dataSnapshot, "breakfast_meals", Meal.class));
                prefs.setLunchMeals(getListFromJson(dataSnapshot, "lunch_meals", Meal.class));
                prefs.setDinnerMeals(getListFromJson(dataSnapshot, "dinner_meals", Meal.class));

                Log.d(TAG, "Preferences loaded from Firebase and saved locally!");

                // Notify that data has been loaded
                if (listener != null) {
                    listener.onDataLoaded();
                }
            } else {
                Log.e(TAG, "Failed to load preferences from Firebase.");
            }
        });
    }

    public void clearUserData() {
        if (databaseRef == null) return;

        databaseRef.removeValue()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User data cleared from Firebase"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to clear user data", e));
    }



    private int getIntValue(DataSnapshot dataSnapshot, String key) {
        Integer value = dataSnapshot.child(key).getValue(Integer.class);
        return value != null ? value : 0;
    }

    private <T> T getObjectFromJson(DataSnapshot dataSnapshot, String key, Class<T> clazz) {
        String json = dataSnapshot.child(key).getValue(String.class);
        if (json == null) return null;
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON for " + key, e);
            return null;
        }
    }

    private <T> List<T> getListFromJson(DataSnapshot dataSnapshot, String key, Class<T> clazz) {
        String json = dataSnapshot.child(key).getValue(String.class);
        if (json == null) return new ArrayList<>();
        try {
            Type type = TypeToken.getParameterized(List.class, clazz).getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON list for " + key, e);
            return new ArrayList<>();
        }
    }
}
