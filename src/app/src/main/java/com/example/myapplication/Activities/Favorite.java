package com.example.myapplication.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * In this page people can check their favorite products.
 */
public class Favorite extends Page {

    private TextView btnPrivate;
    private TextView btnHome;
    private TextView btnMe;
    private TextView btnFavorite;
    private Button btnTradePlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        btnPrivate = findViewById(R.id.btnPrivate);
        btnHome = findViewById(R.id.btnHome);
        btnMe = findViewById(R.id.btnMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        btnFavorite= findViewById(R.id.btnFavorite);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomePage();
            }
        });

        btnMe.setOnClickListener(new View.OnClickListener() {
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

        btnPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goPrivateMenu();}
        });

    }
}