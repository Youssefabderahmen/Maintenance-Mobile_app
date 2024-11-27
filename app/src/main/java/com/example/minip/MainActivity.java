package com.example.minip;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private ImageView facebookLogin, linkedinLogin, googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        facebookLogin = findViewById(R.id.facebookLogin);
        linkedinLogin = findViewById(R.id.linkedinLogin);
        googleLogin = findViewById(R.id.googleLogin);

        // Set up the login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        // Set up social login button click listeners

    }

    private void login() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Check if the username and password match
        if (username.equals("Admin") && password.equals("123")) {
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            // Proceed to the next activity
            Intent intent = new Intent(MainActivity.this, navigation.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}

