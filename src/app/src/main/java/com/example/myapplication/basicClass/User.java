package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

/**
 * This page is for the user constructor
 * @author Wanzhong Wu (u7642453), Scott Ferrageau de St Amand (u7303997) , Wen Li (u7706423)
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String headImage;
    private String location;
    private List<String> messageIdList;

    public User() {

    }

    public User(String id, String name, String email, String password, String headImage, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.headImage = headImage;
        this.location = location;
        this.messageIdList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public String getLocation() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addMessageId(String messageId) {
        if (this.messageIdList == null) {
            this.messageIdList = new ArrayList<>();
        }

        this.messageIdList.add(messageId);
    }
    public List<String> getMessageIds() {
        return messageIdList;
    }
}
