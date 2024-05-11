package com.example.securenumpad;

import java.util.ArrayList;

public class InformationChecker {
    public static ArrayList<Boolean> checkLoginInfo(double loginMean,double loginVar, UserRegistrationStat userRegistrationStat) {

        ArrayList<Boolean> loginsResults = new ArrayList<>();

        ArrayList<Double> means = userRegistrationStat.getMeans();
        ArrayList<Double> vars = userRegistrationStat.getVars();

        for(int i=0; i<means.size(); i++){
            loginsResults.add(LoginResult(loginMean, loginVar, means.get(i), vars.get(i)));
        }


        return loginsResults;
    }

    private static boolean LoginResult(double loginMean, double loginVar, double regMean, double regVar){
        boolean permitted = false;

        if( loginMean == regMean && loginVar == regVar ) permitted = true;

        return permitted;
    }


}