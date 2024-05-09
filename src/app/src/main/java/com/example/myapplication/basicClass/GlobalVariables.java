package com.example.myapplication.basicClass;

import java.util.ArrayList;

public class GlobalVariables {

    /**
     * Add more global variables as you need
     * */
    private static GlobalVariables instance;
    private User loginUser;
    private ArrayList<Category> categoryList;
    private UserState state;

    private GlobalVariables(){
        this.state = new UserLoggedOutState();
    };

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

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void addLoginUser(User loginUser) {
        state.addLoginUser(loginUser);
    }

    public void removeLoginUser() {
        state.removeLoginUser();
    }

    /**
     * Determine if the user is in visitor mode.
     *
     * @return true if the user is in visitor mode, false otherwise.
     */
    public boolean isVisitorMode() {
        return state instanceof UserLoggedOutState;
    }

}
