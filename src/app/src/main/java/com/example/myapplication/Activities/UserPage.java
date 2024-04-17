package com.example.myapplication.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * In this page user can check his information.
 */
public class UserPage extends Page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        TextView homePage = findViewById(R.id.HomePage);
        TextView userPage = findViewById(R.id.UserPage);
        TextView tradePage = findViewById(R.id.TradePage);
        TextView favorite = findViewById(R.id.Favorite);

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomePage();
            }
        });

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserPage();
            }
        });

        tradePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTradePage();
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFavorite();
            }
        });
    }
}