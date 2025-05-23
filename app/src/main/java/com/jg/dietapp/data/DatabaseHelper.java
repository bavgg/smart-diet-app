package com.jg.dietapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diet-app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_COL_EMAIL = "email";
    private static final String USER_COL_PASSWORD = "password";
    private static final String USER_TABLE_NAME = "users";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("CREATE TABLE users (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "email TEXT UNIQUE, " +
//                "password TEXT, " +
//                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)"
//        );


        // NOTE: selected_meal_ids
        db.execSQL("CREATE TABLE selected_meal_ids (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +  // Reference to the user who selected the meal
                "meal_id INTEGER, " +  // Reference to the selected meal
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +  // Timestamp for when the entry was created
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE, " +  // Link to users table
                "FOREIGN KEY(meal_id) REFERENCES meals(id) ON DELETE CASCADE)"  // Link to meals table
        );

        // NOTE: user_input
        db.execSQL("CREATE TABLE user_input (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "goal TEXT, " +
                "sex TEXT, " +
                "age INTEGER, " +
                "height INTEGER, " +
                "weight INTEGER, " +
                "activity_level TEXT, " +
                "diet_type TEXT, " +
                "food_allergens TEXT, " +
                "user_submitted INTEGER, " +
                "user_id INTEGER UNIQUE, " +  // Ensures one entry per user
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)"
        );

        // NOTE: base_calories
        db.execSQL("CREATE TABLE base_calories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "base_calories INTEGER, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)"
        );

        // NOTE: current_dietary_track
        db.execSQL("CREATE TABLE current_dietary_track (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "calories INTEGER, " +
                "protein INTEGER, " +
                "carbs INTEGER, " +
                "fat INTEGER, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)"
        );

        // NOTE: generated_meals
        db.execSQL("CREATE TABLE generated_meals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "meal_name TEXT, " +
                "calories INTEGER, " +
                "protein INTEGER, " +
                "carbs INTEGER, " +
                "fat INTEGER, " +
                "mealtime TEXT, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)"
        );

        // NOTE: generated_exercises
        db.execSQL("CREATE TABLE generated_exercises (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "exercise_name TEXT, " +
                "duration INTEGER, " +  // in minutes
                "calories_burned INTEGER, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)"
        );

        // NOTE: ******************************************************************************************************************************************************************************************************************

        // NOTE: users
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );

        // NOTE: meals
        db.execSQL("CREATE TABLE meals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT UNIQUE, " +
                "calories INTEGER, " +
                "protein INTEGER, " +
                "carbs INTEGER, " +
                "fats INTEGER, " +
                "diet_type TEXT, " +
                "allergens TEXT, " +
                "prep_time INTEGER, " +
                "culture TEXT, " +
                "region TEXT, " +
                "servings_grams INTEGER, " +
                "mealtime TEXT," +
                "image_name TEXT, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );

        // NOTE: exercises
        db.execSQL("CREATE TABLE exercises (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT UNIQUE, " +
                "activity_level TEXT CHECK(activity_level IN ('SEDENTARY', 'LIGHT_ACTIVITY', 'MODERATE_ACTIVITY', 'HEAVY_ACTIVITY', 'EXCESSIVE_ACTIVITY')), " +
                "duration INTEGER, " +
                "calories_burned INTEGER, " +
                "image_name TEXT, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );

        seedMealsTable(db); // ✅ Seeding the database
        seedExercisesTable(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meals");
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }

//    public boolean registerUser(String username, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("email", username);
//        values.put("password", password);
//
//        long result = db.insert(USER_TABLE_NAME, null, values);
//        return result != -1;
//    }
//
//    public boolean loginUser(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
//                USER_COL_EMAIL + "=? AND " + USER_COL_PASSWORD + "=?", new String[]{username, password});
//
//        boolean isValid = cursor.getCount() > 0;
//        cursor.close();
//        return isValid;
//    }

    private void seedExercisesTable(SQLiteDatabase db) {
        db.execSQL("INSERT INTO exercises (name, activity_level, duration, calories_burned, image_name) VALUES " +
                // 🚶‍♂️ SEDENTARY (Minimal movement)
                "('Sitting', 'SEDENTARY', 60, 20, 'Sitting.png'), " +
                "('Reading', 'SEDENTARY', 45, 30, 'Reading.png'), " +
                "('Watching TV', 'SEDENTARY', 60, 25, 'Watching TV.png'), " +

                // 🚶 LIGHT_ACTIVITY (Low effort movements)
                "('Walking (slow)', 'LIGHT_ACTIVITY', 30, 100, 'WalkingSlow.png'), " +
                "('Stretching', 'LIGHT_ACTIVITY', 20, 80, 'Stretching.png'), " +
                "('Yoga', 'LIGHT_ACTIVITY', 30, 120, 'Yoga.png'), " +
                "('Leisure Cycling', 'LIGHT_ACTIVITY', 30, 150, 'Leisure Cycling.png'), " +

                // 🏃 MODERATE_ACTIVITY (Steady movement, moderate exertion)
                "('Brisk Walking', 'MODERATE_ACTIVITY', 30, 200, 'Brisk Walking.png'), " +
                "('Dancing', 'MODERATE_ACTIVITY', 40, 250, 'Dancing.png'), " +
                "('Swimming (casual)', 'MODERATE_ACTIVITY', 30, 300, 'Swimming (casual).png'), " +
                "('Rowing (moderate)', 'MODERATE_ACTIVITY', 30, 350, 'Rowing (moderate).png'), " +
                "('Weight Lifting (light)', 'MODERATE_ACTIVITY', 30, 200, 'Weight Lifting (light).png'), " +

                // 🏋️ HEAVY_ACTIVITY (High effort, full-body engagement)
                "('Running', 'HEAVY_ACTIVITY', 30, 400, 'Running.png'), " +
                "('Jump Rope', 'HEAVY_ACTIVITY', 20, 350, 'Jump Rope.png'), " +
                "('Squats', 'HEAVY_ACTIVITY', 20, 250, 'Squats.png'), " +
                "('Push-ups', 'HEAVY_ACTIVITY', 15, 200, 'Pushups.png'), " +
                "('Deadlifts', 'HEAVY_ACTIVITY', 30, 500, 'Deadlifts.png'), " +
                "('Swimming (intense)', 'HEAVY_ACTIVITY', 40, 450, 'Swimming (intense).png'), " +

                // 🏋️‍♂️💪 EXCESSIVE_ACTIVITY (Extreme endurance/intensity)
                "('Marathon Running', 'EXCESSIVE_ACTIVITY', 120, 1500, 'Marathon Running.png'), " +
                "('Triathlon', 'EXCESSIVE_ACTIVITY', 180, 2000, 'Triathlon.png'), " +
                "('Hiit Workout', 'EXCESSIVE_ACTIVITY', 45, 600, 'Hiit Workout.png'), " +
                "('CrossFit', 'EXCESSIVE_ACTIVITY', 60, 700, 'Crossfit.png'), " +
                "('Ironman Competition', 'EXCESSIVE_ACTIVITY', 240, 3000, 'Ironman Competition.png')"
        );
    }

    private void seedIngredientsTable(SQLiteDatabase db) {
        db.beginTransaction(); // Begin transaction for performance
        try {
            // Insert initial ingredients data
            db.execSQL("INSERT OR IGNORE INTO ingredients (name, calories_per_100g, protein_per_100g, carbs_per_100g, fats_per_100g, allergens, category) VALUES " +
                    "('Chicken Breast', 165, 31, 0, 3.6, 'None', 'Meat'), " +
                    "('Brown Rice', 112, 2.6, 23, 0.9, 'None', 'Grain'), " +
                    "('Broccoli', 34, 2.8, 6.6, 0.4, 'None', 'Vegetable'), " +
                    "('Salmon', 208, 20, 0, 13, 'Fish', 'Seafood'), " +
                    "('Egg', 155, 13, 1.1, 11, 'Egg', 'Dairy/Egg'), " +
                    "('Almonds', 579, 21, 22, 50, 'Nuts', 'Nuts/Seeds'), " +
                    "('Quinoa', 120, 4.4, 21, 1.9, 'None', 'Grain'), " +
                    "('Spinach', 23, 2.9, 3.6, 0.4, 'None', 'Vegetable'), " +
                    "('Sweet Potato', 86, 1.6, 20, 0.1, 'None', 'Vegetable'), " +
                    "('Avocado', 160, 2, 9, 15, 'None', 'Fruit'), " +
                    "('Olive Oil', 884, 0, 0, 100, 'None', 'Oil'), " +
                    "('Greek Yogurt', 59, 10, 3.6, 0.4, 'Dairy', 'Dairy/Egg'), " +
                    "('Oats', 389, 17, 66, 7, 'Gluten', 'Grain'), " +
                    "('Banana', 89, 1.1, 23, 0.3, 'None', 'Fruit'), " +
                    "('Peanut Butter', 588, 25, 20, 50, 'Peanuts', 'Nuts/Seeds'), " +
                    "('Tofu', 76, 8, 1.9, 4.8, 'Soy', 'Protein'), " +
                    "('Lentils', 116, 9, 20, 0.4, 'None', 'Legume'), " +
                    "('Tomato', 18, 0.9, 3.9, 0.2, 'None', 'Vegetable'), " +
                    "('Garlic', 149, 6.4, 33, 0.5, 'None', 'Vegetable'), " +
                    "('Onion', 40, 1.1, 9.3, 0.1, 'None', 'Vegetable'), " +
                    "('Mushroom', 22, 3.1, 3.3, 0.3, 'None', 'Vegetable'), " +
                    "('Cucumber', 16, 0.7, 3.6, 0.1, 'None', 'Vegetable'), " +
                    "('Bell Pepper', 20, 0.9, 4.6, 0.2, 'None', 'Vegetable'), " +
                    "('Carrot', 41, 0.9, 10, 0.2, 'None', 'Vegetable'), " +
                    "('Apple', 52, 0.3, 14, 0.2, 'None', 'Fruit'), " +
                    "('Blueberries', 57, 0.7, 14, 0.3, 'None', 'Fruit'), " +
                    "('Strawberries', 32, 0.7, 7.7, 0.3, 'None', 'Fruit'), " +
                    "('Pineapple', 50, 0.5, 13, 0.1, 'None', 'Fruit'), " +
                    "('Coconut Milk', 230, 2.3, 3.3, 24, 'Coconut', 'Dairy/Egg'), " +
                    "('Chia Seeds', 486, 17, 42, 31, 'None', 'Nuts/Seeds')"
            );

            db.setTransactionSuccessful(); // Commit the batch
        } finally {
            db.endTransaction(); // End transaction
        }
    }
    public void seedMealsTable(SQLiteDatabase db) {
        db.beginTransaction(); // ✅ Begin transaction for performance
        try {
            // Manually inserting 50 meals with different cuisines
            db.execSQL("INSERT OR IGNORE INTO meals (name, calories, protein, carbs, fats, diet_type, allergens, prep_time, culture, region, servings_grams, mealtime, image_name)  VALUES " +
//                  // Tagalog (Luzon)
                    "('Adobo', 250, 30, 10, 12, 'Omnivore', 'Soy', 45, 'Tagalog', 'Luzon', 150, 'Lunch/Dinner', 'Adobo.jpg'), " +
                    "('Sinigang na Baboy', 200, 25, 15, 8, 'Omnivore', 'None', 50, 'Tagalog', 'Luzon', 200, 'Lunch/Dinner', 'Sinigang na Baboy.jpg'), " +
                    "('Bulalo', 400, 35, 5, 25, 'Omnivore', 'None', 120, 'Tagalog', 'Luzon', 300, 'Lunch/Dinner', 'Bulalo.jpg'), " +
                    "('Kaldereta', 450, 40, 20, 22, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 250, 'Lunch/Dinner', 'Kaldereta.jpg'), " +
                    "('Pancit Canton', 300, 12, 45, 10, 'Omnivore', 'Gluten', 30, 'Tagalog', 'Luzon', 180, 'All Meals', 'Pancit Canton.jpg'), " +
                    "('Lomi', 350, 15, 50, 10, 'Omnivore', 'Gluten, Egg', 45, 'Tagalog', 'Luzon', 250, 'All Meals', 'Lomi.jpg'), " +
                    "('Puchero', 400, 30, 35, 12, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 300, 'Lunch/Dinner', 'Puchero.jpg'), " +
                    "('Arroz Caldo', 250, 20, 40, 5, 'Omnivore', 'None', 60, 'Tagalog', 'Luzon', 250, 'Breakfast', 'Arroz Caldo.jpg'), " +
                    "('Kare-Kare', 450, 35, 20, 30, 'Omnivore', 'Peanuts', 120, 'Tagalog', 'Luzon', 350, 'Lunch/Dinner', 'Kare-Kare.jpg'), " +
                    "('Bistek Tagalog', 300, 40, 10, 15, 'Omnivore', 'Soy', 45, 'Tagalog', 'Luzon', 200, 'Lunch/Dinner', 'Bistek Tagalog.jpg'), " +
                    "('Batangas Goto', 300, 20, 40, 8, 'Omnivore', 'None', 60, 'Tagalog', 'Luzon', 250, 'Breakfast', 'Batangas Goto.jpg'), " +
                    "('Longganisa Lucban', 350, 25, 10, 20, 'Omnivore', 'None', 30, 'Tagalog', 'Luzon', 120, 'Breakfast', 'Longganisa Lucban.jpg'), " +
                    "('Humba Tagalog', 450, 35, 15, 30, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 200, 'Lunch/Dinner', 'Humba Tagalog.jpg'), " +
                    "('Bulanglang', 200, 8, 30, 5, 'Vegetarian', 'None', 45, 'Tagalog', 'Luzon', 250, 'Lunch/Dinner', 'Bulanglang.jpg'), " +
                    "('Pinais na Isda', 280, 20, 5, 12, 'Pescatarian', 'Fish', 50, 'Tagalog', 'Luzon', 220, 'Lunch/Dinner', 'Pinais na Isda.jpg'), " +


                    // Ilocano (Luzon)
                    "('Pinakbet', 180, 6, 25, 5, 'Vegetarian', 'None', 40, 'Ilocano', 'Luzon', 200, 'Lunch/Dinner', 'Pinakbet.jpg'), " +
                    "('Bagnet', 500, 45, 10, 30, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 150, 'Lunch/Dinner', 'Bagnet.jpg'), " +
                    "('Dinengdeng', 150, 10, 20, 4, 'Vegetarian', 'None', 30, 'Ilocano', 'Luzon', 180, 'Lunch/Dinner', 'Dinengdeng.jpg'), " +
                    "('Poqui-Poqui', 220, 8, 20, 10, 'Vegetarian', 'Egg', 30, 'Ilocano', 'Luzon', 180, 'All Meals', 'Poqui-Poqui.jpg'), " +
                    "('Igado', 350, 40, 10, 20, 'Omnivore', 'None', 50, 'Ilocano', 'Luzon', 200, 'Lunch/Dinner', 'Igado.jpg'), " +
                    "('Dinakdakan', 400, 35, 5, 25, 'Omnivore', 'Egg', 45, 'Ilocano', 'Luzon', 180, 'Lunch/Dinner', 'Dinakdakan.jpg'), " +
                    "('Papaitan', 350, 40, 5, 20, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 220, 'Lunch/Dinner', 'Papaitan.jpg'), " +
                    "('Inabraw (Dinengdeng)', 180, 10, 25, 5, 'Vegetarian', 'None', 40, 'Ilocano', 'Luzon', 200, 'All Meals', 'Inabraw.jpg'), " +
                    "('Tupig', 200, 3, 40, 5, 'Vegetarian', 'None', 30, 'Ilocano', 'Luzon', 180, 'Breakfast', 'Tupig.jpg'), " +
                    "('Empanada', 280, 15, 35, 10, 'Omnivore', 'Gluten, Egg', 30, 'Ilocano', 'Luzon', 180, 'Breakfast', 'Empanada.jpg'), " +
                    "('Kaleskes', 400, 30, 8, 25, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 250, 'All Meals', 'Kaleskes.jpg'), " +
                    "('Abuos', 200, 15, 5, 10, 'Omnivore', 'None', 30, 'Ilocano', 'Luzon', 150, 'Lunch/Dinner', 'Abuos.jpg'), " +
                    "('Batac Empanada', 450, 20, 50, 15, 'Omnivore', 'Gluten', 45, 'Ilocano', 'Luzon', 200, 'Breakfast', 'Batac Empanada.jpg'), " +
                    "('Inartem', 380, 35, 10, 18, 'Omnivore', 'None', 50, 'Ilocano', 'Luzon', 220, 'Lunch/Dinner', 'Inartem.jpg'), " +
                    "('Dinardaraan', 300, 28, 5, 12, 'Omnivore', 'None', 40, 'Ilocano', 'Luzon', 180, 'Lunch/Dinner', 'Dinardaraan.jpg'), " +


                    // Kapampangan (Luzon)
                    "('Sisig', 600, 50, 5, 40, 'Omnivore', 'None', 45, 'Kapampangan', 'Luzon', 180, 'Lunch/Dinner', 'Sisig.jpg')," +
                    "('Bringhe', 400, 15, 50, 12, 'Omnivore', 'Gluten', 60, 'Kapampangan', 'Luzon', 250, 'Lunch/Dinner', 'Bringhe.jpg')," +
                    "('Tocino', 300, 25, 30, 15, 'Omnivore', 'None', 24, 'Kapampangan', 'Luzon', 120, 'Breakfast', 'Tocino.jpg')," +
                    "('Morcon', 450, 40, 10, 22, 'Omnivore', 'Egg', 90, 'Kapampangan', 'Luzon', 220, 'Lunch/Dinner', 'Morcon.jpg')," +
                    "('Burong Isda', 200, 15, 20, 5, 'Omnivore', 'Fish', 48, 'Kapampangan', 'Luzon', 180, 'All Meals', 'Burong Isda.jpg')," +
                    "('Pindang Damulag', 350, 40, 5, 15, 'Omnivore', 'None', 90, 'Kapampangan', 'Luzon', 220, 'Lunch/Dinner', 'Pindang Damulag.jpg')," +
                    "('Betute Tugak', 400, 30, 5, 20, 'Omnivore', 'None', 60, 'Kapampangan', 'Luzon', 200, 'Lunch/Dinner', 'Betute Tugak.jpg')," +
                    "('Kamaru', 250, 15, 10, 10, 'Omnivore', 'None', 45, 'Kapampangan', 'Luzon', 180, 'Lunch/Dinner', 'Kamaru.jpg')," +
                    "('Tibok-Tibok', 180, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Kapampangan', 'Luzon', 150, 'Breakfast', 'Tibok-Tibok.jpg')," +
                    "('San Nicolas Cookies', 200, 3, 35, 5, 'Vegetarian', 'Gluten, Egg', 30, 'Kapampangan', 'Luzon', 100, 'Breakfast', 'San Nicolas Cookies.jpg')," +
                    "('Aligue Rice', 500, 12, 60, 20, 'Omnivore', 'Shellfish', 30, 'Kapampangan', 'Luzon', 250, 'Lunch/Dinner', 'Aligue Rice.jpg')," +
                    "('Bulanglang Kapampangan', 250, 10, 40, 8, 'Vegetarian', 'None', 50, 'Kapampangan', 'Luzon', 200, 'All Meals', 'Bulanglang Kapampangan.jpg')," +
                    "('Buro with Mustasa', 200, 15, 20, 5, 'Omnivore', 'Fish', 60, 'Kapampangan', 'Luzon', 180, 'All Meals', 'Buro with Mustasa.jpg')," +
                    "('Pork Asado Kapampangan', 400, 35, 10, 18, 'Omnivore', 'None', 90, 'Kapampangan', 'Luzon', 250, 'Lunch/Dinner', 'Pork Asado Kapampangan.jpg')," +
                    "('Lechon Kawali with Liver Sauce', 600, 50, 5, 40, 'Omnivore', 'None', 75, 'Kapampangan', 'Luzon', 300, 'Lunch/Dinner', 'Lechon Kawali with Liver Sauce.jpg')," +


                    // Bicolano (Luzon)
                    "('Bicol Express', 380, 30, 20, 25, 'Omnivore', 'Coconut', 60, 'Bicolano', 'Luzon', 200, 'Lunch/Dinner', 'Bicol Express.jpg')," +
                    "('Laing', 180, 6, 15, 10, 'Vegetarian', 'Coconut', 40, 'Bicolano', 'Luzon', 120, 'Lunch/Dinner', 'Laing.jpg')," +
                    "('Kinunot', 250, 35, 5, 12, 'Pescatarian', 'Fish', 45, 'Bicolano', 'Luzon', 180, 'Lunch/Dinner', 'Kinunot.jpg')," +
                    "('Sinantolan', 200, 10, 25, 8, 'Vegetarian', 'Coconut', 30, 'Bicolano', 'Luzon', 150, 'All Meals', 'Sinantolan.jpg')," +
                    "('Tilmok', 280, 20, 10, 15, 'Pescatarian', 'Shellfish', 50, 'Bicolano', 'Luzon', 220, 'Lunch/Dinner', 'Tilmok.jpg')," +
                    "('Pili Nut Candy', 220, 5, 30, 10, 'Vegetarian', 'Nuts', 20, 'Bicolano', 'Luzon', 150, 'Breakfast', 'Pili Nut Candy.jpg')," +
                    "('Ginataang Santol', 250, 8, 20, 15, 'Vegetarian', 'Coconut', 40, 'Bicolano', 'Luzon', 200, 'All Meals', 'Ginataang Santol.jpg')," +
                    "('Kandingga', 300, 30, 5, 20, 'Omnivore', 'None', 50, 'Bicolano', 'Luzon', 180, 'Lunch/Dinner', 'Kandingga.jpg')," +
                    "('Pancit Bato', 350, 12, 50, 10, 'Omnivore', 'Gluten', 45, 'Bicolano', 'Luzon', 200, 'Lunch/Dinner', 'Pancit Bato.jpg')," +
                    "('Tinutungang Manok', 400, 35, 15, 20, 'Omnivore', 'Coconut', 60, 'Bicolano', 'Luzon', 250, 'Lunch/Dinner', 'Tinutungang Manok.jpg')," +
                    "('Ginataang Isda', 350, 30, 10, 15, 'Pescatarian', 'Coconut', 60, 'Bicolano', 'Luzon', 220, 'Lunch/Dinner', 'Ginataang Isda.jpg')," +
                    "('Sinanglay', 280, 22, 8, 12, 'Pescatarian', 'Coconut', 50, 'Bicolano', 'Luzon', 200, 'Lunch/Dinner', 'Sinanglay.jpg')," +
                    "('Pili Nut Tart', 250, 5, 35, 10, 'Vegetarian', 'Nuts', 40, 'Bicolano', 'Luzon', 180, 'Breakfast', 'Pili Nut Tart.jpg')," +
                    "('Kinagang', 300, 10, 50, 12, 'Omnivore', 'None', 55, 'Bicolano', 'Luzon', 220, 'Lunch/Dinner', 'Kinagang.jpg')," +
                    "('Ginataang Langka', 200, 8, 25, 10, 'Vegetarian', 'Coconut', 45, 'Bicolano', 'Luzon', 200, 'All Meals', 'Ginataang Langka.jpg')," +


                    // Cebuano (Visayas)
                    "('Lechon Cebu', 600, 50, 5, 40, 'Omnivore', 'None', 120, 'Cebuano', 'Visayas', 300, 'Lunch/Dinner', 'Lechon Cebu.jpg')," +
                    "('Bam-i', 350, 20, 50, 12, 'Omnivore', 'Gluten', 45, 'Cebuano', 'Visayas', 200, 'Lunch/Dinner', 'Bam.jpg')," +
                    "('Puso Rice', 150, 3, 30, 1, 'Vegetarian', 'None', 15, 'Cebuano', 'Visayas', 180, 'Breakfast', 'Puso Rice.jpg')," +
                    "('Ngohiong', 250, 10, 30, 10, 'Vegetarian', 'None', 40, 'Cebuano', 'Visayas', 180, 'Lunch/Dinner', 'Ngohiong.jpg')," +
                    "('Kinilaw', 200, 30, 5, 8, 'Pescatarian', 'Fish', 30, 'Cebuano', 'Visayas', 150, 'All Meals', 'Kinilaw.jpg')," +
                    "('Balbacua', 500, 40, 10, 35, 'Omnivore', 'None', 180, 'Cebuano', 'Visayas', 300, 'Lunch/Dinner', 'Balbacua.jpg')," +
                    "('Tuslob Buwa', 250, 15, 10, 20, 'Omnivore', 'None', 30, 'Cebuano', 'Visayas', 150, 'All Meals', 'Tuslob Buwa.jpg')," +
                    "('Budbud Kabog', 200, 4, 35, 5, 'Vegetarian', 'None', 30, 'Cebuano', 'Visayas', 180, 'Breakfast', 'Budbud Kabog.jpg')," +
                    "('Bingka Dawa', 180, 5, 30, 8, 'Vegetarian', 'None', 30, 'Cebuano', 'Visayas', 150, 'Breakfast', 'Bingka Dawa.jpg')," +
                    "('Humba', 450, 35, 20, 25, 'Omnivore', 'None', 120, 'Cebuano', 'Visayas', 250, 'Lunch/Dinner', 'Humba.jpg')," +
                    "('Dinuguang Baboy Cebuano', 400, 35, 10, 20, 'Omnivore', 'None', 60, 'Cebuano', 'Visayas', 250, 'Lunch/Dinner', 'Dinuguang Baboy Cebuano.jpg')," +
                    "('Sinuglaw', 380, 30, 15, 22, 'Omnivore', 'None', 50, 'Cebuano', 'Visayas', 220, 'Lunch/Dinner', 'Sinuglaw.jpg')," +
                    "('Tagaktak', 200, 5, 40, 8, 'Vegetarian', 'None', 45, 'Cebuano', 'Visayas', 180, 'Breakfast', 'Tagaktak.jpg')," +
                    "('Siomai sa Tisa', 280, 20, 30, 12, 'Omnivore', 'Gluten', 40, 'Cebuano', 'Visayas', 200, 'Lunch/Dinner', 'Siomai sa Tisa.jpg')," +
                    "('Buwad (Dried Fish)', 150, 25, 5, 8, 'Pescatarian', 'Fish', 30, 'Cebuano', 'Visayas', 100, 'All Meals', 'Buwad (Dried Fish).jpg')," +


                    // Muslim-Filipino (Mindanao)
                    "('Beef Rendang', 500, 40, 20, 25, 'Halal', 'Coconut', 90, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner', 'Beef Rendang.jpg')," +
                    "('Tiyula Itum', 400, 35, 10, 20, 'Halal', 'None', 90, 'Muslim-Filipino', 'Mindanao', 300, 'Lunch/Dinner', 'Tiyula Itum.jpg')," +
                    "('Pyanggang Manok', 380, 30, 15, 22, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner', 'Pyanggang Manok.jpg')," +
                    "('Pastil', 300, 25, 40, 10, 'Halal', 'None', 60, 'Muslim-Filipino', 'Mindanao', 200, 'All Meals', 'Pastil.jpg')," +
                    "('Lokot-Lokot', 150, 2, 30, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 100, 'Breakfast', 'Lokot-Lokot.jpg')," +
                    "('Kuning', 200, 5, 40, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 200, 'Breakfast', 'Kuning.jpg')," +
                    "('Piaparan', 380, 30, 20, 22, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner', 'Piaparan.jpg')," +
                    "('Bakas', 250, 35, 5, 10, 'Halal', 'Fish', 45, 'Muslim-Filipino', 'Mindanao', 200, 'All Meals', 'Bakas.jpg')," +
                    "('Putlih Manding', 400, 30, 15, 25, 'Halal', 'Coconut', 60, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner', 'Putlih Manding.jpg')," +
                    "('Agal-Agal', 150, 2, 30, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 100, 'Breakfast', 'Agal-Agal.jpg')," +
                    "('Sapal', 250, 10, 20, 10, 'Halal', 'Coconut', 40, 'Muslim-Filipino', 'Mindanao', 180, 'Lunch/Dinner', 'Sapal.jpg')," +
                    "('Pater', 320, 30, 45, 12, 'Halal', 'None', 50, 'Muslim-Filipino', 'Mindanao', 250, 'All Meals', 'Pater.jpg')," +
                    "('Latal', 450, 40, 15, 20, 'Halal', 'None', 60, 'Muslim-Filipino', 'Mindanao', 300, 'Lunch/Dinner', 'Latal.jpg')," +
                    "('Siyagul', 380, 30, 10, 15, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner', 'Siyagul.jpg')," +
                    "('Apam', 180, 5, 30, 5, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 150, 'Breakfast', 'Apam.jpg')," +


                    // Indigenous Cordilleran (Luzon)
                    "('Pinikpikan', 350, 40, 5, 20, 'Omnivore', 'None', 90, 'Cordilleran', 'Luzon', 250, 'Lunch/Dinner', 'Pinikpikan.jpg')," +
                    "('Etag', 450, 50, 5, 30, 'Omnivore', 'None', 48, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner', 'Etag.jpg')," +
                    "('Inasin', 300, 35, 5, 20, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220, 'Lunch/Dinner', 'Inasin.jpg')," +
                    "('Linapay', 280, 25, 15, 15, 'Omnivore', 'None', 75, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner', 'Linapay.jpg')," +
                    "('Watwat', 320, 30, 5, 18, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner', 'Watwat.jpg')," +
                    "('Inasin a Baboy', 350, 40, 5, 20, 'Omnivore', 'None', 90, 'Cordilleran', 'Luzon', 250, 'Lunch/Dinner', 'Inasin a Baboy.jpg')," +
                    "('Kinuday', 450, 50, 5, 30, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220, 'Lunch/Dinner', 'Kinuday.jpg')," +
                    "('Binungor', 300, 25, 15, 15, 'Omnivore', 'None', 75, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner', 'Binungor.jpg')," +
                    "('Patopat', 200, 3, 40, 5, 'Vegetarian', 'None', 30, 'Cordilleran', 'Luzon', 180, 'Breakfast', 'Patopat.jpg')," +
                    "('Sabusab', 180, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Cordilleran', 'Luzon', 150, 'Breakfast', 'Sabusab.jpg')," +
                    "('Kiniing', 350, 40, 8, 20, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220, 'Lunch/Dinner', 'Kiniing.jpg')," +
                    "('Inabraw', 200, 10, 30, 5, 'Vegetarian', 'None', 50, 'Cordilleran', 'Luzon', 200, 'All Meals', 'Inabraw.jpg')," +
                    "('Tupig Cordillera', 300, 5, 50, 8, 'Vegetarian', 'None', 45, 'Cordilleran', 'Luzon', 180, 'Breakfast', 'Tupig Cordillera.jpg')," +
                    "('Inkiwar', 280, 8, 40, 10, 'Vegetarian', 'None', 50, 'Cordilleran', 'Luzon', 220, 'Breakfast', 'Inkiwar.jpg')," +
                    "('Baya-o', 150, 2, 30, 5, 'Vegetarian', 'None', 30, 'Cordilleran', 'Luzon', 150, 'Breakfast', 'Baya-o.jpg')," +


                    // Chavacano (Zamboanga)
                    "('Calandracas', 400, 30, 15, 20, 'Omnivore', 'None', 45, 'Chavacano', 'Mindanao', 250, 'Lunch/Dinner', 'Calandracas.jpg')," +
                    "('Arroz a la Valenciana', 450, 20, 60, 15, 'Omnivore', 'None', 60, 'Chavacano', 'Mindanao', 300, 'Lunch/Dinner', 'Arroz a la Valenciana.jpg')," +
                    "('Guisado de Mariscos', 350, 30, 20, 12, 'Pescatarian', 'Shellfish', 50, 'Chavacano', 'Mindanao', 220, 'Lunch/Dinner', 'Guisado de Mariscos.jpg')," +
                    "('Torones de Mani', 200, 5, 35, 8, 'Vegetarian', 'Nuts', 40, 'Chavacano', 'Mindanao', 180, 'Breakfast', 'Torones de Mani.jpg')," +
                    "('Puchero Zamboangueño', 380, 35, 15, 18, 'Omnivore', 'None', 75, 'Chavacano', 'Mindanao', 250, 'Lunch/Dinner', 'Puchero Zamboangueño.jpg')," +
                    "('Knickerbocker', 250, 4, 50, 5, 'Vegetarian', 'Dairy', 20, 'Chavacano', 'Mindanao', 200, 'Breakfast', 'Knickerbocker.jpg')," +
                    "('Alavar Sauce', 100, 2, 10, 8, 'Pescatarian', 'Coconut', 30, 'Chavacano', 'Mindanao', 100, 'All Meals', 'Alavar Sauce.jpg')," +
                    "('Tamales Zamboangueño', 350, 12, 40, 15, 'Omnivore', 'Gluten, Peanuts', 60, 'Chavacano', 'Mindanao', 200, 'Lunch/Dinner', 'Tamales Zamboangueño.jpg')," +
                    "('Paella Chavacano', 450, 35, 60, 15, 'Omnivore', 'Shellfish', 90, 'Chavacano', 'Mindanao', 300, 'Lunch/Dinner', 'Paella Chavacano.jpg')," +
                    "('Toron con Mani', 220, 4, 35, 8, 'Vegetarian', 'Peanuts', 30, 'Chavacano', 'Mindanao', 180, 'Breakfast', 'Toron con Mani.jpg')," +
                    "('Satti', 400, 35, 10, 20, 'Omnivore', 'None', 40, 'Chavacano', 'Mindanao', 200, 'Lunch/Dinner', 'Satti.jpg')," +
                    "('Tamal', 250, 10, 30, 8, 'Omnivore', 'Gluten', 60, 'Chavacano', 'Mindanao', 200, 'Lunch/Dinner', 'Tamal.jpg')," +
                    "('Curacha with Alavar Sauce', 500, 40, 10, 25, 'Pescatarian', 'Shellfish', 75, 'Chavacano', 'Mindanao', 250, 'Lunch/Dinner', 'Curacha with Alavar Sauce.jpg')," +
                    "('Kiampong', 300, 8, 50, 5, 'Vegetarian', 'None', 45, 'Chavacano', 'Mindanao', 200, 'Breakfast', 'Kiampong.jpg')," +
                    "('Maja Blanca', 200, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Chavacano', 'Mindanao', 150, 'Breakfast', 'Maja Blanca.jpg')");



            db.setTransactionSuccessful(); // ✅ Commit the batch
        } finally {
            db.endTransaction(); // ✅ End transaction
        }
    }

}
