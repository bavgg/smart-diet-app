package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthPrefs {
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AuthPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public void clearAuthPrefs() {
        editor.clear();
        editor.apply();
    }
}
