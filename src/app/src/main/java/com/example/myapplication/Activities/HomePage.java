package com.example.myapplication.Activities;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * This is a home page, after you login you come to this page.
 * This page will contain the search and some advertisements.
 */
public class HomePage extends Page {

    private  ImageView btnSearch;
    private TextView btnPrivate;
    private TextView btnHome;
    private TextView btnMe;
    private TextView btnFavorite;
    private Button btnTradePlatform;
    private  TextView btnViewmore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnSearch = findViewById(R.id.btnSearch);
        btnPrivate = findViewById(R.id.btnPrivate);
        btnHome = findViewById(R.id.btnHome);
        btnMe = findViewById(R.id.btnMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        btnFavorite= findViewById(R.id.btnFavorite);
        btnViewmore = findViewById(R.id.btnViewmore);
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
                startActivity(new Intent(HomePage.this, TradePlatform.class));
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFavorite();
            }
        });

        btnPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, PrivateChat.class));
            }
        });
    }
}