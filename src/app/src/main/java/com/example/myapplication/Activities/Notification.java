package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;

public class Notification extends Page {

    private ImageView notification_btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notification_btnBack = findViewById(R.id.notification_btnBack);

        notification_btnBack.setOnClickListener(View-> finish());
    }
}