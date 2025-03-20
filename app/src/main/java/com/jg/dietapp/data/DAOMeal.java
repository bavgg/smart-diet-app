package com.jg.dietapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;

import java.util.ArrayList;
import java.util.List;

public class DAOMeal {
    private static final String TAG = "DAOMeal";
    private final SQLiteDatabase db;

    public DAOMeal(DatabaseHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
    }

    public Meal insertMeal(Context context, Meal meal) {
        if (db == null || !db.isOpen()) {
            Log.e(TAG, "Database is not available for writing.");
            Toast.makeText(context, "Database is not available", Toast.LENGTH_SHORT).show();
            return null;
        }

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
        values.put("mealtime", meal.getMealtime());
        values.put("image_name", meal.getImageName());

        Meal insertedMeal = null;

        try {
            long result = db.insert("meals", null, values);
            if (result == -1) {
                Log.e(TAG, "Failed to insert meal");
                Toast.makeText(context, "Failed to insert meal", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Meal inserted successfully, ID: " + result);
                Toast.makeText(context, "Meal inserted successfully!", Toast.LENGTH_SHORT).show();

                // Retrieve the inserted meal from the database
                Cursor cursor = db.rawQuery("SELECT * FROM meals WHERE rowid = ?", new String[]{String.valueOf(result)});
                if (cursor != null && cursor.moveToFirst()) {
                    insertedMeal = new Meal(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("name")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("calories")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("protein")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("carbs")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("fats")),
                            cursor.getString(cursor.getColumnIndexOrThrow("diet_type")),
                            cursor.getString(cursor.getColumnIndexOrThrow("allergens")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("prep_time")),
                            cursor.getString(cursor.getColumnIndexOrThrow("culture")),
                            cursor.getString(cursor.getColumnIndexOrThrow("region")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("servings_grams")),
                            cursor.getString(cursor.getColumnIndexOrThrow("mealtime")),
                            cursor.getString(cursor.getColumnIndexOrThrow("image_name"))
                    );
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error inserting meal: " + e.getMessage());
            Toast.makeText(context, "Error inserting meal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }

        return insertedMeal;
    }

    public List<Meal> getMealsByDietAndAllergens(UserInput userInput) {

        System.out.println("UserInput " + userInput);
        String dietType = userInput.getDietType().toString();

        String foodAllergens = userInput.getFoodAllergens();

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

    public List<Meal> getAllMeals() {
        List<Meal> meals = new ArrayList<>();
        String query = "SELECT * FROM meals";

        try (Cursor cursor = db.rawQuery(query, null)) {
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
                        cursor.getString(12),   // Mealtime
                        cursor.getString(13)   // Image name

                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching all meals", e);
        }

        return meals;
    }

    public List<Meal> getRecentMeals() {
        List<Meal> meals = new ArrayList<>();
        String query = "SELECT * FROM meals ORDER BY created_at DESC LIMIT 10"; // ✅ Fetch last 10 meals based on timestamp

        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor.moveToFirst()) { // ✅ Ensure cursor has data
                do {
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
                            cursor.getString(12),   // Mealtime
                            cursor.getString(13)   // Image name
                    ));
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "No recent meals found."); // ✅ Debugging log
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching recent meals", e);
        }

        return meals;
    }


}
