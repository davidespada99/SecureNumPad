package com.example.securenumpad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private int userIndex = 0;

    private Login currentLogin = new Login();

    private int currentAttempt = 1;

    private int MAXATTEMPTS = 4;

    private ArrayList<Login> tmpLogins;
    private ArrayList<User> users;

    String PIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        FunctionHelperActivity.initializeButtons(rootLayout);

        userID = FunctionHelperActivity.getCurrentUserId();
        isRealUser = FunctionHelperActivity.isIsRealUser();
        tmpLogins = new ArrayList<>();
        //Retrieve UserRegistrationStat and UserLoginStat

        users = CSVHelper.CSVToUser();
        PIN = users.get(userIndex).getPIN();
        currentLogin.setID(HomeActivity.currentSessionUser.getID());
        currentLogin.setUserPINID(userIndex+1);
        currentLogin.setAttempt(currentAttempt);
        updatePinData();
    }



    //HANDLE BUTTON CLICKED
    public void onButtonClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        //display_text box
        View displayTextView = findViewById(R.id.display_pin);
        EditText displayPin = (EditText) displayTextView;

        //button
        Button b = (Button) v;
        String buttonNumber = (String) b.getText();

        currentLogin.setButtonID(Integer.parseInt(buttonNumber));


        tmpLogins.add(new Login(currentLogin));

        String textToWrite = String.valueOf(displayPin.getText()) + String.valueOf(b.getText());







        //We inserted the PIN
        if(textToWrite.length() >= 4 ){

            if(textToWrite.equals(PIN)) {

                CSVHelper.LoginToCSV(tmpLogins, true);

                currentAttempt++;
                if (currentAttempt > MAXATTEMPTS) {
                    userIndex++;
                    if (userIndex >= users.size()) {
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        startActivity(intent);

                        userIndex=0; // !!!!!! DON'T DELETE THIS !!!!!
                    }
                    PIN = users.get(userIndex).getPIN();
                    currentAttempt = 1;
                    currentLogin.setUserPINID(userIndex + 1);
                }


                currentLogin.setAttempt(currentAttempt);
            }

            updatePinData();
            tmpLogins.clear();
            textToWrite = "";
        }




        displayPin.setText(textToWrite);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        String toLog = "";

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double size = event.getSize();
            toLog = String.valueOf(size) + ",";
            FunctionHelperActivity.setLogDataToCSV(FunctionHelperActivity.getLogDataToCSV() + toLog);
            currentLogin.setGetSize(size);

        }

        return super.dispatchTouchEvent(event);
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


    public void  updatePinData(){
        EditText displayData = (EditText) findViewById(R.id.display_data);
        EditText displayPin = (EditText) findViewById(R.id.display_pin);

        String text = "";
        text = "Insert the following PIN "+ String.valueOf(MAXATTEMPTS-(currentAttempt-1)) +" times: \n" + String.valueOf(PIN);
        displayData.setText(text);

        if (displayPin.getText().length() == 4 && displayPin.getText().toString().equals(PIN)) {
            //Display error message
            Toast.makeText(getApplicationContext(), "Right PIN! ", Toast.LENGTH_SHORT).show();

        } else if (displayPin.getText().length() == 4 && !displayPin.getText().toString().equals(PIN)) {
            //Display error message
            Toast.makeText(getApplicationContext(), "Wrong PIN! ", Toast.LENGTH_SHORT).show();
            //Delete last 4 lines from csv;
        }
    }


}
