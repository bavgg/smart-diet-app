package com.jg.dietapp.data;

import static com.jg.dietapp.MainActivity.sharedPrefsHelper;
import static com.jg.dietapp.MainActivity.userInput;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.UserInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAOMeal {
    private SQLiteDatabase db;

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
        values.put("prep_time", meal.getPrepTime());  // ✅ Added Prep Time
        values.put("culture", meal.getCulture());    // ✅ Added Cuisine
        values.put("region", meal.getRegion());
        values.put("servings_grams", meal.getServingsGrams()); // ✅ Added Servings in Grams

        long result = db.insert("meals", null, values);

        if (result == -1) {
            System.out.println("Failed to insert meal");
        } else {
            System.out.println("Meal inserted successfully, ID: " + result);
        }
    }


    public List<Meal> getMealsByDietAndAllergens(String dietType, String foodAllergens) {
        List<Meal> meals = new ArrayList<>();
        List<String> selectionArgs = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM meals WHERE diet_type = ?");

        // Add dietType as the first parameter
        selectionArgs.add(dietType);

        System.out.println("User input: " + userInput);

        // Split the food allergens string into a list
        if (foodAllergens != null && !foodAllergens.isEmpty()) {
            String[] allergensArray = foodAllergens.split(",");
            String[] subArray = Arrays.copyOfRange(allergensArray, 1, allergensArray.length);

            for (String allergen : subArray) {
                query.append(" AND allergens NOT LIKE ?");
                selectionArgs.add("%" + allergen.trim() + "%");
            }
        }

        System.out.println("Selection args: " + selectionArgs);

        // Execute query
        Cursor cursor = db.rawQuery(query.toString(), selectionArgs.toArray(new String[0]));

        while (cursor.moveToNext()) {
            meals.add(new Meal(
                    cursor.getInt(0),  // ✅ ID
                    cursor.getString(1),  // Name
                    cursor.getDouble(2),  // Calories
                    cursor.getInt(3),     // Protein
                    cursor.getInt(4),     // Carbs
                    cursor.getInt(5),     // Fats
                    cursor.getString(6),  // Diet Type
                    cursor.getString(7),  // Allergens
                    cursor.getInt(8),     // Prep Time
                    cursor.getString(9),  // Culture
                    cursor.getString(10), // Region
                    cursor.getInt(11),    // Servings in Grams
                    cursor.getString(12)  // Mealtime
            ));
        }
        cursor.close();

        return meals;
    }

}
