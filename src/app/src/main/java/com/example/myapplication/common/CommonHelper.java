package com.example.myapplication.common;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.OnDataUpdatedListener;
import com.example.myapplication.basicClass.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommonHelper {

    public static void showToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void refreshLoginUser(OnDataUpdatedListener listener) {

        FirebaseDatabase database = Database.getDatabase();
        GlobalVariables globalVars = GlobalVariables.getInstance();
        String userEmail = globalVars.getLoginUser().getEmail();
        database = Database.getDatabase();
        DatabaseReference db = database.getReference("User");
        Query query = db.orderByChild("email").equalTo(userEmail);
        ArrayList<User> users = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    users.clear();
                    for (DataSnapshot issue:snapshot.getChildren()){
                        users.add(issue.getValue(User.class));
                    }
                    if(users.size()>0){
                        GlobalVariables.getInstance().setLoginUser(users.get(0));
                        listener.onDataUpdated();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
