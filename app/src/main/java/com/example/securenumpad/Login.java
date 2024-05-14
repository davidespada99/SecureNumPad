package com.example.securenumpad;

import android.util.Log;

import java.util.ArrayList;

public class Login {

    static String filename = "Login.csv";

    int ID;

    int userPINID;

    int attempt;

    int buttonID;

    double getSize;

    public Login(ArrayList<String> loginString){
        this.ID = Integer.parseInt(loginString.get(0));
        this.userPINID = Integer.parseInt(loginString.get(1));
        this.attempt = Integer.parseInt(loginString.get(2));
        this.buttonID = Integer.parseInt(loginString.get(3));
        this.getSize = Double.parseDouble(loginString.get(4));
    }


    public static String getFilename() {
        return filename;
    }

    public static String getHeader() {
        return "ID, userPINID, attempt, buttonID, getSize\n";
    }

    public String fromObjectToCSVRow(){
        return ID + "," + userPINID + "," + attempt + "," + buttonID + "," + getSize + "\n";
    }


}
