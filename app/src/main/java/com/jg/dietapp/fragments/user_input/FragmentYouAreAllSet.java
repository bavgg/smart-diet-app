package com.jg.dietapp.fragments.user_input;

import static com.jg.dietapp.UserInputActivity.userInput;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.jg.dietapp.MainActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.prefs.FirebaseDataPrefs;

public class FragmentYouAreAllSet extends Fragment {

    Button nextButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_you_are_all_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        nextButton = view.findViewById(R.id.nextButton);


//        userInput.setUserSubmitted(true);

//        firebaseDataPrefs.saveUser(userInput);
//        System.out.println("You are all set: FirebaseDataPrefs: " + firebaseDataPrefs.getUser());
//        firebaseUtils.syncUserInput();
//
//        Log.i("TAG", "FragmentYouAreAllSet FirebaseDataPrefs: " + new Gson().toJson(firebaseDataPrefs));

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });



    }
}
