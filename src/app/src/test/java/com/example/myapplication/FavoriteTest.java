package com.example.myapplication;

import static org.junit.Assert.*;

// Import necessary classes
import com.example.myapplication.basicClass.Favorite; // Import Favorite class
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Favorite class.
 * @author Xiaojie Zhou (u7769944)
 */
public class FavoriteTest {

    private Favorite favorite; // Declare a private Favorite variable named favorite

    @Before
    public void setUp() { // Method to set up test data before each test
        favorite = new Favorite("fav123", "1pT81l19tLX0shrGzm7iNAVyArv2", "-NwQx3TZI9r4GUmtBpbrc", true); // Create a new Favorite object with specified parameters
    }

    @Test
    public void testGetFavoriteID() { // Test method to check getFavoriteID() function
        assertEquals("fav123", favorite.getFavoriteID()); // Check if the favorite ID is "fav123"
    }

    @Test
    public void testSetFavoriteID() { // Test method to check setFavoriteID() function
        favorite.setFavoriteID("fav321"); // Set a new favorite ID
        assertEquals("fav321", favorite.getFavoriteID()); // Check if the favorite ID is updated to "fav321"
    }

    @Test
    public void testGetUserID() { // Test method to check getUserID() function
        assertEquals("1pT81l19tLX0shrGzm7iNAVyArv2", favorite.getUserID()); // Check if the user ID is "1pT81l19tLX0shrGzm7iNAVyArv2"
    }

    @Test
    public void testSetUserID() { // Test method to check setUserID() function
        favorite.setUserID("1q6k9lFufdcrib7UjDaFO1yPpGf1"); // Set a new user ID
        assertEquals("1q6k9lFufdcrib7UjDaFO1yPpGf1", favorite.getUserID()); // Check if the user ID is updated to "1q6k9lFufdcrib7UjDaFO1yPpGf1"
    }

    @Test
    public void testGetProductID() { // Test method to check getProductID() function
        assertEquals("-NwQx3TZI9r4GUmtBpbrc", favorite.getProductID()); // Check if the product ID is "-NwQx3TZI9r4GUmtBpbrc"
    }

    @Test
    public void testSetProductID() { // Test method to check setProductID() function
        favorite.setProductID("-NxBJQkCnmQqH1o3yGqtV"); // Set a new product ID
        assertEquals("-NxBJQkCnmQqH1o3yGqtV", favorite.getProductID()); // Check if the product ID is updated to "-NxBJQkCnmQqH1o3yGqtV"
    }

    @Test
    public void testIsFavoriteStatus() { // Test method to check isFavoriteStatus() function
        assertTrue(favorite.isFavoriteStatus()); // Check if the favorite status is true
    }

    @Test
    public void testSetFavoriteStatus() { // Test method to check setFavoriteStatus() function
        favorite.setFavoriteStatus(false); // Set the favorite status to false
        assertFalse(favorite.isFavoriteStatus()); // Check if the favorite status is updated to false
    }

    @Test
    public void testDefaultConstructor() { // Test method to check default constructor
        Favorite defaultFavorite = new Favorite(); // Create a new Favorite object using the default constructor
        assertNull(defaultFavorite.getFavoriteID()); // Check if the default favorite ID is null
        assertNull(defaultFavorite.getUserID()); // Check if the default user ID is null
        assertNull(defaultFavorite.getProductID()); // Check if the default product ID is null
        assertFalse(defaultFavorite.isFavoriteStatus()); // Check if the default favorite status is false
    }
}
