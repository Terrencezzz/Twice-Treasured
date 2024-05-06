package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
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

    private Button btnTestMessage;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_menu);

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);
        btnTestMessage = findViewById(R.id.open_message_test_btn);


        /**
         * Temporary button to test private message functionality, to be fixed when there is
         * a product page.
         */
        btnTestMessage.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PrivateChat.class );
            intent.putExtra("userEmail", "chattest@gmail.com");
            intent.putExtra("userId", "LE16b1YUZDPPR7pewtr7qTQz7Sz1");
            startActivity(intent);
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
