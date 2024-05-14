package com.example.securenumpad;

import java.util.ArrayList;

public class Registration{

    static String filename = "Registration.csv";

    int ID;

    int attempt;

    int buttonID;

    double getSize;

    public Registration(){
    }

    public Registration(Registration registration){
        this.ID = registration.getID();
        this.attempt = registration.getAttempt();
        this.buttonID = registration.getButtonID();
        this.getSize = registration.getGetSize();
    }

    public Registration(int ID, int attempt, int buttonID, double getSize) {
        this.ID = ID;
        this.attempt = attempt;
        this.buttonID = buttonID;
        this.getSize = getSize;
    }

    public Registration(ArrayList<String> registrationString) {
        this.ID = Integer.parseInt(registrationString.get(0));
        this.attempt = Integer.parseInt(registrationString.get(1));
        this.buttonID = Integer.parseInt(registrationString.get(2));
        this.getSize = Double.parseDouble(registrationString.get(3));
    }
    public static void setFilename(String filename) {
        Registration.filename = filename;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public static  String getHeader(){
        return "ID, attempt, buttonID, getSize\n";
    }

    public static String getFilename() {
        return filename;
    }

    public String fromObjectToCSVRow(){
        return ID + "," + attempt + "," + buttonID + "," + getSize + "\n";
    }

    @Override
    public String toString() {
        return "Registration{" +
                "ID=" + ID +
                ", attempt=" + attempt +
                ", buttonID=" + buttonID +
                ", getSize=" + getSize +
                '}';
    }
}
