package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private Button register;
    private Button login;
    private EditText account;
    private EditText password;
    private FirebaseAuth auth;

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
        account = findViewById(R.id.account);
        password = findViewById(R.id.password_Login);
        auth = FirebaseAuth.getInstance();

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
                String txt_account = account.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_account,txt_password);
            }
        });
    }

    private void loginUser(String txtAccount, String txtPassword) {
        auth.signInWithEmailAndPassword(txtAccount,txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResult) {
                if (authResult.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Success!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this, HomePage.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}