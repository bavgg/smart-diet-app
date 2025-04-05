package com.jg.dietapp.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOUser {
    private String USER_TABLE_NAME = "users";
    private String USER_COL_ID = "user_id";
    private String USER_COL_EMAIL = "email";
    private String USER_COL_PASSWORD = "password";
    private static final String TAG = "DAOMeal";
    private final SQLiteDatabase db;

    public DAOUser(DatabaseHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
    }

    public boolean registerUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put("email", username);
        values.put("password", password);

        long result = db.insert(USER_TABLE_NAME, null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    public int loginUser(String username, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                USER_COL_EMAIL + "=? AND " + USER_COL_PASSWORD + "=?", new String[]{username, password});

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return userId;
    }
}
