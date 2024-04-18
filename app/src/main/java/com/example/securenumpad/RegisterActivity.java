package com.example.securenumpad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    // Create an instance of the Random class
    Random random = new Random();
    // Generate a random integer between 0 and 9999 (inclusive)
    int randomNumber = random.nextInt(10000);
    // Format the random number as a string with leading zeros if necessary
    String randomPIN = String.format(Locale.getDefault(), "%04d", randomNumber);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);

        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        helperActivity.initializeButtons(rootLayout);

        // Retrieve the intent
        //Intent intent = getIntent();

        View displayDataView = findViewById(R.id.display_data);
        EditText displayData = (EditText) displayDataView;
        displayData.setText("Insert the following PIN 10 times: \n" + String.valueOf(randomPIN));
    }



    public void onClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        //display_text box
        View displayTextView = findViewById(R.id.display_text);
        EditText displayText = (EditText) displayTextView;

        //button
        Button button = (Button) v;
        Log.d("button_ID", String.valueOf(button.getId()));

//        TextView userIdText = findViewById(R.id.selected_number);
//        int currUserId = Integer.parseInt(String.valueOf(userIdText.getText()));

        // Write in the csv file the buttonID, currUserID
        csvWriter(String.valueOf(button.getId() + "\n") /*+ "\n" + String.valueOf(currUserId)*/);

        String textToWrite = String.valueOf(displayText.getText()) + String.valueOf(button.getText());

        if(textToWrite.length() >= 4 ){
            textToWrite = "";
            helperActivity.initializeButtons(rootLayout);
        }

        displayText.setText(textToWrite);
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
