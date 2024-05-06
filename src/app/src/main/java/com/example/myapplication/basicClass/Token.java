package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

public class Token {

    public enum Type{LOCATION, Category, NAME, IGNORE};

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

    @NonNull
    @Override
    public String toString() {
        if (type == Type.LOCATION) {
            return "LOCATION: " + token + " ";
        }
        else if (type == Type.Category) {
            return "CATEGORY " + token + " ";
        }
        else if (type == Type.IGNORE) {
            return "IGNORE " + token + " ";
        }
        else return "NAME " + token + " ";
    }
}
