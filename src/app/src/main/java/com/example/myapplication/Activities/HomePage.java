package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.Adapters.RecommendAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Category;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * This is a home page, after you login you come to this page.
 * This page will contain the search and some advertisements.
 */
public class HomePage extends Page {

    private ImageView btnSearch;
    private ImageView btnNotification;
    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;
    private  TextView btnViewmore;
    private TextView input;
    private TextView txtUserName;

    FirebaseDatabase database;
    GlobalVariables globalVars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        database = Database.getDatabase();
        globalVars = GlobalVariables.getInstance();

        btnSearch = findViewById(R.id.btnSearch);
        btnNotification = findViewById(R.id.btnNotification);
        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);
        btnViewmore = findViewById(R.id.btnViewmore);
        input = findViewById(R.id.input);
        txtUserName = findViewById(R.id.txtUserName);

        initLoginUser();
        initCategory();
        initRecommend();

        clMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserPage();
            }
        });

        btnTradePlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTradePage();
            }
        });

        clFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFavorite();
            }
        });

        clPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goPrivateMenu();}
        });

        btnNotification.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, Notification.class);
            startActivity(intent);
            //do not use finish() here, otherwise it cannot back to here
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = input.getText().toString(); // Get the string entered by the user
                Intent intent = new Intent(HomePage.this, SearchResultPage.class);
                intent.putExtra("HomeSearchString", userInput); // Pass user input as string to SearchResultPage
                startActivity(intent); // Go to SearchResultPage
            }
        });


    }
    /**
     *  Reload username
     * */
    @Override
    protected void onStart() {
        super.onStart();
        if(globalVars.getLoginUser()!=null){
            txtUserName.setText(globalVars.getLoginUser().getName());
        }
    }

    private void initLoginUser() {
        String userEmail = getIntent().getStringExtra("email");
        DatabaseReference  db = database.getReference("User");
        Query query = db.orderByChild("email").equalTo(userEmail);
        ArrayList<User> users = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    users.clear();
                    for (DataSnapshot issue:snapshot.getChildren()){
                        users.add(issue.getValue(User.class));
                    }
                    if(users.size()>0){
                        globalVars.setLoginUser(users.get(0));
                        txtUserName.setText(globalVars.getLoginUser().getName());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initRecommend() {
        DatabaseReference  db =database.getReference("Product");
        ProgressBar pbRecommend = findViewById(R.id.pbRecommend);
        pbRecommend.setVisibility(View.VISIBLE);
        ArrayList<Product> products = new ArrayList<>();
        /*
        * Need Add Query Condition
        * */
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        products.add(issue.getValue(Product.class));
                    }

                    if(products.size()>0){
                        RecyclerView rvRecommend = findViewById(R.id.rvRecommend);
                        rvRecommend.setLayoutManager(new LinearLayoutManager(HomePage.this,LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter<RecommendAdapter.ViewHolder> adapter  = new RecommendAdapter(products, HomePage.this);
                        rvRecommend.setAdapter(adapter);
                    }

                    pbRecommend.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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