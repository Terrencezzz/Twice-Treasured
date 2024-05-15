package com.example.myapplication;

import com.example.myapplication.basicClass.Product;
import com.example.myapplication.common.AVLTree;
import com.example.myapplication.basicClass.Parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ParserTest {
    private AVLTree<Product> total = new AVLTree<>();

    Product product1 = new Product(
            "Smartphone",
            "P001",
            "Electronics",
            "A high-end smartphone with a 6.5-inch display.",
            "999",
            "New",
            "2024-01-10",
            "Available",
            "http://example.com/images/smartphone.jpg",
            "O001",
            "C001",
            "Canberra"
    );

    Product product2 = new Product(
            "Laptop",
            "P002",
            "Electronics",
            "A lightweight laptop with a 14-inch screen.",
            "1299",
            "New",
            "2024-02-15",
            "Available",
            "http://example.com/images/laptop.jpg",
            "O002",
            "C001",
            "Sydney"
    );

    Product product3 = new Product(
            "Headphones",
            "P003",
            "electronics",
            "Noise-cancelling over-ear headphones.",
            "199",
            "New",
            "2024-03-20",
            "Available",
            "http://example.com/images/headphones.jpg",
            "O003",
            "C002",
            "Canberra"
    );

    Product product4 = new Product(
            "Coffee Maker",
            "P004",
            "other",
            "Automatic drip coffee maker with a 12-cup capacity.",
            "89",
            "New",
            "2024-04-05",
            "Available",
            "http://example.com/images/coffeemaker.jpg",
            "O004",
            "C003",
            "Melbourne"
    );

    Product product5 = new Product(
            "Gaming Console",
            "P005",
            "electronic",
            "Next-gen gaming console with 1TB storage.",
            "499",
            "New",
            "2024-05-10",
            "Available",
            "http://example.com/images/console.jpg",
            "O005",
            "C004",
            "Queensland"
    );

    @Before
    public void setup() {
        total.insert(product1);
        total.insert(product2);
        total.insert(product3);
        total.insert(product4);
        total.insert(product5);
    }

    @Test
    public void simpleTest() {
        String input = "canberra";
        Parser parser = new Parser(input);
        AVLTree<Product> result = parser.parseEXP(total);

        AVLTree<Product> expectResult = new AVLTree<>();
        expectResult.insert(product1);
        expectResult.insert(product3);

        ArrayList<Product> resultInOrder = result.convertToAscendingArrayList();
        ArrayList<Product> expectResultInOrder = expectResult.convertToAscendingArrayList();

        Assert.assertEquals(resultInOrder, expectResultInOrder);
    }

    @Test
    public void typoTest1() {
        String input = "canberr";
        Parser parser = new Parser(input);
        AVLTree<Product> result = parser.parseEXP(total);

        AVLTree<Product> expectResult = new AVLTree<>();
        expectResult.insert(product1);
        expectResult.insert(product3);

        ArrayList<Product> resultInOrder = result.convertToAscendingArrayList();
        ArrayList<Product> expectResultInOrder = expectResult.convertToAscendingArrayList();

        Assert.assertEquals(resultInOrder, expectResultInOrder);
    }

    @Test
    public void typoTest2() {
        String input = "canberraa";
        Parser parser = new Parser(input);
        AVLTree<Product> result = parser.parseEXP(total);

        AVLTree<Product> expectResult = new AVLTree<>();
        expectResult.insert(product1);
        expectResult.insert(product3);

        ArrayList<Product> resultInOrder = result.convertToAscendingArrayList();
        ArrayList<Product> expectResultInOrder = expectResult.convertToAscendingArrayList();

        Assert.assertEquals(resultInOrder, expectResultInOrder);
    }

    @Test
    public void typoTest3() {
        String input = "canbeera";
        Parser parser = new Parser(input);
        AVLTree<Product> result = parser.parseEXP(total);

        AVLTree<Product> expectResult = new AVLTree<>();
        expectResult.insert(product1);
        expectResult.insert(product3);

        ArrayList<Product> resultInOrder = result.convertToAscendingArrayList();
        ArrayList<Product> expectResultInOrder = expectResult.convertToAscendingArrayList();

        Assert.assertEquals(resultInOrder, expectResultInOrder);
    }

    @Test
    public void nameTest() {
        String input = "I need a Coffee Maker";
        Parser parser = new Parser(input);
        AVLTree<Product> result = parser.parseEXP(total);

        AVLTree<Product> expectResult = new AVLTree<>();
        expectResult.insert(product4);

        ArrayList<Product> resultInOrder = result.convertToAscendingArrayList();
        ArrayList<Product> expectResultInOrder = expectResult.convertToAscendingArrayList();

        Assert.assertEquals(resultInOrder, expectResultInOrder);
    }

    @Test
    public void categoryTest() {
        String input = "I need some electronic";
        Parser parser = new Parser(input);
        AVLTree<Product> result = parser.parseEXP(total);

        AVLTree<Product> expectResult = new AVLTree<>();
        expectResult.insert(product1);
        expectResult.insert(product2);
        expectResult.insert(product3);
        expectResult.insert(product5);

        ArrayList<Product> resultInOrder = result.convertToAscendingArrayList();
        ArrayList<Product> expectResultInOrder = expectResult.convertToAscendingArrayList();

        Assert.assertEquals(resultInOrder, expectResultInOrder);
    }
}
