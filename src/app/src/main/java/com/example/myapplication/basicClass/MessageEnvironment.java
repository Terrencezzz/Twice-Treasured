package com.example.myapplication.basicClass;

import java.util.List;

/**
 * This class saves details regarding the Message Environment between two users.
 *
 */


public class MessageEnvironment {
    String environmentId, recentSenderId, timestamp, recentMessageTimestamp;
    List<String> UserIds;


    public MessageEnvironment(String environmentId, List<String> userIds, String timeStamp, String recentSenderId) {
        this.environmentId = environmentId;
        UserIds = userIds;
        this.timestamp = timeStamp;
        this.recentSenderId = recentSenderId;
    }

    public MessageEnvironment(){};

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public List<String> getUserIds() {
        return UserIds;
    }

    public void setUserIds(List<String> userIds) {
        UserIds = userIds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRecentSenderId() {
        return recentSenderId;
    }

    public void setRecentSenderId(String recentSenderId) {
        this.recentSenderId = recentSenderId;
    }

    public String getRecentMessageTimestamp() {
        return recentMessageTimestamp;
    }

    public void setRecentMessageTimestamp(String recentMessageTimestamp) {
        this.recentMessageTimestamp = recentMessageTimestamp;
    }
}
