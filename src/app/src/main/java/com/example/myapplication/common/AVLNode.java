package com.example.myapplication.common;

import java.util.ArrayList;

/**
 * This class represents a node in an AVL tree.
 * It stores a key, maintains a list of duplicate keys, and tracks the height of the node.
 * @author Xiaojie Zhou (u7769944)
 */
public class AVLNode<T extends Comparable<? super T>> {
    T key; // Key of the node
    ArrayList<T> duplicates; // List to store duplicate keys
    int height; // Height of the node in the AVL tree
    AVLNode<T> leftChild; // Reference to the left child node
    AVLNode<T> rightChild; // Reference to the right child node

    // Constructor with key only
    public AVLNode(T key) {
        this(key, null, null);
    }

    // Constructor with key, left child, and right child
    public AVLNode(T key, AVLNode<T> leftChild, AVLNode<T> rightChild) {
        this.key = key;
        this.duplicates = new ArrayList<>(); // Initialize the list for duplicates
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.height = 0; // Initialize height to 0
    }

    // Getter method for the key
    public T getKey() {
        return key;
    }

    public int getHeight() {
        return height;
    }

    // Getter method for duplicates list
    public ArrayList<T> getDuplicates() {
        return duplicates;
    }

    public int getCount() {
        return duplicates.size() + 1; // +1 for the key itself
    }

    // Override toString method to provide a string representation of the node
    @Override
    public String toString() {
        return "AVLNode{" +
                "key=" + key +
                ", duplicates=" + duplicates +
                ", height=" + height +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }

    public AVLNode<T> getLeftChild() {
        return leftChild;
    }

    public AVLNode<T> getRightChild() {
        return rightChild;
    }

}
