package com.jg.dietapp.fragments.user_input;
import static com.jg.dietapp.UserInputActivity.increaseProgress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.R;

public class FragmentStart extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_startnow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Close Bottom Sheet on Button Click
        Button startNow = view.findViewById(R.id.startNow);

        startNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                increaseProgress();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FragmentGoal())
                        .addToBackStack(null) // Optional: Allows back navigation
                        .commit();
            }
        });
    }
}

