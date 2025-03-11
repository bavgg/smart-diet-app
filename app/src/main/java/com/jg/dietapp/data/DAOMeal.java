package com.jg.dietapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jg.dietapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class DAOMeal {
    private static final String TAG = "DAOMeal";
    private final SQLiteDatabase db;

    public DAOMeal(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insertMeal(Meal meal) {
        ContentValues values = new ContentValues();
        values.put("name", meal.getName());
        values.put("calories", meal.getCalories());
        values.put("protein", meal.getProtein());
        values.put("carbs", meal.getCarbs());
        values.put("fats", meal.getFats());
        values.put("diet_type", meal.getDietType());
        values.put("allergens", meal.getAllergens());
        values.put("prep_time", meal.getPrepTime());
        values.put("culture", meal.getCulture());
        values.put("region", meal.getRegion());
        values.put("servings_grams", meal.getServingsGrams());

        long result = db.insert("meals", null, values);

        if (result == -1) {
            Log.e(TAG, "Failed to insert meal");
        } else {
            Log.d(TAG, "Meal inserted successfully, ID: " + result);
        }
    }

    public List<Meal> getMealsByDietAndAllergens(String dietType, String foodAllergens) {
        List<Meal> meals = new ArrayList<>();
        List<String> selectionArgs = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM meals WHERE diet_type = ?");

        selectionArgs.add(dietType);

        String[] allergensArray = foodAllergens.split(",");
        for (String allergen : allergensArray) {
            if (!allergen.trim().isEmpty()) {  // ✅ Ignore empty strings
                query.append(" AND allergens NOT LIKE ?");
                selectionArgs.add("%" + allergen.trim() + "%");
            }
        }


        Log.d(TAG, "Query: " + query);
        Log.d(TAG, "Selection args: " + selectionArgs);

        try (Cursor cursor = db.rawQuery(query.toString(), selectionArgs.toArray(new String[0]))) {
            while (cursor.moveToNext()) {
                meals.add(new Meal(
                        cursor.getInt(0),  // ID
                        cursor.getString(1),  // Name
                        cursor.getDouble(2),  // Calories
                        cursor.getInt(3),  // Protein
                        cursor.getInt(4),  // Carbs
                        cursor.getInt(5),  // Fats
                        cursor.getString(6),  // Diet Type
                        cursor.getString(7),  // Allergens
                        cursor.getInt(8),  // Prep Time
                        cursor.getString(9),  // Culture
                        cursor.getString(10),  // Region
                        cursor.getInt(11),  // Servings in Grams
                        cursor.getString(12)   // Mealtime
                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching meals", e);
        }

        return meals;
    }
}
