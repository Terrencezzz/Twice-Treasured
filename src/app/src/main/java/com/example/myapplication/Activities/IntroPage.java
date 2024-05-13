package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.User;
import com.google.common.reflect.TypeToken;

import java.util.List;

public class IntroPage extends Page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        setButton();

//These two lines of code should not be deleted,they are used to upload data to Firebase
//     String assetFileName = "product_data.json";
//        Database.uploadJsonToFirebase(this, assetFileName);

    }

    private void setButton() {

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