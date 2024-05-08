package com.example.securenumpad;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private final int NUMBEROFREGISTRATION = 10;
    private int currentNumberOfRegistration = 0;
    String randomPIN = FunctionHelperActivity.randomPIN();
    private int currentUserId = FunctionHelperActivity.getCurrentUserId();

    private ArrayList<Integer> sampleNumbers = new ArrayList<>(Arrays.asList(1, 2, 5, 10));

    private ArrayList<Double> means;
    private ArrayList<Double> vars;



    private ArrayList<Double> sizes;
    private ArrayList<Double> tempSizes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FunctionHelperActivity.setCurrentInsertedPin("");
        FunctionHelperActivity.setLogDataToCSV("");
        setContentView(R.layout.activity_pad);
        ViewGroup rootLayout = findViewById(android.R.id.content);
        FunctionHelperActivity.initializeButtons(rootLayout);
        updatePinData(randomPIN);
        means = new ArrayList<>();
        vars = new ArrayList<>();
        sizes = new ArrayList<>();
        tempSizes = new ArrayList<>();
        //=============================================== AddUserEntry() aggiungo utente e pin al file di match tra pin e utente  =============================================== //
            //Controllo se l'id esiste già lo sovrascrivo
            //Controllo se il file User{id}Stats.csv esiste già, lo elimino e creo nuovo file
    }


    public void onButtonClicked(View v) throws IOException {
        //button
        Button button = (Button) v;
        String buttonId = String.valueOf(button.getId());
        String buttonNumber = (String) button.getText();

        //Aggiorna il PIN
        FunctionHelperActivity.setCurrentInsertedPin(FunctionHelperActivity.getCurrentInsertedPin() + buttonNumber);
        //Aggiorna il display del PIN
        updateDisplayPin(buttonNumber);
        //Aggiorna il display delle Info
        updatePinData(randomPIN);

        String logData = String.valueOf(currentUserId) + ","
                + currentNumberOfRegistration + ","
                + buttonId + ","
                + buttonNumber + "\n";

        FunctionHelperActivity.setLogDataToCSV(FunctionHelperActivity.getLogDataToCSV() + logData);


        //Handler of the PIN when reach the desired length
        if(FunctionHelperActivity.getCurrentInsertedPin().length()==4) {
            //write in csv only if right pin!
            if(FunctionHelperActivity.getCurrentInsertedPin().equals(randomPIN)) {
                //Log data in csv
                //FunctionHelperActivity.csvWriter();
                sizes.addAll(tempSizes);


                if ( sampleNumbers.contains(currentNumberOfRegistration + 1) ){

                    means.add(FunctionHelperActivity.Mean(sizes));
                    vars.add(FunctionHelperActivity.Var(sizes));

                }

                //Update display data
                updateAttempts();
            }

            //If we inserted NUMBEROFREGISTRATION times the correct PIN, we finished the registration and come back to the home activity
            if(currentNumberOfRegistration == NUMBEROFREGISTRATION){
                // ================================================ createUserStatsCSV(); ================================================
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                FunctionHelperActivity.csvWriterStats(currentUserId, randomPIN, means, vars);
                startActivity(intent);
            }
            logData = "";
            FunctionHelperActivity.setCurrentInsertedPin("");
            FunctionHelperActivity.setLogDataToCSV("");
            tempSizes.clear();
        }

    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!


    private void updateAttempts() {
        EditText displayData = findViewById(R.id.display_data);
        currentNumberOfRegistration++;
        String text = "Insert the following PIN "+ String.valueOf(NUMBEROFREGISTRATION-currentNumberOfRegistration) +" times: \n" + String.valueOf(randomPIN);
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
    public void  updatePinData(String randomPIN){
        EditText displayData = (EditText) findViewById(R.id.display_data);
        EditText displayPin = (EditText) findViewById(R.id.display_pin);

        String text = "";
        text = "Insert the following PIN "+ String.valueOf(NUMBEROFREGISTRATION-currentNumberOfRegistration) +" times: \n" + String.valueOf(randomPIN);
        displayData.setText(text);

        if (displayPin.getText().length() == 4 && displayPin.getText().toString().equals(randomPIN)) {
            //Display error message
            Toast.makeText(getApplicationContext(), "Right PIN! ", Toast.LENGTH_SHORT).show();

        } else if (displayPin.getText().length() == 4 && !displayPin.getText().toString().equals(randomPIN)) {
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
            tempSizes.add(size);

        }

        return super.dispatchTouchEvent(event);
    }



}
