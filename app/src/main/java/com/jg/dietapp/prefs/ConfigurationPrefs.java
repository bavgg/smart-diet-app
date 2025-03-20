package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigurationPrefs {
    private static final String PREFS_NAME = "configuration_prefs";
    private static final String KEY_ASSET_LOADED = "is_asset_loaded";
    private static final String KEY_INITIALIZED = "has_initialized";
    private static final String KEY_INITIALIZED_ONLINE = "initialized_online";
    private static final String KEY_DB_SEEDED = "has_seeded";

    private final SharedPreferences sharedPreferences;

    public ConfigurationPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setAssetLoaded(boolean loaded) {
        sharedPreferences.edit().putBoolean(KEY_ASSET_LOADED, loaded).apply();
    }

    public boolean isAssetLoaded() {
        return sharedPreferences.getBoolean(KEY_ASSET_LOADED, false); // Default to false if not set
    }

    public boolean isInitialized() {
        return sharedPreferences.getBoolean(KEY_INITIALIZED, false);
    }
    public void setIsInitialized(boolean initialized) {
        sharedPreferences.edit().putBoolean(KEY_INITIALIZED, initialized).apply();
    }

    public boolean isInitializedOnline() {
        return sharedPreferences.getBoolean(KEY_INITIALIZED_ONLINE, false);
    }
    public void setIsInitializedOnline(boolean initializedOnline) {
        sharedPreferences.edit().putBoolean(KEY_INITIALIZED_ONLINE, initializedOnline).apply();
    }
    public boolean hasSeeded() {
        return sharedPreferences.getBoolean(KEY_DB_SEEDED, false);
    }
    public void setHasSeeded(boolean initializedOnline) {
        sharedPreferences.edit().putBoolean(KEY_DB_SEEDED, initializedOnline).apply();
    }
}
