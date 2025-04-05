package com.jg.dietapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jg.dietapp.enums.EnumActivityLevel;
import com.jg.dietapp.enums.EnumDietType;
import com.jg.dietapp.enums.EnumGoal;
import com.jg.dietapp.enums.EnumSex;
import com.jg.dietapp.models.DietaryTrack;
import com.jg.dietapp.models.Exercise;

import com.jg.dietapp.models.Meal;

import com.jg.dietapp.models.UserInput;

import java.util.ArrayList;
import java.util.List;

public class DAOPrefs {
    private static final String TAG = "DAOPrefs";
    private final SQLiteDatabase db;

    public DAOPrefs(DatabaseHelper dbHelper) {
        this.db = dbHelper.getWritableDatabase();
    }

    // NOTE: Clear all tables
    public void clearAllTables() {
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM selected_meal_ids");
            db.execSQL("DELETE FROM user_input");
            db.execSQL("DELETE FROM base_calories");
            db.execSQL("DELETE FROM current_dietary_track");
            db.execSQL("DELETE FROM generated_meals");
            db.execSQL("DELETE FROM generated_exercises");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error clearing all tables", e);
        } finally {
            db.endTransaction();
        }
    }


    // NOTE: Selected meal IDs

    public boolean insertSelectedMealIDs(int userId, List<Integer> selectedMealIDs) {
        String query = "INSERT INTO selected_meal_ids (user_id, meal_id) VALUES (?, ?)";
        try {
            // Loop through the list of meal IDs and insert each into the database
            for (Integer mealId : selectedMealIDs) {
                db.execSQL(query, new Object[]{
                        userId,
                        mealId
                });
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting selected meals", e);
            return false;
        }
    }

    public List<Integer> getSelectedMealIDsByUserIDs(int userId) {
        List<Integer> selectedMealIDs = new ArrayList<>();
        String query = "SELECT meal_id FROM selected_meal_ids WHERE user_id = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor.moveToFirst()) {
                do {
                    selectedMealIDs.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving selected meal IDs", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return selectedMealIDs;
    }



    // NOTE: User Input

    public UserInput getUserInputByUserId(int userId) {
        String query = "SELECT * FROM user_input WHERE user_id = ?";
        UserInput userInput = null;

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
            if (cursor.moveToFirst()) { // Ensure only one record is processed
                userInput = new UserInput(
                        EnumGoal.valueOf(cursor.getString(1)),  // Goal
                        EnumSex.valueOf(cursor.getString(2)),  // Sex
                        cursor.getInt(3),  // Age
                        cursor.getInt(4),  // Height
                        cursor.getInt(5),  // Weight
                        EnumActivityLevel.valueOf(cursor.getString(6)),  // Activity Level
                        EnumDietType.valueOf(cursor.getString(7)),  // Diet Type
                        cursor.getString(8),  // Food Allergens
                        cursor.getInt(9) == 1 // User Submitted (Convert INT to boolean)
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching user input by user ID", e);
        }

        return userInput; // Returns null if no user input is found
    }

    public boolean insertUserInput(int userId, UserInput userInput) {
        // First, delete any existing entry for the user to maintain uniqueness
        db.execSQL("DELETE FROM user_input WHERE user_id = ?", new Object[]{userId});

        String query = "INSERT INTO user_input (user_id, goal, sex, age, height, weight, activity_level, diet_type, food_allergens, user_submitted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            db.execSQL(query, new Object[]{
                    userId,
                    userInput.getGoal().name(),
                    userInput.getSex().name(),
                    userInput.getAge(),
                    userInput.getHeight(),
                    userInput.getWeight(),
                    userInput.getActivityLevel().name(),
                    userInput.getDietType().name(),
                    userInput.getFoodAllergens(),
                    userInput.getUserSubmitted() ? 1 : 0
            });
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting user input", e);
            return false;
        }
    }


    // NOTE: Dietary Track

    public DietaryTrack getCurrentDietaryTrackByUserId(int userId) {
        String query = "SELECT * FROM current_dietary_track WHERE user_id = ?";
        DietaryTrack nutritionData = null;

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
            if (cursor.moveToFirst()) { // Fetch only one record
                nutritionData = new DietaryTrack(
                        cursor.getInt(2),  // Calories
                        cursor.getInt(3),  // Protein
                        cursor.getInt(4),  // Carbs
                        cursor.getInt(5)  // Fat
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching nutrition data by user ID", e);
        }

        return nutritionData; // Returns null if no data is found
    }

    public boolean insertDietaryTrack(int userId, DietaryTrack dietaryTrack) {
        String query = "INSERT INTO current_dietary_track (user_id, calories, protein, carbs, fat) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            db.execSQL(query, new Object[]{
                    userId,
                    dietaryTrack.getCalories(),
                    dietaryTrack.getProtein(),
                    dietaryTrack.getCarbs(),
                    dietaryTrack.getFat()
            });
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting nutrition data", e);
            return false;
        }
    }


    // NOTE: Base Calories

    public int getBaseCaloriesByUserID(int userID) {
        int baseCalories = 0;
        String query = "SELECT base_calories FROM base_calories WHERE user_id = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userID)})) {
            if (cursor.moveToFirst()) {
                baseCalories = cursor.getInt(2);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching base calories for user ID: " + userID, e);
        }

        return baseCalories; // Returns 0 if no data is found
    }

    public boolean insertBaseCaloriesByUserID(int userID, int baseCalories) {
        String query = "INSERT INTO base_calories (user_id, base_calories) VALUES (?, ?)";

        try {
            db.execSQL(query, new Object[]{userID, baseCalories});
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting base calories for user ID: " + userID, e);
            return false;
        }
    }



    // NOTE: Generated Meals
    public boolean insertGeneratedMealsByUserID(int userID, List<Meal> meals) {
        String query = "INSERT INTO generated_meals (user_id, meal_name, culture, region, diet_type, mealtime, allergens, " +
                "image_name, calories, carbs, fats, protein, prep_time, servings_in_grams) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Loop through each meal in the list and insert it
            for (Meal meal : meals) {
                db.execSQL(query, new Object[]{
                        userID,
                        meal.getName(),
                        meal.getCulture(),
                        meal.getRegion(),
                        meal.getDietType(),
                        meal.getMealtime(),
                        meal.getAllergens(),
                        meal.getImageName(),
                        meal.getCalories(),
                        meal.getCarbs(),
                        meal.getFats(),
                        meal.getProtein(),
                        meal.getPrepTime(),
                        meal.getServingsGrams()
                });
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting lunch meal", e);
            return false;
        }
    }
    public List<Meal> getGeneratedMealsByUserId(int userId, String mealTime) {
        List<Meal> breakfastMeals = new ArrayList<>();
        String query = "SELECT * FROM generated_meals WHERE user_id = ? AND mealtime = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), mealTime})) {
            while (cursor.moveToNext()) {
                breakfastMeals.add(new Meal(
                        cursor.getString(2),  // Meal Name
                        cursor.getString(3),  // Culture
                        cursor.getString(4),  // Region
                        cursor.getString(5),  // Diet Type
                        cursor.getString(6),  // Mealtime
                        cursor.getString(7),  // Allergens
                        cursor.getDouble(9), // Calories
                        cursor.getInt(10),   // Carbs
                        cursor.getInt(11),   // Fats
                        cursor.getInt(12),   // Protein
                        cursor.getInt(13),   // Prep Time
                        cursor.getInt(14)    // Servings in Grams
                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching breakfast meals", e);
        }
        return breakfastMeals;
    }


    // NOTE: Generated Exercise
    public boolean insertGeneratedExercise(int userID, List<Exercise> exercises) {
        try {
            db.beginTransaction();

            String query = "INSERT INTO exercises (user_id, exercise_name, duration, calories_burned) VALUES (?, ?, ?, ?)";

            for (Exercise exercise : exercises) {
                db.execSQL(query, new Object[]{
                        userID,
                        exercise.getName(),
                        exercise.getDuration(),
                        exercise.getCaloriesBurned(),
                });
            }

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting exercise", e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public List<Exercise> getGeneratedExercisesByUserId(int userId) {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM generated_exercises WHERE user_id = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
            while (cursor.moveToNext()) {
                exercises.add(new Exercise(
                        cursor.getString(2),  // Exercise Name
                        cursor.getInt(3),  // Duration
                        cursor.getInt(4)  // Calories Burned
                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching exercises by user ID", e);
        }
        return exercises;
    }
}
