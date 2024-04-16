package com.example.myapplication.basicClass;

public abstract class Tree<T extends Comparable<T>> {

    public final T value;
    public Tree<T> leftNode;
    public Tree<T> rightNode;

    public Tree() {value = null;}
}
