package com.jg.dietapp.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileUtils
{
    public void getUserName() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String displayName = user.getDisplayName(); // Get full name

            if (displayName != null && !displayName.isEmpty()) {
                String[] nameParts = displayName.split(" ", 2); // Split name by first space
                String firstName = nameParts[0]; // First part
                String lastName = (nameParts.length > 1) ? nameParts[1] : ""; // Rest as last name

                // Debugging
                Log.d("FirebaseAuth", "First Name: " + firstName);
                Log.d("FirebaseAuth", "Last Name: " + lastName);
            }
        } else {
            Log.d("FirebaseAuth", "No user signed in");
        }
    }
}
