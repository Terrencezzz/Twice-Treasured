package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ChatMenuAdapter;
import com.example.myapplication.Adapters.MessageAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.MessageBuble;
import com.example.myapplication.basicClass.MessageEnvironment;
import com.example.myapplication.basicClass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class PrivateMenuActivity extends Page {

    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;

    private Button btnTestMessage;

    private RecyclerView recyclerViewChatMenu;
    private User currentUser;
    List<User> usersList;
    ChatMenuAdapter chatMenuAdapter;
    FirebaseDatabase database;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_menu);

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite = findViewById(R.id.clFavorite);
        recyclerViewChatMenu = findViewById(R.id.menu_recycler_view);

        GlobalVariables globalVariables = GlobalVariables.getInstance();
        currentUser = globalVariables.getLoginUser();
        usersList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("User").child(currentUser.getId());


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


        chatMenuAdapter = new ChatMenuAdapter(getApplicationContext(), usersList);
        recyclerViewChatMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewChatMenu.setAdapter(chatMenuAdapter);


        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("messageIds").exists()) {
                    usersList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.child("messageIds").getChildren()) {
                        String id = dataSnapshot.getValue(String.class);

                        database.getReference("MessageEnvironments").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshotOfId : snapshot.child("userIds").getChildren()) {
                                    String id = snapshotOfId.getValue(String.class);
                                    if (!id.equals(currentUser.getId())) {
                                        database.getReference("User").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                User user = snapshot.getValue(User.class);
                                                if (user != null) {
                                                    usersList.add(user);
                                                }
                                                chatMenuAdapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                } else {
                    usersList.clear();
                    chatMenuAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userRef.addValueEventListener(userListener);
    }
}
