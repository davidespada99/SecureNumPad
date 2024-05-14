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

    public Login(){}

    public Login(ArrayList<String> loginString){
        this.ID = Integer.parseInt(loginString.get(0));
        this.userPINID = Integer.parseInt(loginString.get(1));
        this.attempt = Integer.parseInt(loginString.get(2));
        this.buttonID = Integer.parseInt(loginString.get(3));
        this.getSize = Double.parseDouble(loginString.get(4));
    }

    public Login(Login login){
        this.ID = login.getID();
        this.userPINID = login.getUserPINID();
        this.attempt = login.getAttempt();
        this.buttonID = login.getButtonID();
        this.getSize = login.getGetSize();
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

    public static void setFilename(String filename) {
        Login.filename = filename;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserPINID() {
        return userPINID;
    }

    public void setUserPINID(int userPINID) {
        this.userPINID = userPINID;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int getButtonID() {
        return buttonID;
    }

    public void setButtonID(int buttonID) {
        this.buttonID = buttonID;
    }

    public double getGetSize() {
        return getSize;
    }

    public void setGetSize(double getSize) {
        this.getSize = getSize;
    }
}
