package com.example.myapplication.basicClass;

public class UserLoggedInState implements UserState{

    @Override
    public void addLoginUser(User loginUser) {
        GlobalVariables globalVars = GlobalVariables.getInstance();
        globalVars.setLoginUser(loginUser);
    }

    @Override
    public void removeLoginUser() {
        System.out.println("You're not a logged out user.");
    }
}
