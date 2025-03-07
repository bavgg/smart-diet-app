package com.jg.dietapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diet-app.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE meals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "calories INTEGER, " +
                "protein INTEGER, " +
                "carbs INTEGER, " +
                "fats INTEGER, " +
                "diet_type TEXT, " +
                "allergens TEXT, " +
                "prep_time INTEGER, " +
                "culture TEXT, " +
                "region TEXT, " +
                "servings_grams INTEGER)"
        );

        db.execSQL("CREATE TABLE exercises (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "category TEXT, " +
                "duration INTEGER, " +
                "calories_burned INTEGER)");

        seedDatabase(db); // ✅ Seeding the database
    }

    private void seedDatabase(SQLiteDatabase db) {
        db.beginTransaction(); // ✅ Begin transaction for performance
        try {
            // Manually inserting 50 meals with different cuisines
            db.execSQL("INSERT INTO meals (name, calories, protein, carbs, fats, diet_type, allergens, prep_time, culture, region, servings_grams)  VALUES " +
//                  // Tagalog (Luzon)
                    "('Adobo', 250, 30, 10, 12, 'Omnivore', 'Soy', 45, 'Tagalog', 'Luzon', 150), " +
                    "('Sinigang na Baboy', 200, 25, 15, 8, 'Omnivore', 'None', 50, 'Tagalog', 'Luzon', 200), " +
                    "('Bulalo', 400, 35, 5, 25, 'Omnivore', 'None', 120, 'Tagalog', 'Luzon', 300), " +
                    "('Kaldereta', 450, 40, 20, 22, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 250), " +
                    "('Pancit Canton', 300, 12, 45, 10, 'Omnivore', 'Gluten', 30, 'Tagalog', 'Luzon', 180), " +

                    // Ilocano (Luzon)
                    "('Pinakbet', 180, 6, 25, 5, 'Vegetarian', 'None', 40, 'Ilocano', 'Luzon', 200), " +
                    "('Bagnet', 500, 45, 10, 30, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 150), " +
                    "('Dinengdeng', 150, 10, 20, 4, 'Vegetarian', 'None', 30, 'Ilocano', 'Luzon', 180), " +
                    "('Poqui-Poqui', 220, 8, 20, 10, 'Vegetarian', 'Egg', 30, 'Ilocano', 'Luzon', 180), " +
                    "('Igado', 350, 40, 10, 20, 'Omnivore', 'None', 50, 'Ilocano', 'Luzon', 200), " +

                    // Kapampangan (Luzon)
                    "('Sisig', 600, 50, 5, 40, 'Omnivore', 'None', 45, 'Kapampangan', 'Luzon', 180)," +
                    "('Bringhe', 400, 15, 50, 12, 'Omnivore', 'Gluten', 60, 'Kapampangan', 'Luzon', 250)," +
                    "('Tocino', 300, 25, 30, 15, 'Omnivore', 'None', 24, 'Kapampangan', 'Luzon', 120)," +
                    "('Morcon', 450, 40, 10, 22, 'Omnivore', 'Egg', 90, 'Kapampangan', 'Luzon', 220)," +
                    "('Burong Isda', 200, 15, 20, 5, 'Omnivore', 'Fish', 48, 'Kapampangan', 'Luzon', 180)," +

                    // Bicolano (Luzon)
                    "('Bicol Express', 380, 30, 20, 25, 'Omnivore', 'Coconut', 60, 'Bicolano', 'Luzon', 200)," +
                    "('Laing', 180, 6, 15, 10, 'Vegetarian', 'Coconut', 40, 'Bicolano', 'Luzon', 120),"  +
                    "('Kinunot', 250, 35, 5, 12, 'Pescatarian', 'Fish', 45, 'Bicolano', 'Luzon', 180)," +
                    "('Sinantolan', 200, 10, 25, 8, 'Vegetarian', 'Coconut', 30, 'Bicolano', 'Luzon', 150)," +
                    "('Tilmok', 280, 20, 10, 15, 'Pescatarian', 'Shellfish', 50, 'Bicolano', 'Luzon', 220)," +

                    // Cebuano (Visayas)
                    "('Lechon Cebu', 600, 50, 5, 40, 'Omnivore', 'None', 120, 'Cebuano', 'Visayas', 300)," +
                    "('Bam-i', 350, 20, 50, 12, 'Omnivore', 'Gluten', 45, 'Cebuano', 'Visayas', 200)," +
                    "('Puso Rice', 150, 3, 30, 1, 'Vegetarian', 'None', 15, 'Cebuano', 'Visayas', 180)," +
                    "('Ngohiong', 250, 10, 30, 10, 'Vegetarian', 'None', 40, 'Cebuano', 'Visayas', 180)," +
                    "('Kinilaw', 200, 30, 5, 8, 'Pescatarian', 'Fish', 30, 'Cebuano', 'Visayas', 150), " +

                    // Muslim-Filipino (Mindanao)
                    "('Beef Rendang', 500, 40, 20, 25, 'Halal', 'Coconut', 90, 'Muslim-Filipino', 'Mindanao', 250)," +
                    "('Tiyula Itum', 400, 35, 10, 20, 'Halal', 'None', 90, 'Muslim-Filipino', 'Mindanao', 300)," +
                    "('Pyanggang Manok', 380, 30, 15, 22, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250)," +
                    "('Pastil', 300, 25, 40, 10, 'Halal', 'None', 60, 'Muslim-Filipino', 'Mindanao', 200)," +
                    "('Lokot-Lokot', 150, 2, 30, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 100)," +

                    // Indigenous Cordilleran (Luzon)
                    "('Pinikpikan', 350, 40, 5, 20, 'Omnivore', 'None', 90, 'Cordilleran', 'Luzon', 250)," +
                    "('Etag', 450, 50, 5, 30, 'Omnivore', 'None', 48, 'Cordilleran', 'Luzon', 200)," +
                    "('Inasin', 300, 35, 5, 20, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220)," +
                    "('Linapay', 280, 25, 15, 15, 'Omnivore', 'None', 75, 'Cordilleran', 'Luzon', 200)," +
                    "('Watwat', 320, 30, 5, 18, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 200)," +

                    // Chavacano (Zamboanga)
                    "('Satti', 400, 35, 10, 20, 'Omnivore', 'None', 40, 'Chavacano', 'Mindanao', 200)," +
                    "('Tamal', 250, 10, 30, 8, 'Omnivore', 'Gluten', 60, 'Chavacano', 'Mindanao', 200)," +
                    "('Curacha with Alavar Sauce', 500, 40, 10, 25, 'Pescatarian', 'Shellfish', 75, 'Chavacano', 'Mindanao', 250)," +
                    "('Kiampong', 300, 8, 50, 5, 'Vegetarian', 'None', 45, 'Chavacano', 'Mindanao', 200)," +
                    "('Maja Blanca', 200, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Chavacano', 'Mindanao', 150)");

            // Manually inserting exercises
            db.execSQL("INSERT INTO exercises (name, category, duration, calories_burned) VALUES " +
                    "('Running', 'CARDIO', 30, 300), " +
                    "('Push-ups', 'STRENGTH', 15, 150), " +
                    "('Jump Rope', 'CARDIO', 20, 250), " +
                    "('Weight Lifting', 'STRENGTH', 40, 400), " +
                    "('Cycling', 'CARDIO', 45, 500), " +
                    "('Pull-ups', 'STRENGTH', 10, 100), " +
                    "('Swimming', 'CARDIO', 60, 600), " +
                    "('Deadlifts', 'STRENGTH', 35, 350)");

            db.setTransactionSuccessful(); // ✅ Commit the batch
        } finally {
            db.endTransaction(); // ✅ End transaction
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meals");
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }
}
