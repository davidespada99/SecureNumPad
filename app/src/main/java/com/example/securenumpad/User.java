package com.example.securenumpad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class User{

    static String filename = "User.csv";

    boolean isRealUser = true;
    int ID;
    String PIN;

    public User(int ID, String PIN) {
        this.ID = ID;
        this.PIN = PIN;
    }

    public User(ArrayList<String> userString) {
        this.ID = Integer.parseInt(userString.get(0));
        this.PIN = userString.get(1);;
    }

    int getID() {
        return this.ID;
    }


    String getPIN() {
        return this.PIN;
    }

    public void setIsRealUser(boolean val){
        this.isRealUser = val;
    }

    public String fromObjectToCSVRow(){
        return ID + "," + PIN+ "\n";
    }

    public static ArrayList<User> createRandomUserPINTable(int nUsers, int pinLength){
        if (nUsers <= 0 || pinLength <= 0) {
            throw new IllegalArgumentException("nUsers and pinLength must be positive integers.");
        }
        ArrayList<User> Users = new ArrayList<>();
        String validChars = "0123456789"; // Only digits for PINs

        for (int i = 1; i <= nUsers; i++) {
            String pin;
            do {
                pin = generateRandomPIN(pinLength, validChars);
            } while (isDuplicatePIN(Users, pin)); // Ensure unique PINs

            Users.add(new User(i, pin));
        }

        return Users;

    }






    public static String getHeader(){
        return "ID, PIN\n";
    }

    public static String getFilename(){
        return filename;
    }








    private static String generateRandomPIN(int length, String validChars) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(validChars.length());
            sb.append(validChars.charAt(index));
        }
        return sb.toString();
    }

    private static boolean isDuplicatePIN(ArrayList<User> users, String pin) {
        for (User user : users) {
            if (user.getPIN().equals(pin)) {
                return true;
            }
        }
        return false;
    }


    public static User getUserByID(ArrayList<User> users, int ID){
        for(User user : users) {
            if (user.getID() == ID) return user;
        }
        return null;
    }


}
