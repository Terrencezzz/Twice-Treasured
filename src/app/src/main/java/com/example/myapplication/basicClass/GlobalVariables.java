package com.example.myapplication.basicClass;

import java.util.ArrayList;

public class GlobalVariables {

    /**
     * Add more global variables as you need
     * */
    private static GlobalVariables instance;
    private User loginUser;
    private ArrayList<Category> categoryList;

    private GlobalVariables(){};

    public static synchronized GlobalVariables getInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }






}
