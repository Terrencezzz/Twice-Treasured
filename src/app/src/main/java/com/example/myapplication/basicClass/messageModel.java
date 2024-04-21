package com.example.myapplication.basicClass;

public class messageModel {
    String message;
    String senderId;
    long timeStamp;

    public messageModel(String message, String senderId, long timeStamp) {
        this.message = message;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
    }
}
