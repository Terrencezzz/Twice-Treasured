package com.example.myapplication;

import static org.junit.Assert.*;
import com.example.myapplication.common.AVLNode; // Import AVLNode class
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Favorite class.
 * It tests various methods of the Favorite class to ensure their correctness.
 * Author: Xiaojie Zhou (u7769944)
 */
public class AVLNodeTest { // Define a test class named AVLNodeTest

    private AVLNode<Integer> node; // Declare a private AVLNode variable named node
    private AVLNode<Integer> leftChild; // Declare a private AVLNode variable named leftChild
    private AVLNode<Integer> rightChild; // Declare a private AVLNode variable named rightChild

    @Before
    public void setUp() { // Method to set up test data before each test
        leftChild = new AVLNode<>(5); // Create a new AVLNode with key 5 and assign it to leftChild
        rightChild = new AVLNode<>(15); // Create a new AVLNode with key 15 and assign it to rightChild
        node = new AVLNode<>(10, leftChild, rightChild); // Create a new AVLNode with key 10, leftChild, and rightChild
    }

    @Test
    public void testGetKey() { // Test method to check getKey() function
        assertEquals(Integer.valueOf(10), node.getKey()); // Check if the key of the node is 10
    }

    @Test
    public void testGetDuplicates() { // Test method to check getDuplicates() function
        assertTrue(node.getDuplicates().isEmpty()); // Check if the duplicates list of the node is empty

        node.getDuplicates().add(10); // Add a duplicate key (10) to the node
        assertEquals(1, node.getDuplicates().size()); // Check if the size of duplicates list is 1
        assertTrue(node.getDuplicates().contains(10)); // Check if the duplicates list contains the key 10
    }

    @Test
    public void testGetCount() { // Test method to check getCount() function
        assertEquals(1, node.getCount()); // Check if the count of keys in the node is 1

        node.getDuplicates().add(10); // Add a duplicate key (10) to the node
        assertEquals(2, node.getCount()); // Check if the count of keys in the node is 2

        node.getDuplicates().add(10); // Add another duplicate key (10) to the node
        assertEquals(3, node.getCount()); // Check if the count of keys in the node is 3
    }

    @Test
    public void testGetLeftChild() { // Test method to check getLeftChild() function
        assertEquals(leftChild, node.getLeftChild()); // Check if the left child of the node is leftChild
    }

    @Test
    public void testGetRightChild() { // Test method to check getRightChild() function
        assertEquals(rightChild, node.getRightChild()); // Check if the right child of the node is rightChild
    }

    @Test
    public void testToString() { // Test method to check toString() function
        String expectedString = "AVLNode{" +
                "key=10" +
                ", duplicates=[]" +
                ", height=0" +
                ", leftChild=AVLNode{" +
                "key=5" +
                ", duplicates=[]" +
                ", height=0" +
                ", leftChild=null" +
                ", rightChild=null" +
                '}' +
                ", rightChild=AVLNode{" +
                "key=15" +
                ", duplicates=[]" +
                ", height=0" +
                ", leftChild=null" +
                ", rightChild=null" +
                '}' +
                '}';

        assertEquals(expectedString, node.toString()); // Check if the string representation of the node is as expected
    }

    @Test
    public void testGetHeight() { // Test method to check getHeight() function
        assertEquals(0, node.getHeight()); // Check if the height of the node is 0
    }

    @Test
    public void testHeightInitialization() { // Test method to check height initialization
        // This is just an additional test to make sure height is initialized correctly
        assertEquals(0, node.getHeight()); // Check if the height of the node is initialized to 0
    }

    @Test
    public void testNodeWithDuplicates() { // Test method to check node creation with duplicates
        // Testing node with duplicates
        AVLNode<Integer> duplicateNode = new AVLNode<>(20); // Create a new AVLNode with key 20
        duplicateNode.getDuplicates().add(20); // Add a duplicate key (20) to the node
        duplicateNode.getDuplicates().add(20); // Add another duplicate key (20) to the node
        assertEquals(3, duplicateNode.getCount()); // Check if the count of keys in the node is 3 (1 original + 2 duplicates)
    }
}
