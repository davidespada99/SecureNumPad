package com.example.securenumpad;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CSVHelper {


    public static void createTable(String tableCode) {
        String filename = "";
        String header = "";

        switch (tableCode) {
            case "User":
                filename = User.getFilename();
                header = User.getHeader();
                break;
            case "Registration":
                filename = Registration.getFilename();
                header = Registration.getHeader();
                break;
            case "Login":
                filename = Login.getFilename();
                header = Login.getHeader();
                break;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filename);

        try {
            if (!file.exists()) {
                Log.d("FILEISFILE", "createTable: " + file.exists());
                file.createNewFile();
                try (FileWriter fw = new FileWriter(file.getAbsoluteFile());
                     BufferedWriter bw = new BufferedWriter(fw)) {
                    Log.d("FILEISFILE", "writing header: " + header);
                    bw.write(header);
                    if(tableCode.equals("User")){
                        ArrayList<User> users = User.createRandomUserPINTable(20, 4);
                        for (User user: users){
                            bw.write(user.fromObjectToCSVRow());
                        }
                    }

                }
            } else {
                Log.d("FILEISFILE", "File already exists: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception here if needed
        }
    }



    public static void UserToCSV(ArrayList<User> users, boolean append) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), User.getFilename());
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), append);;
        BufferedWriter bw = new BufferedWriter(fw);

        for (User user: users){
            bw.write(user.fromObjectToCSVRow());
        }
        bw.close();
    }

    public static void RegistrationToCSV(ArrayList<Registration> registrations, boolean append) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), Registration.getFilename());
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), append);;
        BufferedWriter bw = new BufferedWriter(fw);

        for (Registration registration: registrations){
            Log.d("RegistrationToCSV", "RegistrationToCSV writing " + registration.fromObjectToCSVRow());
            bw.write(registration.fromObjectToCSVRow());
        }
        bw.close();
    }

    public static void LoginToCSV(ArrayList<Login> logins, boolean append) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), Login.getFilename());
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), append);;
        BufferedWriter bw = new BufferedWriter(fw);

        for (Login login: logins){
            bw.write(login.fromObjectToCSVRow());
        }
        bw.close();
    }


    public static ArrayList<User> CSVToUser() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), User.getFilename());

        // Check if file exists
        if (!file.exists()) {
            // Handle file not found case (e.g., log error, return empty list)
            Log.e("CSVToUser", "File not found: " + file.getAbsolutePath());
            return users;
        }

        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);

            br.readLine(); // Skip header row
            String row;
            while ((row = br.readLine()) != null) {
                String[] tokens = row.split(",");
                ArrayList<String> userString = new ArrayList<>(Arrays.asList(tokens));
                users.add(new User(userString));
            }

            br.close();
        } catch (IOException e) {
            // Handle general IO exceptions (e.g., log error, return empty list)
            Log.e("CSVToUser", "Error reading CSV file: " + e.getMessage());
        }

        return users;
    }


    public static ArrayList<Registration> CSVToRegistration() {
        ArrayList<Registration> registrations = new ArrayList<>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), Registration.getFilename());

        // Check if file exists
        if (!file.exists()) {
            // Handle file not found case (e.g., log error, return empty list)
            Log.e("CSVToRegistrations", "File not found: " + file.getAbsolutePath());
            return registrations;
        }

        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);

            br.readLine(); // Skip header row
            String row;
            while ((row = br.readLine()) != null) {
                String[] tokens = row.split(",");
                ArrayList<String> registrationString = new ArrayList<>(Arrays.asList(tokens));
                registrations.add(new Registration(registrationString));
            }

            br.close();
        } catch (IOException e) {
            // Handle general IO exceptions (e.g., log error, return empty list)
            Log.e("CSVToUser", "Error reading CSV file: " + e.getMessage());
        }

        return registrations;
    }

    public static ArrayList<Login> CSVToLogin() {
        ArrayList<Login> logins = new ArrayList<>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), Login.getFilename());

        // Check if file exists
        if (!file.exists()) {
            // Handle file not found case (e.g., log error, return empty list)
            Log.e("CSVToRegistrations", "File not found: " + file.getAbsolutePath());
            return logins;
        }

        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);

            br.readLine(); // Skip header row
            String row;
            while ((row = br.readLine()) != null) {
                String[] tokens = row.split(",");
                ArrayList<String> loginString = new ArrayList<>(Arrays.asList(tokens));
                logins.add(new Login(loginString));
            }

            br.close();
        } catch (IOException e) {
            // Handle general IO exceptions (e.g., log error, return empty list)
            Log.e("CSVToUser", "Error reading CSV file: " + e.getMessage());
        }

        return logins;
    }





}
