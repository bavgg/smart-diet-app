package com.jg.dietapp.utils;

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


}
