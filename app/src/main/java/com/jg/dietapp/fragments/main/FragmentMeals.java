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
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.dialogs.DialogCreateMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.viewmodel.RecentMealsViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentMeals extends Fragment {
    RecyclerView recyclerViewMeals;
    private MealAdminAdapter mealAdminAdapter;
    Button addMealButton;
    DAOMeal mealDAO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
        mealDAO = new DAOMeal(dbHelper);

        // Set recent meals data
        List<Meal> recentMeals = mealDAO.getRecentMeals();
        RecentMealsViewModel recentMealsViewModel = new ViewModelProvider(this).get(RecentMealsViewModel.class);
        recentMealsViewModel.setMeals(recentMeals);

        addMealButton = view.findViewById(R.id.addMealButton);



        // Fetch meals from ViewModel

// Initialize RecyclerView
        recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));

// Initialize Adapter with an empty list
        mealAdminAdapter = new MealAdminAdapter(new ArrayList<>());
        recyclerViewMeals.setAdapter(mealAdminAdapter);

// Observe LiveData and update adapter
        recentMealsViewModel.getMeals().observe(getViewLifecycleOwner(), rMeals -> {
            if (rMeals != null) {
                mealAdminAdapter.updateMeals(rMeals); // Update data instead of recreating adapter
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
