package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class LoginPage extends AppCompatActivity {

    private Button register;
    private Button login;

    /**
     * This app will start from login page, if the user successfully login, use this function
     * to move to next activity.
     */
    private void onLoginSuccess() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
    }

    /**
     * This function check the user account
     * @return correctness of the Account
     */
    private boolean checkAccount() {
        return true;
    }

    /**
     * This function check the user password
     * @return correctness of the Password
     */
    private boolean checkPassword() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterActivity.class));
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAccount()&&checkPassword()) onLoginSuccess();
            }
        });
    }
}