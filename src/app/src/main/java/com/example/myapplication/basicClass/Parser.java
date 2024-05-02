package com.example.myapplication.basicClass;

public class Parser {
    Tokenizer tokenizer;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Parser(String text) {
        this.tokenizer = new Tokenizer(text);
    }

}
