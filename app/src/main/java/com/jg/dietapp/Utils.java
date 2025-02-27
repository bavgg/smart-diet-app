package com.jg.dietapp;

import java.util.logging.Logger;

public class Utils {
    public static double lbsToKg(double lbs) {
        return lbs * 0.453592;
    }

    public static final Logger LOGGER = Logger.getLogger(Utils.class.getName());


}
