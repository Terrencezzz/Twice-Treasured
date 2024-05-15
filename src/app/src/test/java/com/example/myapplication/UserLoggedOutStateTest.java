package com.example.myapplication;

import static org.junit.Assert.*;

import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.basicClass.UserLoggedInState;
import com.example.myapplication.basicClass.UserLoggedOutState;

import org.junit.Before;
import org.junit.Test;

public class UserLoggedOutStateTest {

    GlobalVariables globalVariables;


    @Before
    public void setUp(){
        globalVariables = GlobalVariables.getInstance();

    }

    @Test
    public void addLoginUserTest(){

        User user = new User();
        user.setId("1");

        globalVariables.setState(new UserLoggedOutState());
        globalVariables.removeLoginUser();
        globalVariables.addLoginUser(user);
        assertNull(globalVariables.getLoginUser());

    }

    @Test
    public void removeLoginUser(){

        globalVariables.setState(new UserLoggedOutState());
        globalVariables.removeLoginUser();
        assertNull(globalVariables.getLoginUser());
    }
}
