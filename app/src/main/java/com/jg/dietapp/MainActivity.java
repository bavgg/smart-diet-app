package com.jg.dietapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {
    private int currentIndex = 0;
    private final Fragment[] fragments = {new StartFragment(), new GoalFragment(), new AchieveFragment(), new AboutYouFragment()};
    private int progress = 0;
    public static SharedViewModel sharedViewModel = new SharedViewModel();
    LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressIndicator = findViewById(R.id.progressIndicator);
//        progressIndicator.setProgress(25);

//        Button nextButton = findViewById(R.id.nextButton);
        // Load the first fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragments[currentIndex])
                .commit();
//        MaterialButton toggleButton = findViewById(R.id.toggle_button);
//
//        toggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isSelected = toggleButton.isSelected();
//                toggleButton.setSelected(!isSelected);
//
//                // Change appearance based on selection
//                if (toggleButton.isSelected()) {
//                    System.out.println("Checked");
////                    toggleButton.setBackgroundColor(getResources().getColor(R.color.sky));
//                    toggleButton.setText("Selected");
//                } else {
////                    toggleButton.setBackgroundColor(getResources().getColor(R.color.white));
//                    toggleButton.setText("Select Me");
//                }
//            }
//        });
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                BottomSheetDialogFragment bottomSheet = new BottomSheetDialogFragment();
////                bottomSheet.show(getSupportFragmentManager(), "BottomSheetDialogFragment");
//                if(fragments.length - 1 > currentIndex) {
//                    currentIndex++;
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, fragments[currentIndex])
//                            .commit();
//                }
//
//            }
//        } );
    }

    public void updateProgress(int value) {
        progress += value;
        progressIndicator.setProgress(progress);
    }
}