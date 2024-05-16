package com.example.myapplication.Activities;

import static com.example.myapplication.common.CommonHelper.showAlertDialog;

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
import com.example.myapplication.basicClass.UserLoggedInState;
import com.example.myapplication.basicClass.UserLoggedOutState;
import com.example.myapplication.basicClass.UserState;
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
 * @author Wen Li u7706423, Lingjie Qin u7756873, Xiaojie Zhou u7769944
 */
public class HomePage extends Page {

    private ImageView btnSearch;
    private ImageView btnNotification;
    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;
    private TextView input;
    private TextView txtUserName;
    private ImageView btnLogout;
    private ImageView btnLoginAgain;

    FirebaseDatabase database;
    GlobalVariables globalVars;
    private TextView btnFavorite;
    private ImageView imageViewFavorite;
    private RecyclerView rvRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnFavorite = findViewById(R.id.textView16);
        imageViewFavorite = findViewById(R.id.imageView12);
        rvRecommend = findViewById(R.id.rvRecommend);

        database = Database.getDatabase();
        globalVars = GlobalVariables.getInstance();
        UserState userState = globalVars.getState();

        btnSearch = findViewById(R.id.btnSearch);
        btnNotification = findViewById(R.id.btnNotification);
        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite = findViewById(R.id.clFavorite);
        input = findViewById(R.id.input);
        txtUserName = findViewById(R.id.txtUserName);
        btnLogout = findViewById(R.id.btnLogout);
        btnLoginAgain = findViewById(R.id.btnLoginAgain);

        initLoginUser();
        initCategory();
        initRecommend();

        Boolean visiterMode = globalVars.isVisitorMode();
        clMe.setOnClickListener(v -> {

            if (visiterMode) {
                // Visitor mode - show login page
                Intent intent = new Intent(HomePage.this, LoginPage.class);
                startActivity(intent);
            }else {
                goUserPage();
            }
        });

        btnTradePlatform.setOnClickListener(v -> {
            if (visiterMode) {
                // Visitor mode - show login page
                Intent intent = new Intent(HomePage.this, LoginPage.class);
                startActivity(intent);
            }else {
                goTradePage();
            }
        });

        clFavorite.setOnClickListener(v -> {
            if (visiterMode) {
                // Visitor mode - show login page
                Intent intent = new Intent(HomePage.this, LoginPage.class);
                startActivity(intent);
            }else {
                goFavorite();
            }
        });

        clPrivate.setOnClickListener(v -> {
            if (visiterMode) {
                // Visitor mode - show login page
                Intent intent = new Intent(HomePage.this, LoginPage.class);
                startActivity(intent);
            }else {
                goPrivateMenu();
            }
        });

        btnLogout.setOnClickListener(view -> {

            showAlertDialog(HomePage.this,"Security Alert","Confirm to log out?",
                    "Confirm",(dialog, which) -> {
                        globalVars.setState(new UserLoggedOutState());
                        globalVars.removeLoginUser();
                        goIntroPage();
                    },"Cancel", (dialog, which) -> dialog.dismiss());
        });
        btnLoginAgain.setOnClickListener(view ->{
            goLoginPage();
        });

        btnNotification.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, NotificationPage.class);
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

        if (userState instanceof UserLoggedOutState) {
            txtUserName.setText("Visitor");
            btnLogout.setVisibility(View.GONE);
            btnLoginAgain.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Reload username
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (globalVars.getLoginUser() != null) {
            txtUserName.setText(globalVars.getLoginUser().getName());
        }
    }

    /**
     * Initial Login User Info
     * @author Wen Li @u7706423
     * */
    private void initLoginUser() {
        String userEmail = getIntent().getStringExtra("email");
        DatabaseReference db = database.getReference("User");
        Query query = db.orderByChild("email").equalTo(userEmail);
        ArrayList<User> users = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    users.clear();
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        users.add(issue.getValue(User.class));
                    }
                    if (users.size() > 0) {
                        globalVars.setState(new UserLoggedInState());
                        globalVars.addLoginUser(users.get(0));
                        txtUserName.setText(globalVars.getLoginUser().getName());
                        btnLogout.setVisibility(View.VISIBLE);
                        btnLoginAgain.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtUserName.setText("Unknown User");
            }
        });


    }

    private void initRecommend() {
        DatabaseReference db = database.getReference("Product");
        String userLocation = "canberra"; // Default location
        User loginUser = GlobalVariables.getInstance().getLoginUser();

        if (loginUser != null) {
            String userLocationFromUser = loginUser.getLocation();
            if (userLocationFromUser != null && !userLocationFromUser.isEmpty()) {
                userLocation = userLocationFromUser.toLowerCase();
            }
        }

        ProgressBar pbRecommend = findViewById(R.id.pbRecommend);
        pbRecommend.setVisibility(View.VISIBLE);
        ArrayList<Product> products = new ArrayList<>();

        // Query products based on user location
        Query query = db.orderByChild("location").equalTo(userLocation);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        products.add(issue.getValue(Product.class));
                    }
                    if (products.size() > 0) {
                        displayProducts(products);
                    } else {
                        // If no products found for user location, query for Canberra products
                        queryCanberraProducts();
                    }
                } else {
                    // If no products found for user location, query for Canberra products
                    queryCanberraProducts();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void queryCanberraProducts() {
        DatabaseReference db = database.getReference("Product");
        ArrayList<Product> products = new ArrayList<>();
        Query query = db.orderByChild("location").equalTo("canberra");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        products.add(issue.getValue(Product.class));
                    }
                    if (products.size() > 0) {
                        displayProducts(products);
                    }
                }
                ProgressBar pbRecommend = findViewById(R.id.pbRecommend);
                pbRecommend.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayProducts(ArrayList<Product> products) {
        RecyclerView rvRecommend = findViewById(R.id.rvRecommend);
        rvRecommend.setLayoutManager(new LinearLayoutManager(HomePage.this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter<RecommendAdapter.ViewHolder> adapter = new RecommendAdapter(products, HomePage.this);
        rvRecommend.setAdapter(adapter);
    }


    private void initCategory() {
        DatabaseReference db = database.getReference("Category");
        ProgressBar pbCategory = findViewById(R.id.pbCategory);
        pbCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> categories = new ArrayList<>();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        categories.add(issue.getValue(Category.class));
                    }
                    if (categories.size() > 0) {
                        RecyclerView rvCategory = findViewById(R.id.rvCategory);
                        rvCategory.setLayoutManager(new GridLayoutManager(HomePage.this, 4));
                        RecyclerView.Adapter<CategoryAdapter.ViewHolder> adapter = new CategoryAdapter(categories);
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