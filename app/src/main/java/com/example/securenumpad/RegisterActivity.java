package com.example.securenumpad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    private final int NUMBEROFREGISTRATION = 10;
    private int currentNumberOfRegistration = 1;
    String PIN;
    private int currentUserId = FunctionHelperActivity.getCurrentUserId();

    private ArrayList<Integer> sampleNumbers = new ArrayList<>(Arrays.asList(1, 2, 5, 10));
    private ArrayList<Registration> tmpRegistrations;

    private static Registration currentRegistration;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FunctionHelperActivity.setCurrentInsertedPin("");
        setContentView(R.layout.activity_pad);
        ViewGroup rootLayout = findViewById(android.R.id.content);


        FunctionHelperActivity.initializeButtons(rootLayout);


        PIN = HomeActivity.currentSessionUser.getPIN();
        updatePinData();

        currentRegistration = new Registration();
        tmpRegistrations = new ArrayList<>();
    }


    public void onButtonClicked(View v) throws IOException {
        //button
        Button button = (Button) v;
        String buttonNumber = (String) button.getText();

        //Aggiorna il PIN
        FunctionHelperActivity.setCurrentInsertedPin(FunctionHelperActivity.getCurrentInsertedPin() + buttonNumber);
        //Aggiorna il display del PIN
        updateDisplayPin(buttonNumber);
        //Aggiorna il display delle Info
        updatePinData();

        currentRegistration.setID(HomeActivity.currentSessionUser.getID());
        currentRegistration.setAttempt(currentNumberOfRegistration);
        currentRegistration.setButtonID(Integer.parseInt(buttonNumber));


        tmpRegistrations.add(new Registration(currentRegistration));
        Log.d("CURRENTREGISTRATION", tmpRegistrations.toString() );


        //Handler of the PIN when reach the desired length
        if(FunctionHelperActivity.getCurrentInsertedPin().length()==4) {
            //write in csv only if right pin!
            if(FunctionHelperActivity.getCurrentInsertedPin().equals(PIN)) {
                //Log data in csv
                //FunctionHelperActivity.csvWriter();
                //sizes.addAll(tempSizes);
                CSVHelper.RegistrationToCSV(tmpRegistrations, true);

                if ( sampleNumbers.contains(currentNumberOfRegistration) ){



                }

                //Update display data
                updateAttempts();
            }

            //If we inserted NUMBEROFREGISTRATION times the correct PIN, we finished the registration and come back to the home activity
            if(currentNumberOfRegistration == NUMBEROFREGISTRATION){
                // ================================================ createUserStatsCSV(); ================================================
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);

                startActivity(intent);
            }
            FunctionHelperActivity.setCurrentInsertedPin("");
            FunctionHelperActivity.setLogDataToCSV("");
            tmpRegistrations.clear();
        }

    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!


    private void updateAttempts() {
        EditText displayData = findViewById(R.id.display_data);
        currentNumberOfRegistration++;
        String text = "Insert the following PIN "+ String.valueOf(NUMBEROFREGISTRATION-(currentNumberOfRegistration-1)) +" times: \n" + String.valueOf(PIN);
        displayData.setText(text);
    }

    public void updateDisplayPin(String buttonNumber) {
        String pinToWrite = FunctionHelperActivity.getCurrentInsertedPin();

        //if pinView > 4 --> reset to the current number pressed
        if(pinToWrite.length() > 4 ) pinToWrite = buttonNumber;

        View displayPinView = findViewById(R.id.display_pin);
        EditText displayPin = (EditText) displayPinView;
        displayPin.setText(pinToWrite);

    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!
    public void  updatePinData(){
        EditText displayData = (EditText) findViewById(R.id.display_data);
        EditText displayPin = (EditText) findViewById(R.id.display_pin);

        String text = "";
        text = "Insert the following PIN "+ String.valueOf(NUMBEROFREGISTRATION-(currentNumberOfRegistration-1)) +" times: \n" + String.valueOf(PIN);
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        String toLog = "";

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double size = event.getSize();
            toLog = String.valueOf(size) + ",";
            FunctionHelperActivity.setLogDataToCSV(FunctionHelperActivity.getLogDataToCSV() + toLog);
            currentRegistration.setGetSize(size);

        }

        return super.dispatchTouchEvent(event);
    }



}
