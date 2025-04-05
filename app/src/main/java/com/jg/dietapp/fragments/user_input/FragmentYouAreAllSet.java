package com.jg.dietapp.fragments.user_input;

import static com.jg.dietapp.UserInputActivity.userInput;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.LoadingActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.prefs.LoadPrefs;

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

        LoadPrefs firebaseDataPrefs = new LoadPrefs(view.getContext());

        nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            // Save user inputs to shared prefs
            userInput.setUserSubmitted(true);
            firebaseDataPrefs.saveUserInput(userInput);

            Intent intent = new Intent(getContext(), LoadingActivity.class);
            startActivity(intent);
        });



    }
}
