package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.Adapters.NotificationAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Category;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class NotificationPage extends Page {

    FirebaseStorage storage;
    FirebaseDatabase database;
    GlobalVariables globalVars;
    private ImageView notification_btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        database = Database.getDatabase();
        globalVars = GlobalVariables.getInstance();

        notification_btnBack = findViewById(R.id.notification_btnBack);

        notification_btnBack.setOnClickListener(View-> finish());

        initNotiList();
    }

    private void initNotiList() {

        DatabaseReference db = database.getReference("Notification");
        ArrayList<Notification> notifications = new ArrayList<>();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        notifications.add(issue.getValue(Notification.class));
                    }
                    if (notifications.size() > 0) {
                        RecyclerView rvNotification = findViewById(R.id.rvNotification);
                        rvNotification.setLayoutManager(new LinearLayoutManager(NotificationPage.this,LinearLayoutManager.VERTICAL,false));
                        RecyclerView.Adapter<NotificationAdapter.viewholder> adapter = new NotificationAdapter(notifications);
                        rvNotification.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}