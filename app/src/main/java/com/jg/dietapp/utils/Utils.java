package com.jg.dietapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class Utils {
    public static double lbsToKg(double lbs) {
        return lbs * 0.453592;
    }

    public static String cmToFeetInches(int cm) {
        double inches = cm / 2.54;
        int feet = (int) (inches / 12);
        int remainingInches = (int) (inches % 12);

        return feet + " feet " + remainingInches + " inches";
    }



    public static final Logger LOGGER = Logger.getLogger(Utils.class.getName());


    public static Bitmap loadImageFromAssets(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    imageView.setImageBitmap(loadImageFromAssets("meal1.jpg"));


    public static void copyAssetToInternalStorage(Context context, String folderName, String assetFileName) {
        // Create a directory inside internal storage
        File directory = new File(context.getFilesDir(), folderName);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Define the destination file inside the created directory
        File file = new File(directory, assetFileName);

        if (!file.exists()) {
            try (InputStream in = context.getAssets().open(assetFileName);
                 OutputStream out = new FileOutputStream(file)) {

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadImageFromInternalStorage(Context context, String imageFolder, String filename) {
        String imagePath = new File(context.getFilesDir(), imageFolder + "/" + filename).getAbsolutePath();
        return BitmapFactory.decodeFile(imagePath);
    }


//    String imagePath = new File(context.getFilesDir(), "images/meal1.jpg").getAbsolutePath();
//    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//    imageView.setImageBitmap(bitmap);





}
