package com.jg.dietapp.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jg.dietapp.R;
import com.jg.dietapp.adapters.MealAdapter;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.models.Meal;

import java.util.List;

public class FragmentMeals extends Fragment {
    RecyclerView recyclerViewMeals;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        List<Meal> meals = DAOMeal.ge


        recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);

        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewMeals.setAdapter(new MealAdapter());


    }
}
