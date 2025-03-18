package com.jg.dietapp.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.jg.dietapp.R;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.utils.Utils;
import com.jg.dietapp.viewmodel.RecentMealsViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DialogCreateMeal extends BottomSheetDialogFragment {

    private ImageView mealImageView;
    private Button createMealButton, uploadButton;
    private TextInputEditText mealNameText, caloriesText, proteinText, carbsText, fatsText, servingsText, prepTimeText;
    private RadioGroup dietTypeRadioGroup, cultureRadioGroup, regionRadioGroup, mealTimeRadioGroup;
    private TextView fileNameText;
    private CheckBox checkboxCoconut, checkboxShellfish, checkboxNuts, checkboxDairy, checkboxGluten, checkboxPeanuts, checkboxSoy, checkboxEgg, checkboxFish;

    private Bitmap selectedImageBitmap;  // Store the bitmap
    private String imageFileName;
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);

                        // Extract the file name
                        imageFileName = getFileName(imageUri);
                        imageFileName = formatFileName(imageFileName);

                        fileNameText.setText(imageFileName);

                        checkFields();



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uploadButton = view.findViewById(R.id.uploadButton);
        createMealButton = view.findViewById(R.id.createMealButton);
        mealNameText = view.findViewById(R.id.mealNameText);
        caloriesText = view.findViewById(R.id.caloriesText);
        proteinText = view.findViewById(R.id.proteinText);
        carbsText = view.findViewById(R.id.carbsText);
        fatsText = view.findViewById(R.id.fatsText);
        servingsText = view.findViewById(R.id.servingsText);
        prepTimeText = view.findViewById(R.id.prepTimeText);
        dietTypeRadioGroup = view.findViewById(R.id.dietRadioGroup);
        cultureRadioGroup = view.findViewById(R.id.cultureRadioGroup);
        regionRadioGroup = view.findViewById(R.id.regionRadioGroup);
        mealTimeRadioGroup = view.findViewById(R.id.mealTimeRadioGroup);
        fileNameText = view.findViewById(R.id.fileNameText);
        checkboxCoconut = view.findViewById(R.id.checkbox_coconut);
        checkboxShellfish = view.findViewById(R.id.checkbox_shellfish);
        checkboxNuts = view.findViewById(R.id.checkbox_nuts);
        checkboxDairy = view.findViewById(R.id.checkbox_dairy);
        checkboxGluten = view.findViewById(R.id.checkbox_gluten);
        checkboxPeanuts = view.findViewById(R.id.checkbox_peanuts);
        checkboxSoy = view.findViewById(R.id.checkbox_soy);
        checkboxEgg = view.findViewById(R.id.checkbox_egg);
        checkboxFish = view.findViewById(R.id.checkbox_fish);


        TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        mealNameText.addTextChangedListener(inputWatcher);
        caloriesText.addTextChangedListener(inputWatcher);
        proteinText.addTextChangedListener(inputWatcher);
        carbsText.addTextChangedListener(inputWatcher);
        fatsText.addTextChangedListener(inputWatcher);
        servingsText.addTextChangedListener(inputWatcher);
        prepTimeText.addTextChangedListener(inputWatcher);

        dietTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());
        cultureRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());
        regionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());
        mealTimeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> checkFields());

        uploadButton.setOnClickListener(v -> {

            openGallery();

        });
        createMealButton.setOnClickListener(v -> createMeal(view));

        checkFields(); // Initial check
    }

    private void checkFields() {
        String fileName = Objects.requireNonNull(fileNameText.getText()).toString().trim();

        boolean allFieldsFilled = !Objects.requireNonNull(mealNameText.getText()).toString().trim().isEmpty() &&
                !Objects.requireNonNull(caloriesText.getText()).toString().trim().isEmpty() &&
                !Objects.requireNonNull(proteinText.getText()).toString().trim().isEmpty() &&
                !Objects.requireNonNull(carbsText.getText()).toString().trim().isEmpty() &&
                !Objects.requireNonNull(fatsText.getText()).toString().trim().isEmpty() &&
                !Objects.requireNonNull(servingsText.getText()).toString().trim().isEmpty() &&
                !Objects.requireNonNull(prepTimeText.getText()).toString().trim().isEmpty() &&
                dietTypeRadioGroup.getCheckedRadioButtonId() != -1 &&
                cultureRadioGroup.getCheckedRadioButtonId() != -1 &&
                regionRadioGroup.getCheckedRadioButtonId() != -1 &&
                mealTimeRadioGroup.getCheckedRadioButtonId() != -1 &&
                fileName.toLowerCase().endsWith(".jpg"); // Check for ".jpg"

        System.out.println(fileName);
        System.out.println(allFieldsFilled);
        createMealButton.setEnabled(allFieldsFilled);

        if (allFieldsFilled) {
            createMealButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.primary));
            createMealButton.setTextColor(Color.WHITE);
        } else {
            createMealButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.gray)); // Optional: Change color when disabled
            createMealButton.setTextColor(Color.DKGRAY);
        }
    }


    private String getFoodAllergens() {
        AtomicReference<String> foodAllergens = new AtomicReference<>("");
        if (checkboxCoconut.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Coconut");
        }
        if (checkboxShellfish.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Shellfish");
        }
        if (checkboxNuts.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Nuts");
        }
        if (checkboxDairy.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Dairy");
        }
        if (checkboxGluten.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Gluten");
        }
        if (checkboxPeanuts.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Peanuts");
        }
        if (checkboxSoy.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Soy");
        }
        if (checkboxEgg.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Egg");

        }
        if (checkboxFish.isChecked()) {
            foodAllergens.updateAndGet(value -> value + ",Fish");
        }
        return foodAllergens.get();

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void createMeal(View view) {
// Get input from EditText fields
        String mealName = Objects.requireNonNull(mealNameText.getText()).toString().trim();
        mealName = mealName.substring(0, 1).toUpperCase() + mealName.substring(1).toLowerCase();
        double calories = Double.parseDouble(Objects.requireNonNull(caloriesText.getText()).toString().trim());
        int protein = Integer.parseInt(Objects.requireNonNull(proteinText.getText()).toString().trim());
        int carbs = Integer.parseInt(Objects.requireNonNull(carbsText.getText()).toString().trim());
        int fats = Integer.parseInt(Objects.requireNonNull(fatsText.getText()).toString().trim());
        int servings = Integer.parseInt(Objects.requireNonNull(servingsText.getText()).toString().trim());
        int prepTime = Integer.parseInt(Objects.requireNonNull(prepTimeText.getText()).toString().trim());

// Convert selected RadioButton IDs to meaningful String values
        String dietType = getSelectedRadioButtonText(dietTypeRadioGroup, view);
        String culture = getSelectedRadioButtonText(cultureRadioGroup, view);
        String region = getSelectedRadioButtonText(regionRadioGroup, view);
        String mealtime = getSelectedRadioButtonText(mealTimeRadioGroup, view);

// Get allergens from checkboxes or other UI elements
        String foodAllergens = getFoodAllergens();

// Create a new Meal object
        Meal meal = new Meal(mealName, calories, protein, carbs, fats, dietType, foodAllergens, prepTime, culture, region, servings, mealtime);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        DAOMeal mealDAO = new DAOMeal(dbHelper);

        Meal addedMeal = mealDAO.insertMeal(getContext(), meal);
        // Save the image to internal storage
        Utils.saveUserImageToInternalStorage(requireContext(), selectedImageBitmap, meal.getImageName());

        // Example logic to store meal (Modify as per requirement)
        System.out.println("Meal Created: " + mealName + ", Calories: " + calories);

        // update the UI with the newly created meal

        RecentMealsViewModel recentMealsViewModel = new ViewModelProvider(requireActivity()).get(RecentMealsViewModel.class);
        recentMealsViewModel.addMeal(addedMeal);

        // Dismiss the dialog after meal creation
        dismiss();
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup, View view) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = view.findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return "Unknown"; // Default value if nothing is selected
    }


    private String formatFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName; // Return as is if empty or null
        }
        fileName = fileName.toLowerCase(); // Convert all to lowercase
        return Character.toUpperCase(fileName.charAt(0)) + fileName.substring(1);
    }
}
