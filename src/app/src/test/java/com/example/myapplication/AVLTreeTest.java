package com.example.myapplication;

import static org.junit.Assert.*;
import com.example.myapplication.common.AVLTree;
import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest {

    private AVLTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new AVLTree<>();
    }

    // Test insertion and automatic balancing of the AVL tree.
    @Test
    public void testInsertionAndBalance() {
        // Insert elements to cause a right rotation (LL case)
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        assertEquals(Integer.valueOf(2), tree.getRoot().getKey());
        assertEquals(Integer.valueOf(1), tree.getRoot().getLeftChild().getKey()); // Changed to getLeftChild()
        assertEquals(Integer.valueOf(3), tree.getRoot().getRightChild().getKey()); // Changed to getRightChild()

        // Insert elements to cause a left rotation (RR case)
        tree.insert(4);
        tree.insert(5);
        assertEquals(Integer.valueOf(2), tree.getRoot().getKey());
        assertEquals(Integer.valueOf(4), tree.getRoot().getRightChild().getKey());
        assertEquals(Integer.valueOf(5), tree.getRoot().getRightChild().getRightChild().getKey());
    }

    // Test removal of elements and check if the tree remains balanced after removal.
    @Test
    public void testDeletionAndBalance() {
        // Setting up a tree
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        // Remove element with two children
        tree.remove(30);
        assertNull(tree.search(tree.getRoot(), 30)); // Simplified assertion
        assertNotNull(tree.search(tree.getRoot(), 20));
        assertNotNull(tree.search(tree.getRoot(), 40));

        // Ensure the tree remains balanced
        int rootBalance = tree.nodeHeight(tree.getRoot().getLeftChild()) - tree.nodeHeight(tree.getRoot().getRightChild());
        assertTrue(Math.abs(rootBalance) <= 1);
    }

    // Test to find the minimum value in the AVL tree.
    @Test
    public void testFindMinimum() {
        tree.insert(40);
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(60);
        assertEquals(Integer.valueOf(10), tree.findMinimum());
    }

    // Test the tree is empty initially and after removals.
    @Test
    public void testEmptyTree() {
        assertTrue(tree.isEmpty());
        tree.insert(20);
        tree.remove(20);
        assertTrue(tree.isEmpty());
    }

    // Test searching functionality of the AVL tree.
    @Test
    public void testSearch() {
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        assertNotNull(tree.search(tree.getRoot(), 30));
        assertNull(tree.search(tree.getRoot(), 90));
    }
}
