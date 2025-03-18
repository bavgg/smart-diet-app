package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.jg.dietapp.models.UserInput;

public class UserInputsPrefs {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USER = "user_data";
    Context context;

    public UserInputsPrefs(Context context) {
        this.context = context;
    }

    public void saveUser( UserInput user) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Convert object to JSON string
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(KEY_USER, userJson); // Save JSON string
        editor.apply(); // Save changes asynchronously
    }

    public UserInput getUser() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String userJson = prefs.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, UserInput.class);
        }
        return new UserInput(); // Return a default instance if no data exists
    }

    public void clearUser() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_USER).apply(); // Remove user data
    }
}
