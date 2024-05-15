package com.example.myapplication;

import static org.junit.Assert.*;

import com.example.myapplication.basicClass.Notification;
import com.example.myapplication.common.CommonHelper;

import org.junit.Test;

public class NotificationTest {

    Notification notification = new Notification();

    @Test
    public void getNotiID() {
        notification.setNotiID("1");
        assertEquals("1",notification.getNotiID());
    }

    @Test
    public void setNotiID() {
        notification.setNotiID("1");
        assertEquals("1",notification.getNotiID());
    }

    @Test
    public void getUserID() {
        notification.setUserID("1");
        assertEquals("1",notification.getUserID());
    }

    @Test
    public void setUserID() {
        notification.setUserID("1");
        assertEquals("1",notification.getUserID());
    }

    @Test
    public void getNotiTitle() {
        notification.setNotiTitle("title");
        assertEquals("title",notification.getNotiTitle());
    }

    @Test
    public void setNotiTitle() {
        notification.setNotiTitle("title");
        assertEquals("title",notification.getNotiTitle());
    }

    @Test
    public void getNotiContents() {
        notification.setNotiContents("contents");
        assertEquals("contents",notification.getNotiContents());
    }

    @Test
    public void setNotiContents() {
        notification.setNotiContents("contents");
        assertEquals("contents",notification.getNotiContents());
    }

    @Test
    public void getNotiStatus() {
        notification.setNotiStatus(1);
        assertEquals(1,notification.getNotiStatus());
    }

    @Test
    public void setNotiStatus() {
        notification.setNotiStatus(1);
        assertEquals(1,notification.getNotiStatus());
    }

    @Test
    public void getCreateTime() {
        String time = CommonHelper.getCurrentTimestamp();
        notification.setCreateTime(time);
        assertEquals(time,notification.getCreateTime());
    }

    @Test
    public void setCreateTime() {
        String time = CommonHelper.getCurrentTimestamp();
        notification.setCreateTime(time);
        assertEquals(time,notification.getCreateTime());
    }

    @Test
    public void getNotiType() {
        notification.setNotiType("1");
        assertEquals("1",notification.getNotiType());
    }

    @Test
    public void setNotiType() {
        notification.setNotiType("1");
        assertEquals("1",notification.getNotiType());
    }

    @Test
    public void getNotiProductID() {
        notification.setNotiProductID("1");
        assertEquals("1",notification.getNotiProductID());
    }

    @Test
    public void setNotiProductID() {
        notification.setNotiProductID("1");
        assertEquals("1",notification.getNotiProductID());
    }
}