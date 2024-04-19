package com.example.myapplication.basicClass;

import java.lang.reflect.Type;

public class Token {

    public enum Type{};

    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

}
