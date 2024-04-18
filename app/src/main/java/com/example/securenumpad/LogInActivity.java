package com.example.securenumpad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);

        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        helperActivity.initializeButtons(rootLayout);

        // Retrieve the intent
        Intent intent = getIntent();

    }

    public void onClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        //display_text box
        View displayTextView = findViewById(R.id.display_text);
        EditText displayText = (EditText) displayTextView;

        //button
        Button b = (Button) v;
        Log.d("button_ID", String.valueOf(b.getId()));

        // Write in the csv file the buttonID (bID)
        //csvWriter(String.valueOf(b.getId()) + "\n");

        String textToWrite = String.valueOf(displayText.getText()) + String.valueOf(b.getText());

        if(textToWrite.length() >= 4 ){
            textToWrite = "";
            helperActivity.initializeButtons(rootLayout);
        }

        displayText.setText(textToWrite);
    }


}
