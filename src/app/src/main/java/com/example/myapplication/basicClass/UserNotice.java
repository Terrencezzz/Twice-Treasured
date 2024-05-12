package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

import com.example.myapplication.common.CommonHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNotice implements Notice{


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference();

    /**
     * uid is UserID
     * */
    @Override
    public void addNotice(String uid) {

        String notiID = mDatabase.push().getKey();
        String createTime = CommonHelper.getCurrentTimestamp();
        Notification notification = new Notification();

        notification.setNotiID(notiID);
        notification.setUserID(uid);
        notification.setNotiTitle("User Security Reminder");
        notification.setNotiContents("Your account has changed password at "+createTime+". If it isn't your action, please lock your account and contact us immediately:)");
        notification.setNotiStatus(0);
        notification.setCreateTime(createTime);
        notification.setNotiType("1");
        notification.setNotiProductID("");

        mDatabase.child("Notification").child(notiID).setValue(notification)
                .addOnSuccessListener(unused -> System.out.println("Generate a new UserNotice successfully.NotiId="+notiID))
                .addOnFailureListener(e -> System.out.println("Fail to generate a new UserNotice, reason:"+e.getMessage()));

    }
}
