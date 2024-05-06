package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

public class MessageBuble {
    private String message, senderId, timestamp;

    public MessageBuble() {
    }

    public MessageBuble(String message, String senderId, String timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageBuble{" +
                "message='" + message + '\'' +
                ", senderId='" + senderId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
