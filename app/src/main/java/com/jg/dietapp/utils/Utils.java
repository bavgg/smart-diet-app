package com.jg.dietapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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


    public static Bitmap loadImageFromInternalStorage(Context context, String imageFolder, String filename) {
        String imagePath = new File(context.getFilesDir(), imageFolder + "/" + filename).getAbsolutePath();
        return BitmapFactory.decodeFile(imagePath);
    }

    public static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static   void loadImagesFromAssetToInternalStorage(Context context) {
        executor.execute(() -> {
            try {
                String[] assetFiles = context.getAssets().list(""); // Root of assets folder
                if (assetFiles != null) {
                    for (String file : assetFiles) {
                        if (file.toLowerCase().endsWith(".jpg")) { // Filter only .jpg files
                            System.out.println("JPG File: " + file);
                            copyAssetToInternalStorage(context, "images", file);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void copyAssetToInternalStorage(Context context, String folderName, String assetFileName) {
        File directory = new File(context.getFilesDir(), folderName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

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

    public static String getImageFile(Context context, String imageName) {
        String folderName = "images";
        File directory = new File(context.getFilesDir(), folderName);
        File imageFile = new File(directory, imageName);
        return imageFile.exists() ? imageFile.getAbsolutePath() : null;
    }

    public static String saveUserImageToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        try {
            // Get internal storage directory
            File directory = new File(context.getFilesDir(), "images");

            // Create the directory if it doesn't exist
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create file inside the subfolder
            File file = new File(directory, fileName);

            // Write bitmap to file
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            return file.getAbsolutePath(); // Return saved file path
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




//    String imagePath = new File(context.getFilesDir(), "images/meal1.jpg").getAbsolutePath();
//    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//    imageView.setImageBitmap(bitmap);





}
