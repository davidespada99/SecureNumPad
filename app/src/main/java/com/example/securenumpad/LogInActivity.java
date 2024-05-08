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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class LogInActivity extends AppCompatActivity {

    private ArrayList<Double> sizes;
    private int UserId;
    private boolean isRealUser;
    private double mean;
    private double var;
    private String pinToInsert = "";
    private int userCurrentConfusionMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        FunctionHelperActivity.initializeButtons(rootLayout);

        // Retrieve the intent
        Intent intent = getIntent();

        UserId = FunctionHelperActivity.getCurrentUserId();
        isRealUser = FunctionHelperActivity.isIsRealUser();
        ArrayList<Double>[] userInfo;
        sizes = new ArrayList<>();
        Log.d("onCreate LoginActivity 1", "Tryng get info: ");
        try {
            userInfo = getUserInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d("onCreate LoginActivity", "onCreate: " + UserId + " " + isRealUser + " " + sizes + " " + Arrays.toString(userInfo) + " " + pinToInsert);

        //////////// temp code to move //////////////
        EditText displayData = findViewById(R.id.display_data);
        String text = "Insert the following PIN " + pinToInsert;
        displayData.setText(text);


    }

    public void onClicked(View v) throws IOException {
        FunctionHelperActivity helperActivity = new FunctionHelperActivity();
        ViewGroup rootLayout = findViewById(android.R.id.content);
        //display_text box
        View displayTextView = findViewById(R.id.display_pin);
        EditText displayText = (EditText) displayTextView;

        //button
        Button b = (Button) v;
        Log.d("button_ID", String.valueOf(b.getId()));

        // Write in the csv file the buttonID (bID)
        //csvWriter(String.valueOf(b.getId()) + "\n");

        String textToWrite = String.valueOf(displayText.getText()) + String.valueOf(b.getText());

        //We inserted the PIN
        if(textToWrite.length() >= 4 ){
            textToWrite = "";
            FunctionHelperActivity.initializeButtons(rootLayout);
        }

        displayText.setText(textToWrite);
    }

    public void changeUser(View view) {
        Log.d("DBisRealUser", String.valueOf(FunctionHelperActivity.isIsRealUser()));
        Log.d("DBcurrentUserId", String.valueOf(FunctionHelperActivity.getCurrentUserId()));
    }


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

    private ArrayList<Double>[] getUserInfo() throws IOException {
        ArrayList<Double>[] userInfo = new ArrayList[2];
        userInfo[0] = new ArrayList<Double>();
        userInfo[1] = new ArrayList<Double>();
        String filename = "UserStatistics.csv";
        int l;
        //Retrieve informazioni dal CSV
        ArrayList<String> userInfoRecord = parseCSVUserInfo(filename, UserId);
        Log.d("After parseCSVUserInfo ", "1 getUserInfo: " + userInfoRecord);
        l = userInfoRecord.size();
        Log.d("GetUserInfo", "2 getUserInfo: " + userInfoRecord);

        //SET PIN TO INSERT (MAYBE CAN BE MOVED)
        pinToInsert = userInfoRecord.get(1);


        for (int i = 2; i < l; i ++){
            double valToInsert = Double.valueOf(userInfoRecord.get(i));
            Log.d("GetUserInfo", "3 getUserInfo userInfo: " + userInfo.toString() + " adding " + valToInsert);
            if (  i < (l+2)/2 ) {
                userInfo[0].add(valToInsert);
            }else{
               userInfo[1].add( valToInsert );
            }
        }

        Log.d("GetUserInfo", "return getUserInfo: userInfo" + Arrays.toString(userInfo));
        return userInfo;

    }

    private boolean[] checkLogin(){
        //Deve ritornare un array di booleani 1 o 0 in base al login permesso o meno confrontando per ogni media e varianza

        boolean loginPermitted[] = new boolean[4];

        return loginPermitted;
    }

    private ArrayList<String> parseCSVUserInfo(String filename, int userId) throws IOException {
        ArrayList<String> userRecord = null;
        InputStream inputStream = null;
        File file;
        try{
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filename);
            inputStream = new FileInputStream(file);

            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            Log.d("After new file", "parseCSVUserInfo: File exixts?" + file.exists());
            String line;

            while((line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");
                Log.d("After new file", "parseCSVUserInfo: line = " + line);
                Log.d("After new file", "parseCSVUserInfo: token = " + Arrays.toString(tokens));
                Log.d("After new file", "parseCSVUserInfo: userId = " + userId + "tokens[0] = " +tokens[0]);
                Log.d("After new file", "parseCSVUserInfo: tokens.length > 1 = " + (tokens.length > 1) + " tokens[0].equals(String.valueOf(userId) = " + (tokens[0].equals(String.valueOf(userId))));
                if(tokens.length > 1 && tokens[0].equals(String.valueOf(userId))){

                    userRecord = new ArrayList<>(Arrays.asList(tokens));
                    Log.d("After new file", "parseCSVUserInfo: userRecord = " + userRecord);
                    break;
                }
         ;;   }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
