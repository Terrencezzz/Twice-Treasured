package com.example.myapplication.Activities;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * This is a home page, after you login you come to this page.
 * This page will contain the search and some advertisements.
 */
public class HomePage extends Page {

    private  ImageView btnSearch;
    private TextView btnPrivate;
    private TextView btnHome;
    private TextView btnMe;
    private TextView btnFavorite;
    private Button btnTradePlatform;
    private  TextView btnViewmore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initCategory();

        btnSearch = findViewById(R.id.btnSearch);
        btnPrivate = findViewById(R.id.btnPrivate);
        btnHome = findViewById(R.id.btnHome);
        btnMe = findViewById(R.id.btnMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        btnFavorite= findViewById(R.id.btnFavorite);
        btnViewmore = findViewById(R.id.btnViewmore);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomePage();
            }
        });

        btnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserPage();
            }
        });

        btnTradePlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, TradePlatform.class));
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFavorite();
            }
        });

        btnPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, PrivateChat.class));
            }
        });
    }

    private void initCategory() {
        DatabaseReference  db =database.getReference("Category");
        ProgressBar pbCategory = findViewById(R.id.pbCategory);
        pbCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> categories = new ArrayList<>();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        categories.add(issue.getValue(Category.class));
                    }
                    if(categories.size()>0){
                        RecyclerView rvCategory = findViewById(R.id.rvCategory);
                        rvCategory.setLayoutManager(new GridLayoutManager(HomePage.this,4));
                        RecyclerView.Adapter<CategoryAdapter.ViewHolder> adapter  = new CategoryAdapter(categories);
                        rvCategory.setAdapter(adapter);
                    }

                    pbCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}