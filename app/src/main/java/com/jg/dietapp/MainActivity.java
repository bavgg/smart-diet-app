package com.jg.dietapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jg.dietapp.data.DAOExercise;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.fragments.main.FragmentMeals;
import com.jg.dietapp.fragments.main.FragmentPlan;
import com.jg.dietapp.fragments.main.FragmentSettings;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.ProfileUtils;

public class MainActivity extends AppCompatActivity {

    private FragmentPlan fragmentPlan = new FragmentPlan();
    private FragmentSettings fragmentSettings = new FragmentSettings();
    private FragmentMeals fragmentMeals = new FragmentMeals();
    private Fragment activeFragment = fragmentPlan;  // Track active fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate Hello world");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ProfileUtils profileUtils = new ProfileUtils();
        profileUtils.getUserName();

        // Set UI
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragmentPlan, "PLAN")
                .add(R.id.fragment_container_view, fragmentSettings, "SETTINGS")
                .hide(fragmentSettings)
                .add(R.id.fragment_container_view, fragmentMeals, "MEALS")
                .hide(fragmentMeals)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                switchFragment(fragmentPlan);
            } else if (item.getItemId() == R.id.nav_settings) {
                switchFragment(fragmentSettings);
            }
            return true;
        });

    }

    private void switchFragment(Fragment targetFragment) {
        if (targetFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment) // Hide current fragment
                    .show(targetFragment) // Show target fragment
                    .commit();
            activeFragment = targetFragment; // Update active fragment
        }
    }
}
