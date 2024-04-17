package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button button = findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAccount()&&checkPassword()) onLoginSuccess();
            }
        });
    }
}