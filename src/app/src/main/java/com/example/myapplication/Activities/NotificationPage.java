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
import com.example.myapplication.basicClass.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    /***
     * Initial Notification and display
     * @author Wen Li @u7706423
     */
    private void initNotiList() {

        User user = globalVars.getLoginUser();
        if(user == null)
            return;

        DatabaseReference db = database.getReference("Notification");
        Query query = db.orderByChild("userID").equalTo(user.getId());
        ArrayList<Notification> notifications = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    notifications.clear();
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        notifications.add(issue.getValue(Notification.class));
                    }
                    if (notifications.size() > 0) {

                        Collections.sort(notifications, Comparator.comparingInt(Notification::getNotiStatus));
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

    /**
     * Set Notification status read
     * @author Wen Li @u7706423
     */
    public void readNotice(String notiID){

        database = Database.getDatabase();
        DatabaseReference notification = database.getReference().child("Notification").child(notiID);
        notification.child("notiStatus").setValue(1);

    }
}