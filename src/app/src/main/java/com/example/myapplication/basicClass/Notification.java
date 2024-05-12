package com.example.myapplication.basicClass;

public class Notification {
    private String notiID;
    private String userID;
    private String notiTitle;
    private String notiContents;
    private int notiStatus;

    private String createTime;
    private String notiType;
    private String notiProductID;

    public Notification() {
    }

    public String getNotiID() {
        return notiID;
    }

    public void setNotiID(String notiID) {
        this.notiID = notiID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNotiTitle() {
        return notiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiContents() {
        return notiContents;
    }

    public void setNotiContents(String notiContents) {
        this.notiContents = notiContents;
    }

    public int getNotiStatus() {
        return notiStatus;
    }

    public void setNotiStatus(int notiStatus) {
        this.notiStatus = notiStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }

    public String getNotiProductID() {
        return notiProductID;
    }

    public void setNotiProductID(String notiProductID) {
        this.notiProductID = notiProductID;
    }
}
