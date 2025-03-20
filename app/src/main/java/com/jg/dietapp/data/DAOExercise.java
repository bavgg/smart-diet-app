package com.jg.dietapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.jg.dietapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DAOExercise {
    private static final String TAG = "DAOExercise";
    private final SQLiteDatabase db;

    public DAOExercise(DatabaseHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
    }

    public Exercise insertExercise(Context context, Exercise exercise) {
        if (db == null || !db.isOpen()) {
            Log.e(TAG, "Database is not available for writing.");
            Toast.makeText(context, "Database is not available", Toast.LENGTH_SHORT).show();
            return null;
        }

        ContentValues values = new ContentValues();
        values.put("name", exercise.getName());
        values.put("activity_level", exercise.getActivityLevel());
        values.put("duration", exercise.getDuration());
        values.put("calories_burned", exercise.getCaloriesBurned());
        values.put("image_name", exercise.getImageName());

        Exercise insertedExercise = null;

        try {
            long result = db.insert("exercises", null, values);
            if (result == -1) {
                Log.e(TAG, "Failed to insert exercise");
                Toast.makeText(context, "Failed to insert exercise", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Exercise inserted successfully, ID: " + result);
                Toast.makeText(context, "Exercise inserted successfully!", Toast.LENGTH_SHORT).show();

                Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE rowid = ?", new String[]{String.valueOf(result)});
                if (cursor != null && cursor.moveToFirst()) {
                    insertedExercise = new Exercise(
                            cursor.getString(cursor.getColumnIndexOrThrow("name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("type")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("duration")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("calories_burned"))
                    );
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error inserting exercise: " + e.getMessage());
            Toast.makeText(context, "Error inserting exercise: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }

        return insertedExercise;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM exercises";

        try (Cursor cursor = db.rawQuery(query, null)) {
            while (cursor.moveToNext()) {
                exercises.add(new Exercise(
                        cursor.getString(0),  // Name
                        cursor.getString(1),  // Activity Level
                        cursor.getInt(2),  // Duration
                        cursor.getInt(3)   // Calories Burned
                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching exercises", e);
        }

        return exercises;
    }

    public Exercise getExerciseByName(String name) {
        Exercise exercise = null;
        String query = "SELECT * FROM exercises WHERE name = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{name})) {
            if (cursor.moveToFirst()) {
                exercise = new Exercise(
                        cursor.getString(0),  // Name
                        cursor.getString(1),  // Type
                        cursor.getInt(2),  // Duration
                        cursor.getInt(3)   // Calories Burned
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching exercise by name", e);
        }

        return exercise;
    }

    public List<Exercise> getExercisesByActivityLevel(String activityLevel) {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM exercises WHERE activity_level = ? LIMIT 3";

        try (Cursor cursor = db.rawQuery(query, new String[]{activityLevel})) {
            while (cursor.moveToNext()) {
                exercises.add(new Exercise(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),  // Name
                        cursor.getString(cursor.getColumnIndexOrThrow("activity_level")),  // Activity Level
                        cursor.getInt(cursor.getColumnIndexOrThrow("duration")),  // Duration
                        cursor.getInt(cursor.getColumnIndexOrThrow("calories_burned"))   // Calories Burned
                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching exercises by activity level", e);
        }

        return exercises;
    }


    public void deleteExercise(String name) {
        try {
            db.delete("exercises", "name = ?", new String[]{name});
            Log.d(TAG, "Exercise deleted successfully: " + name);
        } catch (Exception e) {
            Log.e(TAG, "Error deleting exercise", e);
        }
    }
}