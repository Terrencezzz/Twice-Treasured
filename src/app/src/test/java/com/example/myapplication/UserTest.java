package com.example.myapplication;

import static org.junit.Assert.*;

import com.example.myapplication.basicClass.User;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {

    User user = new User();

    @Before
    public void setUp(){

    }


    @Test
    public void getId() {
        user.setId("1");
        assertEquals("1",user.getId());
    }

    @Test
    public void getName() {
        user.setName("name");
        assertEquals("name",user.getName());
    }

    @Test
    public void getEmail() {
        user.setEmail("email@gmail.com");
        assertEquals("email@gmail.com",user.getEmail());
    }

    @Test
    public void getPassword() {
        user.setPassword("1234567");
        assertEquals("1234567",user.getPassword());
    }

    @Test
    public void getHeadImage() {
        user.setHeadImage("image.png");
        assertEquals("image.png",user.getHeadImage());
    }

    @Test
    public void getLocation() {
        user.setLocation("location");
        assertEquals("location",user.getLocation());
    }

    @Test
    public void setId() {
        user.setId("1");
        assertEquals("1",user.getId());
    }

    @Test
    public void setName() {
        user.setName("name");
        assertEquals("name",user.getName());
    }

    @Test
    public void setEmail() {
        user.setEmail("email@gmail.com");
        assertEquals("email@gmail.com",user.getEmail());
    }

    @Test
    public void setPassword() {
        user.setPassword("1234567");
        assertEquals("1234567",user.getPassword());
    }

    @Test
    public void setHeadImage() {
        user.setHeadImage("image.png");
        assertEquals("image.png",user.getHeadImage());

    }

    @Test
    public void setLocation() {
        user.setLocation("location");
        assertEquals("location",user.getLocation());
    }

    @Test
    public void addMessageId() {

    }

    @Test
    public void getMessageIds() {
    }
}