package com.example.myapplication.basicClass;

import java.lang.reflect.Type;

public class Token {

    public enum Type{LOCATION, Category, NAME, AMOUNT};

    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token.toLowerCase();
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }
}
