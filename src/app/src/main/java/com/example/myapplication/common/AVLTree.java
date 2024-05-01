package com.example.myapplication.common;

import java.util.ArrayList;

// Definition of an AVL Tree class that supports generic types with Comparable interface
public class AVLTree<T extends Comparable<? super T>> {
    private AVLNode<T> root;  // Root of the AVL Tree.
    private static final int ALLOWED_IMBALANCE = 1;  // Maximum height difference allowed between subtrees.

    // Constructor to initialize an empty AVL Tree.
    public AVLTree() {
        root = null;
    }

    // Public method to insert a key into the AVL Tree.
    public void insert(T key) {
        root = insertIntoTree(key, root);
    }

    // Public method to remove a key from the AVL Tree.
    public void remove(T key) {
        root = removeFromTree(key, root);
    }

    // Public method to find the smallest key in the AVL Tree.
    public T findMinimum() {
        return findMinNode(root).key;
    }

    // Public method to clear the AVL Tree, removing all nodes.
    public void clear() {
        root = null;
    }

    // Public method to check if the AVL Tree is empty.
    public boolean isEmpty() {
        return root == null;
    }

    // Private recursive method to insert a key into the AVL Tree, maintaining the balance.
    private AVLNode<T> insertIntoTree(T key, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<>(key);
        }

        int compareResult = key.compareTo(node.key);

