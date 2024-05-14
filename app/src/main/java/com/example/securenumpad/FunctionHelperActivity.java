package com.example.securenumpad;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FunctionHelperActivity extends AppCompatActivity {

    private static boolean isRealUser = true;
    private static int currentUserId = 1;
    private static String currentInsertedPin = "";
    private static String pinToInsert = "";



    private static String logDataToCSV = "";

    public static String getCurrentInsertedPin() {
        return currentInsertedPin;
    }

    public static void setCurrentInsertedPin(String tempCurrentInsertedPin) {
        currentInsertedPin = tempCurrentInsertedPin;
    }

    public static String getLogDataToCSV() {
        return logDataToCSV;
    }

    public static void setLogDataToCSV(String logDataToCSV) {
        FunctionHelperActivity.logDataToCSV = logDataToCSV;
    }

    public static String getPinToInsert() {
        return pinToInsert;
    }

    public static void setPinToInsert(String pinToInsert) {
        FunctionHelperActivity.pinToInsert = pinToInsert;
    }


    public static boolean isIsRealUser() {
        return isRealUser;
    }

    public static void setIsRealUser(boolean isRealUser) {
        FunctionHelperActivity.isRealUser = isRealUser;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        FunctionHelperActivity.currentUserId = currentUserId;
    }


    public static void initializeButtons(ViewGroup rootLayout) {
        String buttonTag = "button";
        List<View> buttons = findViewsByTag(rootLayout, buttonTag);

        if (buttons.size() != 10) {
            Log.e("MainActivity", "Expected 10 buttons with tag 'myButton', found: " + buttons.size());
            return;
        }
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

//        Convert int array to Integer array
        //Integer[] integerNumbers = Arrays.stream(numbers).boxed().toArray(Integer[]::new);
        int[] integerNumbers = numbers;
//        Shuffle the array
        //Collections.shuffle(Arrays.asList(integerNumbers));

//        setId() for each button in ascending order
        int id=0;
        for (View button : buttons) {
            button.setId(id);
            id++;
        }

        int index = 0;
        for (View buttonView : buttons) {
            if (buttonView instanceof Button) {
                Button button = (Button) buttonView;
                button.setText(String.valueOf(integerNumbers[index++]));
            }
        }
    }

    private static List<View> findViewsByTag(ViewGroup root, String tag) {
        List<View> views = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(findViewsByTag((ViewGroup) child, tag));
            }
            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }
        }
        return views;
    }

    public static void changeUser(View v, TextView userIdText) {
        int currId = Integer.parseInt(String.valueOf(userIdText.getText()));
        String tag = String.valueOf(v.getTag());

        if(Integer.parseInt(tag) == -1 && currId > 1){
            userIdText.setText(String.valueOf(currId - 1));
        }else if(Integer.parseInt(tag) == 1){
            userIdText.setText(String.valueOf(currId + 1));
        }
    }

    public static double Mean(ArrayList<Double> data){
        int length = data.size();
        double mean = 0;
        for (double val : data ){
            mean += val;
        }
        return mean / (float)length;
    }

    public static double Var(List<Double> data){
        int length = data.size();
        double mean = 0;
        double var = 0;
        for (double val : data ){
            mean += val;
        }
        mean = mean / (float)length;

        for (double val : data ){
            var = Math.pow((val - mean), 2);
        }

        return var / (float) length;
    }




}
