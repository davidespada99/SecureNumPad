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
    int totalAttemps = 3;
    FunctionHelperActivity helperActivity = new FunctionHelperActivity();
    String randomPIN = helperActivity.randomPIN();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);

        FunctionHelperActivity helperActivity = new FunctionHelperActivity();

        ViewGroup rootLayout = findViewById(android.R.id.content);
        helperActivity.initializeButtons(rootLayout);

        View displayDataView = findViewById(R.id.display_data);
        EditText displayData = (EditText) displayDataView;
        View displayPinView = findViewById(R.id.display_pin);
        EditText displayPin = (EditText) displayPinView;

        updatePinData(displayData, displayPin);
    }



    String logData = "";
    public void onClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
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


        updateDisplayPin(displayPin, rootLayout, buttonNumber);
        updatePinData(displayData, displayPin);

        //Get current USer ID
        TextView userIdText = findViewById(R.id.selected_number);
        int currUserId = Integer.parseInt(String.valueOf(userIdText.getText()));


        logData += String.valueOf(currUserId) + ","
                + totalAttemps + ","
                + buttonId + ","
                + buttonNumber + "\n";
        Log.i("logdata", logData);

        //write in csv only if right pin!
        if(displayPin.getText().length()==4 && displayPin.getText().toString().equals(randomPIN)) {
            //Log data in csv
            helperActivity.csvWriter(logData);
            Log.i("logdata", "written");
            logData = "";

            //Update display data
            updateAttemps(displayData);
        } else if (displayPin.getText().length()==4 && !displayPin.getText().toString().equals(randomPIN)) {
            Log.i("logdata", "NOT written");
            logData = "";
        }
    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!

    public void updatePinData(EditText displayData, EditText displayPin){
        String text = "";
        if(totalAttemps > 0) {
            text = "Insert the following PIN " + String.valueOf(totalAttemps) + " times: \n" + String.valueOf(randomPIN);
        }
        displayData.setText(text);

        if(displayPin.getText().length()==4 && displayPin.getText().toString().equals(randomPIN)){
            //Display error message
            Toast.makeText(getApplicationContext(), "Right PIN! ", Toast.LENGTH_SHORT).show();

        } else if (displayPin.getText().length()==4 && !displayPin.getText().toString().equals(randomPIN)) {
            //Display error message
            Toast.makeText(getApplicationContext(), "Wrong PIN! ", Toast.LENGTH_SHORT).show();
            //Delete last 4 lines from csv;
        }
    }
    private void updateAttemps(EditText displayData) {
        if(totalAttemps>0) totalAttemps--;
        String text = "Insert the following PIN "+ String.valueOf(totalAttemps) +" times: \n" + String.valueOf(randomPIN);
        displayData.setText(text);
    }

    public void updateDisplayPin(EditText displayPin, ViewGroup rootLayout, String buttonNumber) {
        String pinToWrite = String.valueOf(displayPin.getText()) + buttonNumber;
        //if pinView > 4 --> reset to the current number pressed
        if(pinToWrite.length() > 4 ) pinToWrite = buttonNumber;

        displayPin.setText(pinToWrite);

        //Re-initialize buttons with shuffle
        if(pinToWrite.length() == 4) helperActivity.initializeButtons(rootLayout);
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


    public void changeUser(View view) {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        TextView userIdText = findViewById(R.id.selected_number);
        helperActivity.changeUser(view, userIdText);

        //set new global variables and update data
        totalAttemps = 3;
        randomPIN = helperActivity.randomPIN();

        View displayDataView = findViewById(R.id.display_data);
        EditText displayData = (EditText) displayDataView;
        View displayPinView = findViewById(R.id.display_pin);
        EditText displayPin = (EditText) displayPinView;

        updatePinData(displayData, displayPin);
    }
}
