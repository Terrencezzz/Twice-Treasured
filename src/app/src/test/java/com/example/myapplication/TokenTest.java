package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.basicClass.Token;

import org.junit.Before;
import org.junit.Test;

public class TokenTest {
    private Token locationToken;
    private Token categoryToken;
    private Token nameToken;

    // Set up the tokens before each test case
    @Before
    public void setUp() {
        locationToken = new Token("Sydney", Token.Type.LOCATION);
        categoryToken = new Token("Electronic", Token.Type.Category);
        nameToken = new Token("iPhone", Token.Type.NAME);
    }

    // Test the getToken method for correct string conversion and handling
    @Test
    public void testGetToken() {
        assertEquals("sydney", locationToken.getToken());  // Ensure location is processed in lowercase
        assertEquals("electronic", categoryToken.getToken());  // Check if category is correctly lowered
        assertEquals("iphone", nameToken.getToken());  // Verify name token handling
    }

    // Test the getType method for correct type attribution
    @Test
    public void testGetType() {
        assertEquals(Token.Type.LOCATION, locationToken.getType());  // Location type should be correctly identified
        assertEquals(Token.Type.Category, categoryToken.getType());  // Verify category type assignment
        assertEquals(Token.Type.NAME, nameToken.getType());  // Name type should be assigned properly
    }
}
