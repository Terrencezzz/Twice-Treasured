package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class IntroPage extends Page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        setButton();
    }

    private void setButton(){

        TextView btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> goRegisterPage());

        TextView btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> goLoginPage());

        TextView btnSkipLogin = findViewById(R.id.btnSkipLogin);
        btnSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomePage();
            }
        });
    }




}