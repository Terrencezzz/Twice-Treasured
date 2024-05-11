package com.example.myapplication.basicClass;

public class Notification {
    private String NotiID;
    private String UserID;
    private String NotiTitle;
    private String NotiContents;
    private int NotiStatus;

    private String CreateTime;

    public Notification() {
    }

    public String getNotiID() {
        return NotiID;
    }

    public void setNotiID(String notiID) {
        NotiID = notiID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getNotiContents() {
        return NotiContents;
    }

    public void setNotiContents(String notiContents) {
        NotiContents = notiContents;
    }

    public int getNotiStatus() {
        return NotiStatus;
    }

    public void setNotiStatus(int notiStatus) {
        NotiStatus = notiStatus;
    }

    public String getNotiTitle() {
        return NotiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        NotiTitle = notiTitle;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
