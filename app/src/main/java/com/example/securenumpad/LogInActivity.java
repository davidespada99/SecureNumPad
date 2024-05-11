package com.example.securenumpad;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LogInActivity extends AppCompatActivity {

    private ArrayList<Double> sizes;
    double loginMean;
    double loginVar;
    private int userID;
    private boolean isRealUser;
    private UserLoginStat userLoginStat;
    private UserRegistrationStat userRegistrationStat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        FunctionHelperActivity.initializeButtons(rootLayout);

        userID = FunctionHelperActivity.getCurrentUserId();
        isRealUser = FunctionHelperActivity.isIsRealUser();

        //Retrieve UserRegistrationStat and UserLoginStat

        sizes = new ArrayList<>();


        try {
            ArrayList<String> userRegistrationRecord = parseCSV("UserRegistrationStats.csv", userID);
            userRegistrationStat = new UserRegistrationStat(userRegistrationRecord);

            userLoginStat = CSVHandler.FromCSVReaderToUserLoginStat(userID);
            Log.d("CSVHandler.FromCSVReaderToUserLoginStat returned ", "onCreate: " + userLoginStat.getID() + " " + userLoginStat.getConfusionMatrix());
            CSVHandler.CSVUpdateUserLoginStats(userLoginStat);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //////////// temp code to move //////////////
        EditText displayData = findViewById(R.id.display_data);
        String text = "Insert the following PIN " + userRegistrationStat.getPIN();
        displayData.setText(text);


    }



    //HANDLE BUTTON CLICKED
    public void onButtonClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        //display_text box
        View displayTextView = findViewById(R.id.display_pin);
        EditText displayText = (EditText) displayTextView;

        //button
        Button b = (Button) v;
        String textToWrite = String.valueOf(displayText.getText()) + String.valueOf(b.getText());

        //We inserted the PIN
        if(textToWrite.length() >= 4 ){
            textToWrite = "";
            FunctionHelperActivity.initializeButtons(rootLayout);

            //DOBBIAMO FARE IL CHECK DEI VALORI E AGGIORNARE IL FILE UserLoginStats;
            loginMean = FunctionHelperActivity.Mean(sizes);
            loginVar = FunctionHelperActivity.Var(sizes);

            ArrayList<Boolean> isLoginPermitted = InformationChecker.checkLoginInfo(loginMean, loginVar, userRegistrationStat);

            Log.d("isLoginPermitted", "onClicked: " + isLoginPermitted);

            //With this array, we have to update the user confusion matrixes
            userLoginStat.updateConfusionMatrix(isLoginPermitted, isRealUser);

            //Remember that the order is TPx FPx TNx FNx
            CSVHandler.CSVUpdateUserLoginStats(userLoginStat);


        }

        displayText.setText(textToWrite);
    }

    //HANDLE DISPLAY TOUCH AND GETSIZE
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        String toLog = "";

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double size = event.getSize();
            toLog = String.valueOf(size) + ",";
            FunctionHelperActivity.setLogDataToCSV(FunctionHelperActivity.getLogDataToCSV() + toLog);
            sizes.add(size);

        }

        return super.dispatchTouchEvent(event);
    }


    // METHOD TO GET LOGIN AND REGISTRATION USER INFO
    private UserLoginStat getUserLoginStat(String filename, int userID) throws IOException {
        int l;
        //Retrieve information from CSV
        ArrayList<String> userInfoRecord = CSVHandler.CSVReader(filename, userID);
        ArrayList<Integer> confsionMatrixes = new ArrayList<>();

        //SET PIN TO INSERT (MAYBE CAN BE MOVED)
        for (int i = 1; i < userInfoRecord.size(); i ++){
            confsionMatrixes.add(Integer.parseInt(userInfoRecord.get(i)));
        }
        return new UserLoginStat(Integer.parseInt(userInfoRecord.get(0)), confsionMatrixes);
    }


    // METHOD TO PARSE CSV

    private ArrayList<String> parseCSV(String filename, int userID) throws IOException {
            ArrayList<String> userRecord = null;
            InputStream inputStream = null;
            File file;
            try{
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filename);
                inputStream = new FileInputStream(file);

                Reader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while((line = bufferedReader.readLine()) != null){
                    String[] tokens = line.split(",");
                    if(tokens.length > 1 && tokens[0].equals(String.valueOf(userID))){
                        userRecord = new ArrayList<>(Arrays.asList(tokens));
                        break;
                    }
             ;;   }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (inputStream != null){
                    inputStream.close();
                }
            }
            return userRecord;
    }


}
