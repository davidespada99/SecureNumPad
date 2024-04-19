package com.example.securenumpad;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    int attemps = 10;
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
        View displayPinView = findViewById(R.id.display_pin);
        updatePinData(displayDataView, displayPinView);
    }



    public void onClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);

        //button
        Button button = (Button) v;
        Log.d("button_ID", String.valueOf(button.getId()));
        String buttonNumber = (String) button.getText();

        //Update Pin digits in display_pin
        View displayPinView = findViewById(R.id.display_pin);
        updateDisplayPin(displayPinView, rootLayout, buttonNumber);

        //update Data Text View
        View displayDataView = findViewById(R.id.display_data);
        updatePinData(displayDataView, displayPinView);

        //Get current USer ID
//        TextView userIdText = findViewById(R.id.selected_number);
//        int currUserId = Integer.parseInt(String.valueOf(userIdText.getText()));

        //Write in the csv file the buttonID, currUserID
//        csvWriter(String.valueOf(button.getId() + "\n") /*+ "\n" + String.valueOf(currUserId)*/);

    }

    //--------------------INSERT INTO FUNCTION HELPER !!!!
;

    public void updatePinData(View displayDataView, View displayPinView){
        EditText displayData = (EditText) displayDataView;
        String text = "Insert the following PIN "+ String.valueOf(attemps) +" times: \n" + String.valueOf(randomPIN);
        displayData.setText(text);

        EditText displayPin = (EditText) displayPinView;
        //Log.d("PINInsertUpdate", displayPin.getText().toString());

        if(displayPin.getText().length()==4 && displayPin.getText().toString().equals(randomPIN)){
            //Display error message
            Toast.makeText(getApplicationContext(), "Right PIN! Please continue", Toast.LENGTH_SHORT).show();
            //Update display data
            updateAttemps(displayData);
        } else if (displayPin.getText().length()==4 && !displayPin.getText().toString().equals(randomPIN)) {
            //Display error message
            Toast.makeText(getApplicationContext(), "Wrong PIN! Please try again", Toast.LENGTH_SHORT).show();
            //Delete last 4 lines from csv;
        }
    }
    private void updateAttemps(EditText displayData) {
        attemps--;
        String text = "Insert the following PIN "+ String.valueOf(attemps) +" times: \n" + String.valueOf(randomPIN);
        displayData.setText(text);
    }

    public void updateDisplayPin(View displayPinView, ViewGroup rootLayout, String buttonNumber) {
        //display_pin box
        EditText displayPin = (EditText) displayPinView;

        String pinToWrite = String.valueOf(displayPin.getText()) + buttonNumber;
        displayPin.setText(pinToWrite);

        //if pinView > 4 --> reset to the current number pressed
        if(pinToWrite.length() > 4 ) pinToWrite = buttonNumber;
        displayPin.setText(pinToWrite);

        //Re-initialize buttons with shuffle
        if(pinToWrite.length() == 4) helperActivity.initializeButtons(rootLayout);
    }




    public void csvWriter( String content ) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "logData.csv");
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);

        //Log.d("FILEDIT", String.valueOf(file.getAbsoluteFile()));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(file.length()<=0){
            bw.write("UserID, attempt_num, getSize, buttonID\n");
        }

        bw.write(content);
        bw.close();
    }



    public void changeUser(View view) {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        TextView userIdText = findViewById(R.id.selected_number);
        helperActivity.changeUser(view, userIdText);

    }
}
