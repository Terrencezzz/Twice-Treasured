package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

/**
 * This class saves details regarding the Message Environment between two users.
 * @author Scott Ferrageau de St Amand (u7303997)
 */


public class MessageEnvironment {
    String environmentId, recentSenderId, timestamp, recentMessageTimestamp;
    List<String> userIds;
    List<MessageBuble> messageList;


    public MessageEnvironment(String environmentId, List<String> userIds, String timestamp,
                              String recentSenderId, String recentMessageTimestamp,
                              List<MessageBuble> messageList) {
        this.environmentId = environmentId;
        this.userIds = userIds;
        this.timestamp = timestamp;
        this.recentSenderId = recentSenderId;
        this.recentMessageTimestamp = recentMessageTimestamp;
        this.messageList = messageList;
    }

    public MessageEnvironment(){};

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
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

    public void addMessage(MessageBuble messageBuble) {
        messageList.add(messageBuble);
    }

    public List<MessageBuble> getMessageList() {
        return messageList;
    }
}
