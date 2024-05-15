package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.basicClass.Category;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    private Category category = new Category();

    @Before
    public void setUp(){
        category.setId(1);
        category.setCategoryName("CategoryName");
        category.setImagePath("http://www.category.com/category.png");

    }

    @Test
    public void getId() {
        assertEquals(1,category.getId());
    }

    @Test
    public void setId() {
        Category category1 = new Category();
        category1.setId(2);
        assertEquals(2,category1.getId());
    }

    @Test
    public void getCategoryName() {
        assertEquals("CategoryName",category.getCategoryName());
    }

    @Test
    public void setCategoryName() {
        Category category2 = new Category();
        category2.setCategoryName("name");
        assertEquals("name",category2.getCategoryName());
    }

    @Test
    public void getImagePath() {
        assertEquals("http://www.category.com/category.png",category.getImagePath());
    }

    @Test
    public void setImagePath() {
        Category category3 = new Category();
        category3.setImagePath("image-path");
        assertEquals("image-path",category3.getImagePath());
    }
}
