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
                "cuisine TEXT)"
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
            db.execSQL("INSERT INTO meals (name, calories, protein, carbs, fats, diet_type, allergens, prep_time, cuisine) VALUES " +
                    "('Sushi (Salmon Roll)', 300, 25, 45, 8, 'LOW_FAT', 'Seafood', 20, 'Japanese'), " +
                    "('Tacos al Pastor', 450, 35, 40, 15, 'PALEO', 'None', 25, 'Mexican'), " +
                    "('Chicken Tikka Masala', 500, 40, 50, 20, 'HIGH_PROTEIN', 'Dairy', 40, 'Indian'), " +
                    "('Ratatouille', 250, 10, 35, 5, 'VEGETARIAN', 'None', 30, 'French'), " +
                    "('Falafel Wrap', 400, 20, 50, 15, 'VEGETARIAN', 'Gluten', 20, 'Middle Eastern'), " +
                    "('Beef Pho', 450, 35, 60, 10, 'LOW_FAT', 'None', 40, 'Vietnamese'), " +
                    "('Borscht', 300, 10, 40, 8, 'VEGETARIAN', 'None', 35, 'Russian'), " +
                    "('Kimchi Fried Rice', 500, 20, 70, 12, 'VEGETARIAN', 'None', 25, 'Korean'), " +
                    "('Paella', 480, 30, 60, 12, 'PALEO', 'Seafood', 45, 'Spanish'), " +
                    "('Shakshuka', 350, 20, 40, 10, 'VEGETARIAN', 'Eggs', 30, 'North African'), " +
                    "('Poutine', 600, 15, 70, 30, 'HIGH_CARB', 'Dairy', 20, 'Canadian'), " +
                    "('Moussaka', 550, 25, 50, 20, 'LOW_CARB', 'Dairy', 50, 'Greek'), " +
                    "('Bulgogi', 500, 35, 55, 12, 'HIGH_PROTEIN', 'None', 30, 'Korean'), " +
                    "('Pad Thai', 450, 30, 65, 10, 'LOW_FAT', 'Peanuts', 35, 'Thai'), " +
                    "('Goulash', 400, 40, 50, 15, 'PALEO', 'None', 45, 'Hungarian'), " +
                    "('Jollof Rice', 500, 25, 80, 12, 'VEGAN', 'None', 40, 'West African'), " +
                    "('Empanadas', 450, 15, 50, 20, 'LOW_CARB', 'Gluten', 35, 'Argentinian'), " +
                    "('Churrasco', 700, 60, 30, 40, 'KETO', 'None', 50, 'Brazilian'), " +
                    "('Shabu-Shabu', 400, 45, 30, 10, 'LOW_FAT', 'None', 30, 'Japanese'), " +
                    "('Bibimbap', 450, 30, 60, 15, 'VEGETARIAN', 'Eggs', 25, 'Korean'), " +
                    "('Feijoada', 600, 50, 55, 25, 'HIGH_PROTEIN', 'None', 50, 'Brazilian'), " +
                    "('Biryani', 550, 35, 80, 15, 'HIGH_CARB', 'Dairy', 60, 'Indian'), " +
                    "('Fajitas', 400, 35, 45, 12, 'PALEO', 'None', 30, 'Mexican'), " +
                    "('Ceviche', 350, 40, 20, 8, 'KETO', 'Seafood', 25, 'Peruvian'), " +
                    "('Kebab', 500, 45, 35, 20, 'PALEO', 'None', 40, 'Turkish'), " +
                    "('Pierogi', 450, 15, 60, 20, 'VEGETARIAN', 'Gluten', 30, 'Polish'), " +
                    "('Dolma', 400, 20, 45, 10, 'VEGAN', 'None', 35, 'Middle Eastern'), " +
                    "('Khachapuri', 600, 25, 60, 30, 'VEGETARIAN', 'Dairy', 40, 'Georgian'), " +
                    "('Bratwurst with Sauerkraut', 500, 40, 30, 25, 'LOW_CARB', 'None', 35, 'German'), " +
                    "('Tamales', 350, 15, 55, 10, 'VEGAN', 'Gluten', 50, 'Mexican'), " +
                    "('Laksa', 550, 30, 70, 15, 'LOW_FAT', 'Seafood', 45, 'Malaysian'), " +
                    "('Arepas', 400, 10, 45, 12, 'VEGETARIAN', 'None', 30, 'Venezuelan'), " +
                    "('Chana Masala', 450, 25, 65, 10, 'VEGAN', 'None', 40, 'Indian'), " +
                    "('Katsu Curry', 600, 40, 75, 20, 'HIGH_CARB', 'Gluten', 35, 'Japanese'), " +
                    "('Clam Chowder', 500, 30, 40, 15, 'LOW_CARB', 'Seafood', 50, 'American'), " +
                    "('Baba Ganoush', 300, 10, 30, 12, 'VEGAN', 'None', 25, 'Middle Eastern'), " +
                    "('Sauerbraten', 550, 50, 35, 18, 'LOW_CARB', 'None', 60, 'German'), " +
                    "('Tteokbokki', 450, 10, 80, 8, 'VEGAN', 'None', 25, 'Korean'), " +
                    "('Stuffed Peppers', 400, 30, 40, 12, 'PALEO', 'None', 35, 'Mediterranean'), " +
                    "('Pav Bhaji', 550, 20, 90, 12, 'VEGAN', 'Gluten', 40, 'Indian'), " +
                    "('Miso Soup', 200, 12, 25, 6, 'LOW_FAT', 'Soy', 15, 'Japanese'), " +
                    "('Chili Con Carne', 600, 50, 40, 20, 'HIGH_PROTEIN', 'None', 50, 'Tex-Mex') ");

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
