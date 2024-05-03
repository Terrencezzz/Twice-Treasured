package com.example.myapplication.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.google.firebase.database.FirebaseDatabase;


/**
 * In this page user can check his information.
 */
public class UserPage extends Page {


    FirebaseDatabase database;
    GlobalVariables globalVars;
    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;

    private ImageView ivUserPic;
    private TextView username;
    private TextView useremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        database = Database.getDatabase();
        globalVars = GlobalVariables.getInstance();

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);
        ivUserPic = findViewById(R.id.ivUserPic);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);

        username.setText(globalVars.getLoginUser().getName());
        useremail.setText(globalVars.getLoginUser().getEmail());
        Glide.with(this).load(globalVars.getLoginUser().getHeadImage()).into(ivUserPic);

        clHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomePage();
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
        clPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goPrivateMenu();}
        });

    }
}