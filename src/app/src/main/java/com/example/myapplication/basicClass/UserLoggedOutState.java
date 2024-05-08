package com.example.myapplication.basicClass;

public class UserLoggedOutState implements UserState{

    @Override
    public void addLoginUser(User loginUser) {
        System.out.println("You're not a logged in user.");
    }

    @Override
    public void removeLoginUser() {
        GlobalVariables globalVars = GlobalVariables.getInstance();
        globalVars.setLoginUser(null);
    }
}
