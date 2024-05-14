package com.example.securenumpad;

import java.util.ArrayList;

public class InformationChecker {

    private static boolean LoginResult(double loginMean, double loginVar, double regMean, double regVar){
        boolean permitted = false;

        if( loginMean == regMean && loginVar == regVar ) permitted = true;

        return permitted;
    }


}