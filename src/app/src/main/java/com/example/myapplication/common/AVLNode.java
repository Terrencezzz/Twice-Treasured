package com.example.myapplication.common;

import java.util.ArrayList;

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

    // Getter method for duplicates list
    public ArrayList<T> getDuplicates() {
        return duplicates;
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
}