        if (compareResult < 0) {
            node.leftChild = insertIntoTree(key, node.leftChild);
        } else if (compareResult > 0) {
            node.rightChild = insertIntoTree(key, node.rightChild);
        } else {
            // Handle duplicate keys by adding them to the duplicates list of the node.
            node.duplicates.add(key);
        }
        return balance(node);  // Balance the tree after insertion.
    }

    // Private recursive method to remove a key from the AVL Tree, maintaining the balance.
    private AVLNode<T> removeFromTree(T key, AVLNode<T> node) {
        if (node == null) {
            return node;
        }

        int compareResult = key.compareTo(node.key);

        if (compareResult < 0) {
            node.leftChild = removeFromTree(key, node.leftChild);
        } else if (compareResult > 0) {
            node.rightChild = removeFromTree(key, node.rightChild);
        } else if (node.leftChild != null && node.rightChild != null) {
            // Both children exist, replace with the minimum key from the right subtree.
            node.key = findMinNode(node.rightChild).key;
            node.rightChild = removeFromTree(node.key, node.rightChild);
        } else {
            // Replace node with its only child or null if it has no children.
            node = (node.leftChild != null) ? node.leftChild : node.rightChild;
        }
        return balance(node);  // Balance the tree after removal.
    }

    // Method to calculate the height of a node.
    private int nodeHeight(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    // Method to balance the AVL Tree, making necessary rotations.
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node == null) {
            return node;
        }

        if (nodeHeight(node.leftChild) - nodeHeight(node.rightChild) > ALLOWED_IMBALANCE) {
            if (nodeHeight(node.leftChild.leftChild) >= nodeHeight(node.leftChild.rightChild)) {
                node = rotateWithLeftChild(node);
            } else {
                node = doubleRotateWithLeftChild(node);
            }
        } else if (nodeHeight(node.rightChild) - nodeHeight(node.leftChild) > ALLOWED_IMBALANCE) {
            if (nodeHeight(node.rightChild.rightChild) >= nodeHeight(node.rightChild.leftChild)) {
                node = rotateWithRightChild(node);
            } else {
                node = doubleRotateWithRightChild(node);
            }
        }
        // Update the height of the node.
        node.height = Math.max(nodeHeight(node.leftChild), nodeHeight(node.rightChild)) + 1;
        return node;
    }

    // Double rotation on right-heavy subtree to balance.
    private AVLNode<T> doubleRotateWithRightChild(AVLNode<T> node) {
        node.rightChild = rotateWithLeftChild(node.rightChild);
        return rotateWithRightChild(node);
    }

    // Right rotation to balance left-heavy subtree.
    private AVLNode<T> rotateWithRightChild(AVLNode<T> node) {
        AVLNode<T> newRoot = node.rightChild;
        node.rightChild = newRoot.leftChild;
        newRoot.leftChild = node;
        node.height = Math.max(nodeHeight(node.rightChild), nodeHeight(node.leftChild)) + 1;
        newRoot.height = Math.max(nodeHeight(newRoot.rightChild), node.height) + 1;
        return newRoot;
    }

    private AVLNode<T> doubleRotateWithLeftChild(AVLNode<T> node) {
        // Perform a right rotation on the left child of the given node
        node.leftChild = rotateWithRightChild(node.leftChild);
        // Perform a left rotation on the given node
        return rotateWithLeftChild(node);
    }

    private AVLNode<T> rotateWithLeftChild(AVLNode<T> node) {
        // Perform a left rotation and return the new root
        AVLNode<T> newRoot = node.leftChild;
        node.leftChild = newRoot.rightChild;
        newRoot.rightChild = node;
        // Update the heights of the nodes
        node.height = Math.max(nodeHeight(node.leftChild), nodeHeight(node.rightChild)) + 1;
        newRoot.height = Math.max(nodeHeight(newRoot.leftChild), node.height) + 1;
        return newRoot;
    }

    private AVLNode<T> findMinNode(AVLNode<T> node) {
        // Find and return the node with the minimum key value
        while (node != null && node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    private AVLNode<T> findMaxNode(AVLNode<T> node) {
        // Find and return the node with the maximum key value
        if (node == null) {
            return null;
        } else if (node.rightChild == null) {
            return node;
        } else {
            return findMaxNode(node.rightChild);
        }
    }

    public AVLNode<T> search(AVLNode<T> currentNode, T target) {
        // Search for a node with the given target key value
        if (currentNode == null) {
            return null;
        }
        int compareResult = currentNode.key.compareTo(target);
        if (compareResult < 0) {
            return search(currentNode.rightChild, target);
        } else if (compareResult > 0) {
            return search(currentNode.leftChild, target);
        } else {
            return currentNode;
        }
    }

    public void searchGreaterThan(AVLNode<T> currentNode, T threshold, ArrayList<T> results) {
        // Search for nodes with key values greater than the threshold
        if (currentNode == null) {
            return;
        }
        int compareResult = currentNode.key.compareTo(threshold);
        if (compareResult > 0) {
            results.add(currentNode.key);
            // Add duplicates if present
            if (!currentNode.duplicates.isEmpty()) {
                results.addAll(currentNode.duplicates);
            }
            // Recursively search in both left and right subtrees
            searchGreaterThan(currentNode.leftChild, threshold, results);
            searchGreaterThan(currentNode.rightChild, threshold, results);
        } else {
            // If the current node's key is not greater than the threshold, search only in the right subtree
            searchGreaterThan(currentNode.rightChild, threshold, results);
        }
    }

    public void searchGreaterOrEqualTo(AVLNode<T> currentNode, T threshold, ArrayList<T> results) {
        // Search for nodes with key values greater than or equal to the threshold
        if (currentNode == null) {
            return;
        }
        int compareResult = currentNode.key.compareTo(threshold);
        if (compareResult >= 0) {
            results.add(currentNode.key);
            // Add duplicates if present
            if (!currentNode.duplicates.isEmpty()) {
                results.addAll(currentNode.duplicates);
            }
            // Recursively search in both left and right subtrees
            searchGreaterOrEqualTo(currentNode.leftChild, threshold, results);
            searchGreaterOrEqualTo(currentNode.rightChild, threshold, results);
        } else {
            // If the current node's key is not greater than or equal to the threshold, search only in the right subtree
            searchGreaterOrEqualTo(currentNode.rightChild, threshold, results);
        }
    }


    public void searchLessThan(AVLNode<T> currentNode, T threshold, ArrayList<T> results) {
        // Search for nodes with key values less than the threshold
        if (currentNode == null) {
            return;
        }
        int compareResult = currentNode.key.compareTo(threshold);
        if (compareResult < 0) {
            results.add(currentNode.key);
            // Add duplicates if present
            if (!currentNode.duplicates.isEmpty()) {
                results.addAll(currentNode.duplicates);
            }
            // Recursively search in both right and left subtrees
            searchLessThan(currentNode.rightChild, threshold, results);
            searchLessThan(currentNode.leftChild, threshold, results);
        } else {
            // If the current node's key is not less than the threshold, search only in the left subtree
            searchLessThan(currentNode.leftChild, threshold, results);
        }
    }

    public void searchLessOrEqualTo(AVLNode<T> currentNode, T threshold, ArrayList<T> results) {
        // Search for nodes with key values less than or equal to the threshold
        if (currentNode == null) {
            return;
        }
        int compareResult = currentNode.key.compareTo(threshold);
        if (compareResult <= 0) {
            results.add(currentNode.key);
            // Add duplicates if present
            if (!currentNode.duplicates.isEmpty()) {
                results.addAll(currentNode.duplicates);
            }
            // Recursively search in both right and left subtrees
            searchLessOrEqualTo(currentNode.rightChild, threshold, results);
            searchLessOrEqualTo(currentNode.leftChild, threshold, results);
        } else {
            // If the current node's key is not less than or equal to the threshold, search only in the left subtree
            searchLessOrEqualTo(currentNode.leftChild, threshold, results);
        }
    }

    public ArrayList<T> convertToArrayList() {
        // Convert the AVL tree to an ArrayList using pre-order traversal
        ArrayList<T> resultList = new ArrayList<>();
        preOrderTraversal(root, resultList);
        return resultList;
    }

    private void preOrderTraversal(AVLNode<T> currentNode, ArrayList<T> resultList) {
        // Perform pre-order traversal of the AVL tree and add elements to the result list
        if (currentNode == null) {
            return;
        }
        resultList.add(currentNode.key);
        // Add duplicates if present
        if (!currentNode.getDuplicates().isEmpty()) {
            resultList.addAll(currentNode.duplicates);
        }
        // Recursively traverse the left and right subtrees
        preOrderTraversal(currentNode.leftChild, resultList);
        preOrderTraversal(currentNode.rightChild, resultList);
    }

}
