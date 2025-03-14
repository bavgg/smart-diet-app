package com.jg.dietapp.fragments.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jg.dietapp.R;
import com.jg.dietapp.adapters.MealAdapter;
import com.jg.dietapp.adapters.MealAdminAdapter;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.viewmodel.AllMealsViewModel;
import com.jg.dietapp.viewmodel.ImageBitmapsViewModel;

import java.util.List;

public class FragmentMeals extends Fragment {
    RecyclerView recyclerViewMeals;
    private MealAdminAdapter mealAdminAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("Fragment Meals exec");

        // Fetch meals from ViewModel
        AllMealsViewModel allMealsViewModel = new ViewModelProvider(requireActivity()).get(AllMealsViewModel.class);
        List<Meal> allMeals = allMealsViewModel.getMeals();

        // Fetch image bitmaps from ViewModel
        ImageBitmapsViewModel imageBitmapsViewModel = new ViewModelProvider(requireActivity()).get(ImageBitmapsViewModel.class);
        List<Bitmap> imageBitmaps = imageBitmapsViewModel.getImageBitmaps();

        // Set meal admin adapter
        mealAdminAdapter = new MealAdminAdapter(allMeals, imageBitmaps);
        recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMeals.setAdapter(mealAdminAdapter);


    }
}
