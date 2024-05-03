package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;

public class PrivateMenuActivity extends Page {

    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_menu);

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);
        ImageButton btnReturnToHome =
                findViewById(R.id.btn_return_to_home);


        btnReturnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {goHomePage();}
        });

        clMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserPage();
            }
        });

        btnTradePlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTradePage();
            }
        });

        clFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFavorite();
            }
        });

        clHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomePage();
            }
        });
    }

}
