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

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_menu);

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);
        recyclerViewChatMenu = findViewById(R.id.menu_recycler_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesData = database.getReference("Messages");


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


        GlobalVariables globalVariables = GlobalVariables.getInstance();
        currentUser = globalVariables.getLoginUser();

        usersList = new ArrayList<>();
        generateUserList(currentUser);
        setUpRecycler(usersList);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateView(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Warning", "load failed");
            }
        };
        messagesData.addValueEventListener(eventListener);
    }

    void generateUserList(User user) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("MessageEnvironments");

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (data != null) {
                        MessageEnvironment messageEnvironment = data.getValue(MessageEnvironment.class);
                        List<String> userIds = messageEnvironment.getUserIds();
                        int i = 0;
                        while (i < 2 ) {
                            if (userIds.get(i).equals(currentUser.getId())) {
                                if (i == 0) {
                                    addUserFromFirebase(userIds.get(1));
                                    Log.d("debug", userIds.get(1));
                                } if (i == 1) {
                                    addUserFromFirebase(userIds.get(0));
                                    Log.d("debug", userIds.get(0));
                                }
                            }
                            i++;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void addUserFromFirebase(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");

        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usersList.add(snapshot.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    void setUpRecycler(List<User> usersList) {
        if (usersList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "yay", Toast.LENGTH_SHORT).show();
            chatMenuAdapter = new ChatMenuAdapter(getApplicationContext(),
                    new ArrayList<User>());
        } else {
            chatMenuAdapter = new ChatMenuAdapter(getApplicationContext(), usersList);
        }
        recyclerViewChatMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChatMenu.setAdapter(chatMenuAdapter);
    }
}
