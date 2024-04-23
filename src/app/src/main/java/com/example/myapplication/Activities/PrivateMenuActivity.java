package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.example.myapplication.R;

public class PrivateMenuActivity extends Page {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_menu);

        ImageButton btnReturnToHome =
                findViewById(R.id.btn_return_to_home);


        btnReturnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {goHomePage();}
        });
    }

}
