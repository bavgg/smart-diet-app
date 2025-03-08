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
                "mealtime TEXT)"
        );

        db.execSQL("CREATE TABLE exercises (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "category TEXT, " +
                "duration INTEGER, " +
                "calories_burned INTEGER)");

        seedDatabase(db); // ✅ Seeding the database
    }

    public void seedDatabase(SQLiteDatabase db) {
        db.beginTransaction(); // ✅ Begin transaction for performance
        try {
            // Manually inserting 50 meals with different cuisines
            db.execSQL("INSERT OR IGNORE INTO meals (name, calories, protein, carbs, fats, diet_type, allergens, prep_time, culture, region, servings_grams, mealtime)  VALUES " +
//                  // Tagalog (Luzon)
                    "('Adobo', 250, 30, 10, 12, 'Omnivore', 'Soy', 45, 'Tagalog', 'Luzon', 150, 'Lunch/Dinner'), " +
                    "('Sinigang na Baboy', 200, 25, 15, 8, 'Omnivore', 'None', 50, 'Tagalog', 'Luzon', 200, 'Lunch/Dinner'), " +
                    "('Bulalo', 400, 35, 5, 25, 'Omnivore', 'None', 120, 'Tagalog', 'Luzon', 300, 'Lunch/Dinner'), " +
                    "('Kaldereta', 450, 40, 20, 22, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 250, 'Lunch/Dinner'), " +
                    "('Pancit Canton', 300, 12, 45, 10, 'Omnivore', 'Gluten', 30, 'Tagalog', 'Luzon', 180, 'All Meals'), " +
                    "('Lomi', 350, 15, 50, 10, 'Omnivore', 'Gluten, Egg', 45, 'Tagalog', 'Luzon', 250, 'All Meals'), " +
                    "('Puchero', 400, 30, 35, 12, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 300, 'Lunch/Dinner'), " +
                    "('Arroz Caldo', 250, 20, 40, 5, 'Omnivore', 'None', 60, 'Tagalog', 'Luzon', 250, 'Breakfast'), " +
                    "('Kare-Kare', 450, 35, 20, 30, 'Omnivore', 'Peanuts', 120, 'Tagalog', 'Luzon', 350, 'Lunch/Dinner'), " +
                    "('Bistek Tagalog', 300, 40, 10, 15, 'Omnivore', 'Soy', 45, 'Tagalog', 'Luzon', 200, 'Lunch/Dinner'), " +
                    "('Batangas Goto', 300, 20, 40, 8, 'Omnivore', 'None', 60, 'Tagalog', 'Luzon', 250, 'Breakfast'), " +
                    "('Longganisa Lucban', 350, 25, 10, 20, 'Omnivore', 'None', 30, 'Tagalog', 'Luzon', 120, 'Breakfast'), " +
                    "('Humba Tagalog', 450, 35, 15, 30, 'Omnivore', 'None', 90, 'Tagalog', 'Luzon', 200, 'Lunch/Dinner'), " +
                    "('Bulanglang', 200, 8, 30, 5, 'Vegetarian', 'None', 45, 'Tagalog', 'Luzon', 250, 'Lunch/Dinner'), " +
                    "('Pinais na Isda', 280, 20, 5, 12, 'Pescatarian', 'Fish', 50, 'Tagalog', 'Luzon', 220, 'Lunch/Dinner'), " +



                    // Ilocano (Luzon)
                    "('Pinakbet', 180, 6, 25, 5, 'Vegetarian', 'None', 40, 'Ilocano', 'Luzon', 200, 'Lunch/Dinner'), " +
                    "('Bagnet', 500, 45, 10, 30, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 150, 'Lunch/Dinner'), " +
                    "('Dinengdeng', 150, 10, 20, 4, 'Vegetarian', 'None', 30, 'Ilocano', 'Luzon', 180, 'Lunch/Dinner'), " +
                    "('Poqui-Poqui', 220, 8, 20, 10, 'Vegetarian', 'Egg', 30, 'Ilocano', 'Luzon', 180, 'All Meals'), " +
                    "('Igado', 350, 40, 10, 20, 'Omnivore', 'None', 50, 'Ilocano', 'Luzon', 200, 'Lunch/Dinner'), " +
                    "('Dinakdakan', 400, 35, 5, 25, 'Omnivore', 'Egg', 45, 'Ilocano', 'Luzon', 180, 'Lunch/Dinner'), " +
                    "('Papaitan', 350, 40, 5, 20, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 220, 'Lunch/Dinner'), " +
                    "('Inabraw (Dinengdeng)', 180, 10, 25, 5, 'Vegetarian', 'None', 40, 'Ilocano', 'Luzon', 200, 'All Meals'), " +
                    "('Tupig', 200, 3, 40, 5, 'Vegetarian', 'None', 30, 'Ilocano', 'Luzon', 180, 'Breakfast'), " +
                    "('Empanada', 280, 15, 35, 10, 'Omnivore', 'Gluten, Egg', 30, 'Ilocano', 'Luzon', 180, 'Breakfast'), " +
                    "('Kaleskes', 400, 30, 8, 25, 'Omnivore', 'None', 60, 'Ilocano', 'Luzon', 250, 'All Meals'), " +
                    "('Abuos', 200, 15, 5, 10, 'Omnivore', 'None', 30, 'Ilocano', 'Luzon', 150, 'Lunch/Dinner'), " +
                    "('Batac Empanada', 450, 20, 50, 15, 'Omnivore', 'Gluten', 45, 'Ilocano', 'Luzon', 200, 'Breakfast'), " +
                    "('Inartem', 380, 35, 10, 18, 'Omnivore', 'None', 50, 'Ilocano', 'Luzon', 220, 'Lunch/Dinner'), " +
                    "('Dinardaraan', 300, 28, 5, 12, 'Omnivore', 'None', 40, 'Ilocano', 'Luzon', 180, 'Lunch/Dinner'), " +




                    // Kapampangan (Luzon)
                    "('Sisig', 600, 50, 5, 40, 'Omnivore', 'None', 45, 'Kapampangan', 'Luzon', 180, 'Lunch/Dinner')," +
                    "('Bringhe', 400, 15, 50, 12, 'Omnivore', 'Gluten', 60, 'Kapampangan', 'Luzon', 250, 'Lunch/Dinner')," +
                    "('Tocino', 300, 25, 30, 15, 'Omnivore', 'None', 24, 'Kapampangan', 'Luzon', 120, 'Breakfast')," +
                    "('Morcon', 450, 40, 10, 22, 'Omnivore', 'Egg', 90, 'Kapampangan', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Burong Isda', 200, 15, 20, 5, 'Omnivore', 'Fish', 48, 'Kapampangan', 'Luzon', 180, 'All Meals')," +
                    "('Pindang Damulag', 350, 40, 5, 15, 'Omnivore', 'None', 90, 'Kapampangan', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Betute Tugak', 400, 30, 5, 20, 'Omnivore', 'None', 60, 'Kapampangan', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Kamaru', 250, 15, 10, 10, 'Omnivore', 'None', 45, 'Kapampangan', 'Luzon', 180, 'Lunch/Dinner')," +
                    "('Tibok-Tibok', 180, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Kapampangan', 'Luzon', 150, 'Breakfast')," +
                    "('San Nicolas Cookies', 200, 3, 35, 5, 'Vegetarian', 'Gluten, Egg', 30, 'Kapampangan', 'Luzon', 100, 'Breakfast')," +
                    "('Aligue Rice', 500, 12, 60, 20, 'Omnivore', 'Shellfish', 30, 'Kapampangan', 'Luzon', 250, 'Lunch/Dinner')," +
                    "('Bulanglang Kapampangan', 250, 10, 40, 8, 'Vegetarian', 'None', 50, 'Kapampangan', 'Luzon', 200, 'All Meals')," +
                    "('Buro with Mustasa', 200, 15, 20, 5, 'Omnivore', 'Fish', 60, 'Kapampangan', 'Luzon', 180, 'All Meals')," +
                    "('Pork Asado Kapampangan', 400, 35, 10, 18, 'Omnivore', 'None', 90, 'Kapampangan', 'Luzon', 250, 'Lunch/Dinner')," +
                    "('Lechon Kawali with Liver Sauce', 600, 50, 5, 40, 'Omnivore', 'None', 75, 'Kapampangan', 'Luzon', 300, 'Lunch/Dinner')," +




                    // Bicolano (Luzon)
                    "('Bicol Express', 380, 30, 20, 25, 'Omnivore', 'Coconut', 60, 'Bicolano', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Laing', 180, 6, 15, 10, 'Vegetarian', 'Coconut', 40, 'Bicolano', 'Luzon', 120, 'Lunch/Dinner')," +
                    "('Kinunot', 250, 35, 5, 12, 'Pescatarian', 'Fish', 45, 'Bicolano', 'Luzon', 180, 'Lunch/Dinner')," +
                    "('Sinantolan', 200, 10, 25, 8, 'Vegetarian', 'Coconut', 30, 'Bicolano', 'Luzon', 150, 'All Meals')," +
                    "('Tilmok', 280, 20, 10, 15, 'Pescatarian', 'Shellfish', 50, 'Bicolano', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Pili Nut Candy', 220, 5, 30, 10, 'Vegetarian', 'Nuts', 20, 'Bicolano', 'Luzon', 150, 'Breakfast')," +
                    "('Ginataang Santol', 250, 8, 20, 15, 'Vegetarian', 'Coconut', 40, 'Bicolano', 'Luzon', 200, 'All Meals')," +
                    "('Kandingga', 300, 30, 5, 20, 'Omnivore', 'None', 50, 'Bicolano', 'Luzon', 180, 'Lunch/Dinner')," +
                    "('Pancit Bato', 350, 12, 50, 10, 'Omnivore', 'Gluten', 45, 'Bicolano', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Tinutungang Manok', 400, 35, 15, 20, 'Omnivore', 'Coconut', 60, 'Bicolano', 'Luzon', 250, 'Lunch/Dinner')," +
                    "('Ginataang Isda', 350, 30, 10, 15, 'Pescatarian', 'Coconut', 60, 'Bicolano', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Sinanglay', 280, 22, 8, 12, 'Pescatarian', 'Coconut', 50, 'Bicolano', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Pili Nut Tart', 250, 5, 35, 10, 'Vegetarian', 'Nuts', 40, 'Bicolano', 'Luzon', 180, 'Breakfast')," +
                    "('Kinagang', 300, 10, 50, 12, 'Omnivore', 'None', 55, 'Bicolano', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Ginataang Langka', 200, 8, 25, 10, 'Vegetarian', 'Coconut', 45, 'Bicolano', 'Luzon', 200, 'All Meals')," +




                    // Cebuano (Visayas)
                    "('Lechon Cebu', 600, 50, 5, 40, 'Omnivore', 'None', 120, 'Cebuano', 'Visayas', 300, 'Lunch/Dinner')," +
                    "('Bam-i', 350, 20, 50, 12, 'Omnivore', 'Gluten', 45, 'Cebuano', 'Visayas', 200, 'Lunch/Dinner')," +
                    "('Puso Rice', 150, 3, 30, 1, 'Vegetarian', 'None', 15, 'Cebuano', 'Visayas', 180, 'Breakfast')," +
                    "('Ngohiong', 250, 10, 30, 10, 'Vegetarian', 'None', 40, 'Cebuano', 'Visayas', 180, 'Lunch/Dinner')," +
                    "('Kinilaw', 200, 30, 5, 8, 'Pescatarian', 'Fish', 30, 'Cebuano', 'Visayas', 150, 'All Meals')," +
                    "('Balbacua', 500, 40, 10, 35, 'Omnivore', 'None', 180, 'Cebuano', 'Visayas', 300, 'Lunch/Dinner')," +
                    "('Tuslob Buwa', 250, 15, 10, 20, 'Omnivore', 'None', 30, 'Cebuano', 'Visayas', 150, 'All Meals')," +
                    "('Budbud Kabog', 200, 4, 35, 5, 'Vegetarian', 'None', 30, 'Cebuano', 'Visayas', 180, 'Breakfast')," +
                    "('Bingka Dawa', 180, 5, 30, 8, 'Vegetarian', 'None', 30, 'Cebuano', 'Visayas', 150, 'Breakfast')," +
                    "('Humba', 450, 35, 20, 25, 'Omnivore', 'None', 120, 'Cebuano', 'Visayas', 250, 'Lunch/Dinner')," +
                    "('Dinuguang Baboy Cebuano', 400, 35, 10, 20, 'Omnivore', 'None', 60, 'Cebuano', 'Visayas', 250, 'Lunch/Dinner')," +
                    "('Sinuglaw', 380, 30, 15, 22, 'Omnivore', 'None', 50, 'Cebuano', 'Visayas', 220, 'Lunch/Dinner')," +
                    "('Tagaktak', 200, 5, 40, 8, 'Vegetarian', 'None', 45, 'Cebuano', 'Visayas', 180, 'Breakfast')," +
                    "('Siomai sa Tisa', 280, 20, 30, 12, 'Omnivore', 'Gluten', 40, 'Cebuano', 'Visayas', 200, 'Lunch/Dinner')," +
                    "('Buwad (Dried Fish)', 150, 25, 5, 8, 'Pescatarian', 'Fish', 30, 'Cebuano', 'Visayas', 100, 'All Meals')," +




                    // Muslim-Filipino (Mindanao)
                    "('Beef Rendang', 500, 40, 20, 25, 'Halal', 'Coconut', 90, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Tiyula Itum', 400, 35, 10, 20, 'Halal', 'None', 90, 'Muslim-Filipino', 'Mindanao', 300, 'Lunch/Dinner')," +
                    "('Pyanggang Manok', 380, 30, 15, 22, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Pastil', 300, 25, 40, 10, 'Halal', 'None', 60, 'Muslim-Filipino', 'Mindanao', 200, 'All Meals')," +
                    "('Lokot-Lokot', 150, 2, 30, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 100, 'Breakfast')," +
                    "('Kuning', 200, 5, 40, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 200, 'Breakfast')," +
                    "('Piaparan', 380, 30, 20, 22, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Bakas', 250, 35, 5, 10, 'Halal', 'Fish', 45, 'Muslim-Filipino', 'Mindanao', 200, 'All Meals')," +
                    "('Putlih Manding', 400, 30, 15, 25, 'Halal', 'Coconut', 60, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Agal-Agal', 150, 2, 30, 3, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 100, 'Breakfast')," +
                    "('Sapal', 250, 10, 20, 10, 'Halal', 'Coconut', 40, 'Muslim-Filipino', 'Mindanao', 180, 'Lunch/Dinner')," +
                    "('Pater', 320, 30, 45, 12, 'Halal', 'None', 50, 'Muslim-Filipino', 'Mindanao', 250, 'All Meals')," +
                    "('Latal', 450, 40, 15, 20, 'Halal', 'None', 60, 'Muslim-Filipino', 'Mindanao', 300, 'Lunch/Dinner')," +
                    "('Siyagul', 380, 30, 10, 15, 'Halal', 'Coconut', 75, 'Muslim-Filipino', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Apam', 180, 5, 30, 5, 'Halal', 'None', 30, 'Muslim-Filipino', 'Mindanao', 150, 'Breakfast')," +




                    // Indigenous Cordilleran (Luzon)
                    "('Pinikpikan', 350, 40, 5, 20, 'Omnivore', 'None', 90, 'Cordilleran', 'Luzon', 250, 'Lunch/Dinner')," +
                    "('Etag', 450, 50, 5, 30, 'Omnivore', 'None', 48, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Inasin', 300, 35, 5, 20, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Linapay', 280, 25, 15, 15, 'Omnivore', 'None', 75, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Watwat', 320, 30, 5, 18, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Inasin a Baboy', 350, 40, 5, 20, 'Omnivore', 'None', 90, 'Cordilleran', 'Luzon', 250, 'Lunch/Dinner')," +
                    "('Kinuday', 450, 50, 5, 30, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Binungor', 300, 25, 15, 15, 'Omnivore', 'None', 75, 'Cordilleran', 'Luzon', 200, 'Lunch/Dinner')," +
                    "('Patopat', 200, 3, 40, 5, 'Vegetarian', 'None', 30, 'Cordilleran', 'Luzon', 180, 'Breakfast')," +
                    "('Sabusab', 180, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Cordilleran', 'Luzon', 150, 'Breakfast')," +
                    "('Kiniing', 350, 40, 8, 20, 'Omnivore', 'None', 60, 'Cordilleran', 'Luzon', 220, 'Lunch/Dinner')," +
                    "('Inabraw', 200, 10, 30, 5, 'Vegetarian', 'None', 50, 'Cordilleran', 'Luzon', 200, 'All Meals')," +
                    "('Tupig Cordillera', 300, 5, 50, 8, 'Vegetarian', 'None', 45, 'Cordilleran', 'Luzon', 180, 'Breakfast')," +
                    "('Inkiwar', 280, 8, 40, 10, 'Vegetarian', 'None', 50, 'Cordilleran', 'Luzon', 220, 'Breakfast')," +
                    "('Baya-o', 150, 2, 30, 5, 'Vegetarian', 'None', 30, 'Cordilleran', 'Luzon', 150, 'Breakfast')," +




                    // Chavacano (Zamboanga)
                    "('Calandracas', 400, 30, 15, 20, 'Omnivore', 'None', 45, 'Chavacano', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Arroz a la Valenciana', 450, 20, 60, 15, 'Omnivore', 'None', 60, 'Chavacano', 'Mindanao', 300, 'Lunch/Dinner')," +
                    "('Guisado de Mariscos', 350, 30, 20, 12, 'Pescatarian', 'Shellfish', 50, 'Chavacano', 'Mindanao', 220, 'Lunch/Dinner')," +
                    "('Torones de Mani', 200, 5, 35, 8, 'Vegetarian', 'Nuts', 40, 'Chavacano', 'Mindanao', 180, 'Breakfast')," +
                    "('Puchero Zamboangueño', 380, 35, 15, 18, 'Omnivore', 'None', 75, 'Chavacano', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Knickerbocker', 250, 4, 50, 5, 'Vegetarian', 'Dairy', 20, 'Chavacano', 'Mindanao', 200, 'Breakfast')," +
                    "('Alavar Sauce', 100, 2, 10, 8, 'Pescatarian', 'Coconut', 30, 'Chavacano', 'Mindanao', 100, 'All Meals')," +
                    "('Tamales Zamboangueño', 350, 12, 40, 15, 'Omnivore', 'Gluten, Peanuts', 60, 'Chavacano', 'Mindanao', 200, 'Lunch/Dinner')," +
                    "('Paella Chavacano', 450, 35, 60, 15, 'Omnivore', 'Shellfish', 90, 'Chavacano', 'Mindanao', 300, 'Lunch/Dinner')," +
                    "('Toron con Mani', 220, 4, 35, 8, 'Vegetarian', 'Peanuts', 30, 'Chavacano', 'Mindanao', 180, 'Breakfast')," +
                    "('Satti', 400, 35, 10, 20, 'Omnivore', 'None', 40, 'Chavacano', 'Mindanao', 200, 'Lunch/Dinner')," +
                    "('Tamal', 250, 10, 30, 8, 'Omnivore', 'Gluten', 60, 'Chavacano', 'Mindanao', 200, 'Lunch/Dinner')," +
                    "('Curacha with Alavar Sauce', 500, 40, 10, 25, 'Pescatarian', 'Shellfish', 75, 'Chavacano', 'Mindanao', 250, 'Lunch/Dinner')," +
                    "('Kiampong', 300, 8, 50, 5, 'Vegetarian', 'None', 45, 'Chavacano', 'Mindanao', 200, 'Breakfast')," +
                    "('Maja Blanca', 200, 5, 30, 8, 'Vegetarian', 'Coconut', 30, 'Chavacano', 'Mindanao', 150, 'Breakfast')");


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
