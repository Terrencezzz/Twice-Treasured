package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Activities.HomePage;
import com.example.myapplication.R;

public class Login extends AppCompatActivity {

    /**
     * This app will start from login page, if the user successfully login, use this function
     * to move to next activity.
     */
    private void onLoginSuccess() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}