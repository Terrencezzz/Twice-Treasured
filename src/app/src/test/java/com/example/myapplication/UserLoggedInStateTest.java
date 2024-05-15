package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.basicClass.UserLoggedInState;

public class UserLoggedInStateTest {

    GlobalVariables globalVariables;


    @Before
    public void setUp(){
        globalVariables = GlobalVariables.getInstance();

    }


    @Test
    public void addLoginUserTest(){
        User user = new User();
        user.setId("1");
        globalVariables.setState(new UserLoggedInState());
        globalVariables.addLoginUser(user);
        assertNotNull(globalVariables.getLoginUser());

    }

    @Test
    public void removeLoginUser(){

        User user = new User();
        user.setId("1");
        globalVariables.setState(new UserLoggedInState());
        globalVariables.addLoginUser(user);
        globalVariables.removeLoginUser();
        assertNotNull(globalVariables.getLoginUser());
    }



}
