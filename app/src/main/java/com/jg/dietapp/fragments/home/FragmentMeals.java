package com.jg.dietapp.fragments.home;

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
import com.jg.dietapp.dialogs.DialogAge;
import com.jg.dietapp.dialogs.DialogCreateMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.viewmodel.RecentMealsViewModel;

import java.util.List;

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


        System.out.println("Fragment Meals exec");

        // Fetch meals from ViewModel
        RecentMealsViewModel allMealsViewModel = new ViewModelProvider(requireActivity()).get(RecentMealsViewModel.class);
        List<Meal> allMeals = allMealsViewModel.getMeals();
        List<Meal> subList = allMeals.subList(0, 10);

        // Fetch image bitmaps from ViewModel
//        ImageBitmapsViewModel imageBitmapsViewModel = new ViewModelProvider(requireActivity()).get(ImageBitmapsViewModel.class);
//        List<Bitmap> imageBitmaps = imageBitmapsViewModel.getImageBitmaps();

        // Set meal admin adapter
        mealAdminAdapter = new MealAdminAdapter(subList);
        recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMeals.setAdapter(mealAdminAdapter);



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
