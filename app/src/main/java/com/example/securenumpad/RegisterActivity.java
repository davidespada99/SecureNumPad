package com.example.securenumpad;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private final int NUMBEROFREGISTRATION = 10;
    private int currentNumberOfRegistration = 0;
    String randomPIN = FunctionHelperActivity.randomPIN();
    private int currentUserId = FunctionHelperActivity.getCurrentUserId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);
        ViewGroup rootLayout = findViewById(android.R.id.content);
        FunctionHelperActivity.initializeButtons(rootLayout);
        View displayDataView = findViewById(R.id.display_data);
        EditText displayData = (EditText) displayDataView;
        View displayPinView = findViewById(R.id.display_pin);
        EditText displayPin = (EditText) displayPinView;
        updatePinData(displayData, displayPin);
    }



    String logData = "";
    public void onButtonClicked(View v) throws IOException {
        ViewGroup rootLayout = findViewById(android.R.id.content);

        //button
        Button button = (Button) v;
        String buttonId = String.valueOf(button.getId());
        String buttonNumber = (String) button.getText();
        Log.d("button_ID --> ButtonNumber", buttonId + " --> " + buttonNumber);

        //Update Pin digits in display_pin
        View displayPinView = findViewById(R.id.display_pin);
        EditText displayPin = (EditText) displayPinView;

        //update Data Text View
        View displayDataView = findViewById(R.id.display_data);
        EditText displayData = (EditText) displayDataView;


        updateDisplayPin(displayPin, buttonNumber);
        updatePinData(displayData, displayPin);


        logData += String.valueOf(currentUserId) + ","
                + buttonId + ","
                + buttonNumber + "\n";
        Log.i("logdata", logData);

        //write in csv only if right pin!
        if(displayPin.getText().length()==4 && displayPin.getText().toString().equals(randomPIN)) {
            //Log data in csv
            FunctionHelperActivity.csvWriter(logData);
            Log.i("logdata", "written");
            logData = "";

            //Update display data
            updateAttempts(displayData);
        } else if (displayPin.getText().length()==4 && !displayPin.getText().toString().equals(randomPIN)) {
            Log.i("logdata", "NOT written");
            logData = "";
        }
    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!

    public void updatePinData(EditText displayData, EditText displayPin){
        if(currentNumberOfRegistration < NUMBEROFREGISTRATION) {
            String text = "";
            text = "Insert the following PIN " + String.valueOf(NUMBEROFREGISTRATION) + " times: \n" + String.valueOf(randomPIN);
            displayData.setText(text);

            if (displayPin.getText().length() == 4 && displayPin.getText().toString().equals(randomPIN)) {
                //Display error message
                Toast.makeText(getApplicationContext(), "Right PIN! ", Toast.LENGTH_SHORT).show();
                currentNumberOfRegistration++;

            } else if (displayPin.getText().length() == 4 && !displayPin.getText().toString().equals(randomPIN)) {
                //Display error message
                Toast.makeText(getApplicationContext(), "Wrong PIN! ", Toast.LENGTH_SHORT).show();
                //Delete last 4 lines from csv;
            }
        }
    }
    private void updateAttempts(EditText displayData) {

        String text = "Insert the following PIN "+ String.valueOf(currentNumberOfRegistration) +" times: \n" + String.valueOf(randomPIN);
        displayData.setText(text);
    }

    public void updateDisplayPin(EditText displayPin, String buttonNumber) {
        String pinToWrite = String.valueOf(displayPin.getText()) + buttonNumber;
        //if pinView > 4 --> reset to the current number pressed
        if(pinToWrite.length() > 4 ) pinToWrite = buttonNumber;

        displayPin.setText(pinToWrite);

    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        String getSize = "";

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            getSize = event.getSize() + ",";
            logData += getSize;
            Log.i("logdata_dispatch", logData);
        }

        return super.dispatchTouchEvent(event);
    }



}
