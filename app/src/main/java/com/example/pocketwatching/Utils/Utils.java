package com.example.pocketwatching.Utils;

public class Utils {
    public static String getString(double input){

        Long number = (long) input;

        if(number >= 1000000000000L){
            return String.format("%.2fT", number/ (float) 1000000000000L);
        }

        if(number >= 1000000000){
            return String.format("%.2fB", number/ 1000000000.0);
        }

        if(number >= 1000000){
            return String.format("%.2fM", number/ 1000000.0);
        }

        if(number >= 100000){
            return String.format("%.2fL", number/ 100000.0);
        }

        if(number >=1000){
            return String.format("%.2fK", number/ 1000.0);
        }

        return String.format("%,.2f", input);
    }
}
