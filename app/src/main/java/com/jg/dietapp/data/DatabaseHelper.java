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
                "mealtime TEXT," +
                "image_name TEXT)"
        );

        db.execSQL("CREATE TABLE exercises (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "category TEXT, " +
                "duration INTEGER, " +
                "calories_burned INTEGER)");

//        seedDatabase(db); // ✅ Seeding the database
    }

    public void seedDatabase(SQLiteDatabase db) {
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
