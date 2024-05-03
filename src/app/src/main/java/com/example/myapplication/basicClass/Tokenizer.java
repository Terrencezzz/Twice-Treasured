package com.example.myapplication.basicClass;

public class Tokenizer {
    private String buffer;
    private Token currentToken;
    private final String categoryCheck = "electronic, clothing, furniture, books, sports, toys, beauty";
    private final String locationCheck = "sydney, melbourne, queensland, adelaide, tasmania, canberra, south australia";

    public Tokenizer(String text) {
        buffer = text.toLowerCase();
        next();
    }


    public void next(){
        buffer = buffer.trim();

        if (buffer.isEmpty()) {
            currentToken = null;
            return;
        }
        String input = "";
        boolean keepChecking = true;
        int n = 0;

        while (keepChecking && n < buffer.length()) {
            char check = buffer.charAt(n);
            if (check != ' ') {
                input += check;
                n++;
            }
            else {
                keepChecking = false;
            }
        }
        if (categoryCheck.contains(input)) {
            currentToken = new Token(input, Token.Type.Category);
        }
        else if (locationCheck.contains(input)) {
            currentToken = new Token(input, Token.Type.LOCATION);
        }
        else if (input.equals("a")) {
            currentToken = new Token(input, Token.Type.AMOUNT);
        }
        else {
            currentToken = new Token(input, Token.Type.NAME);
        }

        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    public Token current() {
        {
            return currentToken;
        }
    }

    public boolean hasNext() {
        {
            return currentToken != null;
        }
    }
}
