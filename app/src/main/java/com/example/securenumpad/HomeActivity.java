package com.example.securenumpad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button registerButton = findViewById(R.id.register_button);
        Button loginButton = findViewById(R.id.login_button);

        registerButton.setOnClickListener(v -> {
            // Perform action for Register button click
            Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
            intent.putExtra("ACTION", "REGISTER"); // Add extra data if needed
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            // Perform action for Log In button click
            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            intent.putExtra("ACTION", "LOGIN"); // Add extra data if needed
            startActivity(intent);
        });

    }
}