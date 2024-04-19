package com.example.securenumpad;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FunctionHelperActivity extends AppCompatActivity {

    public void initializeButtons(ViewGroup rootLayout ) {
        String buttonTag = "button";
        List<View> buttons = findViewsByTag(rootLayout, buttonTag);

        if (buttons.size() != 10) {
            Log.e("MainActivity", "Expected 10 buttons with tag 'myButton', found: " + buttons.size());
            return;
        }
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

//        Convert int array to Integer array
        Integer[] integerNumbers = Arrays.stream(numbers).boxed().toArray(Integer[]::new);

//        Shuffle the array
        Collections.shuffle(Arrays.asList(integerNumbers));

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

    private List<View> findViewsByTag(ViewGroup root, String tag) {
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

    public void changeUser(View v, TextView userIdText) {
        int currId = Integer.parseInt(String.valueOf(userIdText.getText()));
        String tag = String.valueOf(v.getTag());

        if(Integer.parseInt(tag) == -1 && currId > 1){
            userIdText.setText(String.valueOf(currId - 1));
        }else if(Integer.parseInt(tag) == 1){
            userIdText.setText(String.valueOf(currId + 1));
        }
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
            bw.write("UserID, buttonID, attempt_num, getSize\n");
        }

        bw.write(content);
        bw.close();
    }

    //Output a random 4 digits PIN
    public String randomPIN(){
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return String.format(Locale.getDefault(), "%04d", randomNumber);
    }







    public void testHelper(){
        Log.d("TestHelper", "WORKS!!!");
    }
}
