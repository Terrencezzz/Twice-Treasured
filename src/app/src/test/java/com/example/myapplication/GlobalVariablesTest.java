package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.myapplication.basicClass.Category;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.basicClass.UserLoggedInState;
import com.example.myapplication.basicClass.UserLoggedOutState;
import com.example.myapplication.basicClass.UserState;

import java.util.ArrayList;

public class GlobalVariablesTest {

    GlobalVariables globalVariables;

    @Before
    public void setUp(){
        globalVariables = GlobalVariables.getInstance();
    }

    @Test
    public void getInstance() {
        assertNotNull(GlobalVariables.getInstance());
    }

    @Test
    public void getLoginUser() {
        User user = new User();
        user.setId("1");
        globalVariables.setLoginUser(user);
        assertNotNull(globalVariables.getLoginUser());
    }

    @Test
    public void setLoginUser() {
        User user = new User();
        user.setId("1");
        globalVariables.setLoginUser(user);
        assertNotNull(globalVariables.getLoginUser());
    }

    @Test
    public void getCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setId(1);
        categories.add(category);
        globalVariables.setCategoryList(categories);
        assertNotNull(globalVariables.getCategoryList());
    }

    @Test
    public void setCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setId(1);
        categories.add(category);
        globalVariables.setCategoryList(categories);
        assertNotNull(globalVariables.getCategoryList());
    }

    @Test
    public void getState() {
        globalVariables.setState(new UserLoggedOutState());
        assertNotNull(globalVariables.getState());
    }

    @Test
    public void setState() {
        globalVariables.setState(new UserLoggedOutState());
        assertNotNull(globalVariables.getState());
    }

    @Test
    public void addLoginUser() {
        User user = new User();
        user.setId("1");
        globalVariables.setState(new UserLoggedInState());
        globalVariables.addLoginUser(user);
        assertNotNull(globalVariables.getLoginUser());
    }

    @Test
    public void removeLoginUser() {
        globalVariables.setState(new UserLoggedOutState());
        globalVariables.removeLoginUser();
        assertNull(globalVariables.getLoginUser());
    }

    @Test
    public void isVisitorMode() {
        globalVariables.setState(new UserLoggedOutState());
        assertEquals(true,globalVariables.isVisitorMode());

    }


}
