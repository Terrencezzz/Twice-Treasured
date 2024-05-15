package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * This Tokenizer is aims to support parser
 * @author Wanzhong Wu
 */

public class Tokenizer {
    private String buffer;
    private Token currentToken;
    private ArrayList<String> categoryList = new ArrayList<>(Arrays.asList("electronics", "clothing", "furniture", "books", "sports", "toys", "beauty", "others", "electronic", "clothes", "book", "sport", "toy", "other"));
    private ArrayList<String> locationList = new ArrayList<>(Arrays.asList("sydney", "melbourne", "queensland", "tasmania", "canberra", "south australia"));
    private ArrayList<String> ignoreList = new ArrayList<>(Arrays.asList("a", "the", "and", "or", "with", "i", "for", "in", "from", "at", "an"));
    public Tokenizer(String text) {
        buffer = text.toLowerCase();
        next();
    }


    /**
     * This function aims to keep tracking the key word from the user input.
     */
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

        input = checkTypo(input);
        input = checkTypoWithMiss(input);
        String missCheck = checkMiss(input);
        String duplicateCheck = checkDuplicate(input);

        if (locationList.contains(input) || missCheck.equals("location") || duplicateCheck.equals("location")) {
            currentToken = new Token(input, Token.Type.LOCATION);
        }
        else if (categoryList.contains(input) || missCheck.equals("category") || duplicateCheck.equals("category")) {
            currentToken = new Token(input, Token.Type.Category);
        }
        else if (ignoreList.contains(input)) {
            currentToken = new Token(input, Token.Type.IGNORE);
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


    /**
     * This function aims to check the typo such as "canberra" to "canbeera";
     * @param input
     * @return the correct input
     */
    private String checkTypo(String input) {
        for (String string : locationList) {
            if (string.length() == input.length()) {
                int record = 0;
                for (int i = 0; i < string.length(); i++) {
                    if (string.charAt(i) != input.charAt(i)) record++;
                }
                if (record <= 2) return string;
            }
        }

        for (String string : categoryList) {
            if (string.length() == input.length()) {
                int record = 0;
                for (int i = 0; i < string.length(); i++) {
                    if (string.charAt(i) != input.charAt(i)) record++;
                }
                if (record <= 2) return string;
            }
        }
        return input;
    }


    /**
     * This function aims to check is the input miss some words. Then define it's belong to location
     * or category.
     * @param input
     * @return "location" or "category"
     */
    private String checkMiss(String input) {
        for (String string : locationList) {
            if (string.length() > input.length()) {
                if (string.contains(input) && string.length() - input.length() <= 2) {
                    return "location";
                }
            }
        }

        for (String string : categoryList) {
            if (string.length() > input.length()) {
                if (string.contains(input) && string.length() - input.length() <= 2) {
                    return "category";
                }
            }
        }
        return "other";
    }

    /**
     * This function has a bit wired idea, it will makes "canbera" become "canberr", then the result
     * will be tracked by checkMiss();
     * @param input
     * @return something can be tracked by checkMiss();
     */
    private String checkTypoWithMiss(String input) {
        for (String string : locationList) {
            if (string.length() - input.length() == 1) {
                int record = 0;
                boolean find = false;
                for (int i = 0; i < input.length(); i++) {
                    if (!find) {
                        if (string.charAt(i) != input.charAt(i)) {
                            record++;
                            find = true;
                        }
                    } else {
                        if (string.charAt(i + 1) != input.charAt(i)) record++;
                    }

                }
                if (record == 1) return string.substring(0, string.length() - 1);
            }
        }

        for (String string : categoryList) {
            if (string.length() - input.length() == 1) {
                int record = 0;
                boolean find = false;
                for (int i = 0; i < input.length(); i++) {
                    if (!find) {
                        if (string.charAt(i) != input.charAt(i)) {
                            record++;
                            find = true;
                        }
                    } else {
                        if (string.charAt(i + 1) != input.charAt(i)) record++;
                    }

                }
                if (record == 1) return string.substring(0,string.length()-1);
            }
        }
        return input;
    }

    /**
     * This function aims to check is there something like "canberraa", then define it's location or
     * category.
     * @param input
     * @return "location" or "category"
     */
    private String checkDuplicate(String input) {
        for (String string : locationList) {
            if (string.length() < input.length()) {
                if (input.contains(string)) {
                    return "location";
                }
            }
        }

        for (String string : categoryList) {
            if (string.length() < input.length()) {
                if (input.contains(string)) {
                    return "category";
                }
            }
        }
        return "other";
    }
}
