package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.basicClass.Category;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import java.util.ArrayList;
public class CategoryAdapterTest {

    private ArrayList<Category> categories = new ArrayList<>();
    private CategoryAdapter categoryAdapter = null;
    @Before
    public void setUp(){
        Category category_1 = new Category();
        category_1.setId(1);
        category_1.setCategoryName("Name_1");
        category_1.setImagePath("image_1");

        Category category_2 = new Category();
        category_2.setId(2);
        category_2.setCategoryName("Name_2");
        category_2.setImagePath("image_2");

        categories.add(category_1);
        categories.add(category_2);

        categoryAdapter = new CategoryAdapter(categories);
    }

    ViewGroup viewgroup;
    View view;

    @Test
    public void checkItemCount() {
        assertEquals(2,categories.size());
    }

    @Test
    public void checkViewHolder(){

        view = Mockito.mock(View.class);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        assertNotNull(viewHolder);
    }

}
