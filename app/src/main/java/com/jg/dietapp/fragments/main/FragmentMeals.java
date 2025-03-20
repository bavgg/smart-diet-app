package com.jg.dietapp.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jg.dietapp.R;
import com.jg.dietapp.adapters.MealAdminAdapter;
import com.jg.dietapp.dialogs.DialogCreateMeal;
import com.jg.dietapp.viewmodel.RecentMealsViewModel;

import java.util.ArrayList;

public class FragmentMeals extends Fragment {
    RecyclerView recyclerViewMeals;
    private MealAdminAdapter mealAdminAdapter;
    Button addMealButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addMealButton = view.findViewById(R.id.addMealButton);



        // Fetch meals from ViewModel
        RecentMealsViewModel recentMealsViewModel = new ViewModelProvider(requireActivity()).get(RecentMealsViewModel.class);

// Initialize RecyclerView
        recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));

// Initialize Adapter with an empty list
        mealAdminAdapter = new MealAdminAdapter(new ArrayList<>());
        recyclerViewMeals.setAdapter(mealAdminAdapter);

// Observe LiveData and update adapter
        recentMealsViewModel.getMeals().observe(getViewLifecycleOwner(), recentMeals -> {
            if (recentMeals != null) {
                mealAdminAdapter.updateMeals(recentMeals); // Update data instead of recreating adapter
            }
        });




        addMealButton.setOnClickListener(v -> {
            DialogCreateMeal dialogCreateMeal = new DialogCreateMeal();
//            dialogCreateMeal.setSexSelectionListener(age -> {
//                ageSelector.setSelectValue(age + "years");
//                continueButton.setEnabled(isFilled());
//                ageInfo.set(age);
//            });
            dialogCreateMeal.show(getParentFragmentManager(), "CreateMealDialog");
        });



    }
}
