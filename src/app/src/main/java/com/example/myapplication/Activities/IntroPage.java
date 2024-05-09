package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.UserLoggedOutState;

public class IntroPage extends Page {
    private TextView btnSkipLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        setButton();
    }

    // Set up buttons for registration, login, and skipping login.
    private void setButton() {
        // Register button
        TextView btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> goRegisterPage());

        // Login button
        TextView btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> goLoginPage());

        // Skip login button
        btnSkipLogin = findViewById(R.id.btnSkipLogin);
        btnSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set to visitor mode
                GlobalVariables.getInstance().setState(new UserLoggedOutState());

                // Go to HomePage
                Intent intent = new Intent(IntroPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
