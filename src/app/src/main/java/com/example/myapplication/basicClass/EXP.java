package com.example.myapplication.basicClass;

import com.example.myapplication.common.AVLTree;
import java.util.ArrayList;

public abstract class EXP {
    protected AVLTree<Product> productAVLTree;

    public EXP() {
        this.productAVLTree = new AVLTree<>();
    }

    public void setProductAVLTree(AVLTree<Product> tree) {
        this.productAVLTree = tree;
    }

    public abstract ArrayList<Product> search(String keyword);

}
