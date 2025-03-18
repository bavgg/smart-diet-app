package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigurationPrefs {
    private static final String KEY_ASSET_LOADED = "asset_loaded"; // New key for assetLoaded
    private static final String PREFS_NAME = "AssetPrefs";
    private final SharedPreferences prefs;

    public ConfigurationPrefs(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // New methods for assetLoaded flag
    public void setAssetLoaded(boolean loaded) {
        prefs.edit().putBoolean(KEY_ASSET_LOADED, loaded).apply();
    }

    public boolean isAssetLoaded() {
        return prefs.getBoolean(KEY_ASSET_LOADED, false); // Default to false if not set
    }
}
