package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.myapplication.basicClass.FavoriteNotice;
import com.example.myapplication.basicClass.Notice;
import com.example.myapplication.basicClass.NoticeFactory;
import com.example.myapplication.basicClass.UserNotice;

public class NoticeFactoryTest {



    @Test
    public void createNotice(){
        NoticeFactory factory = new NoticeFactory();
        assertNull(factory.createNotice(""));
    }}
