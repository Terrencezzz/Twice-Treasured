package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

import com.example.myapplication.common.CommonHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteNotice implements Notice{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference();

    /**
     *  uid is ProductID
     *  If valid favorite number >5, add notice to product Owner
     * */
    @Override
    public void addNotice(String uid) {

        ArrayList<Favorite> favorites = new ArrayList<>();

        //check favorite number
        DatabaseReference favorite_db = database.getReference("Favorite");
        Query favorite_query = favorite_db.orderByChild("productID").equalTo(uid);
        favorite_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    favorites.clear();
                    for (DataSnapshot issue:snapshot.getChildren()){
                        if(Boolean.TRUE.equals(issue.child("favoriteStatus").getValue(boolean.class))) {
                            favorites.add(issue.getValue(Favorite.class));
                        }
                    }
                    if(favorites.size()>1){ //check favoriteStatus==true && favorite number>5
                        ArrayList<Notification> notifications = new ArrayList<>();
                        DatabaseReference notice_db = database.getReference("Notification");
                        Query notice_query = notice_db.orderByChild("notiProductID").equalTo(uid);
                        notice_query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    notifications.add(snapshot.getValue(Notification.class));
                                }
                                if(notifications.size()==0){  //check whether has been sent a notice already

                                    DatabaseReference product_db = database.getReference("Product").child(uid);
                                    product_db.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                String ownerId = snapshot.child("ownerID").getValue(String.class);
                                                String productName = snapshot.child("name").getValue(String.class);
                                                generateNotice(uid, ownerId, productName);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });

                                }
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

    private void generateNotice(String uid,String ownerId,String productName){

        String notiID = mDatabase.push().getKey();
        String createTime = CommonHelper.getCurrentTimestamp();
        Notification notification = new Notification();

        notification.setNotiID(notiID);
        notification.setUserID(ownerId);
        notification.setNotiTitle("Favorite Number Reminder");
        notification.setNotiContents("Congratulations! Your product "+productName+" has been favorited by more than 5 people!");
        notification.setNotiStatus(0);
        notification.setCreateTime(createTime);
        notification.setNotiType("2");
        notification.setNotiProductID(uid);

        mDatabase.child("Notification").child(notiID).setValue(notification)
                .addOnSuccessListener(unused -> System.out.println("Generate a new FavoriteNotice successfully.NotiId="+notiID))
                .addOnFailureListener(e -> System.out.println("Fail to generate a new FavoriteNotice, reason:"+e.getMessage()));


    }
}
