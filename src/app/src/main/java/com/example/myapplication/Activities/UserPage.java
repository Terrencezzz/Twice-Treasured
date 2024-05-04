package com.example.myapplication.Activities;


import static com.example.myapplication.common.CommonHelper.*;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * In this page user can check his information.
 */
public class UserPage extends Page {


    FirebaseStorage storage;
    FirebaseDatabase database;
    StorageReference storageReference;
    GlobalVariables globalVars;
    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;

    private ImageView ivUserPic;
    private TextView username;
    private TextView useremail;

    private CardView cvUserPic;
    private ConstraintLayout clUserProfile;
    private ConstraintLayout clUserFavorite;
    private ConstraintLayout clUserMessage;
    private ConstraintLayout clUserProduct;
    private ProgressBar pbUserPage;

    private ActivityResultLauncher<String> mGetContent;
    private String imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        database = Database.getDatabase();
        storage = FirebaseStorage.getInstance();
        globalVars = GlobalVariables.getInstance();

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);
        ivUserPic = findViewById(R.id.ivUserPic);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        cvUserPic = findViewById(R.id.cvUserPic);
        clUserProfile = findViewById(R.id.clUserProfile);
        clUserFavorite = findViewById(R.id.clUserFavorite);
        clUserMessage = findViewById(R.id.clUserMessage);
        clUserProduct = findViewById(R.id.clUserProduct);
        pbUserPage = findViewById(R.id.pbUserPage);

        username.setText(globalVars.getLoginUser().getName());
        useremail.setText(globalVars.getLoginUser().getEmail());
        Glide.with(UserPage.this).load(globalVars.getLoginUser().getHeadImage()).into(ivUserPic);


        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::handleImageSelection);
        cvUserPic.setOnClickListener(view -> mGetContent.launch("image/*"));

        clUserProfile.setOnClickListener(view -> goUserDetailPage());
        clUserFavorite.setOnClickListener(view -> goFavorite());
        clUserMessage.setOnClickListener(view -> {});
        clUserProduct.setOnClickListener(view -> {});

        clHome.setOnClickListener(v -> goHomePage());
        btnTradePlatform.setOnClickListener(v -> goTradePage());
        clFavorite.setOnClickListener(v -> goFavorite());
        clPrivate.setOnClickListener(v -> goPrivateMenu());

    }

    private void handleImageSelection(Uri uri) {

        if (uri != null) {
            StorageReference storageReference = storage.getReference();
            StorageReference fileRef = storageReference.child("headImages/" + System.currentTimeMillis() + ".png");
            pbUserPage.setVisibility(View.VISIBLE);
            fileRef.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get downloadUrl in Storage
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUri = uri.toString();
                                Glide.with(UserPage.this).load(imageUri).into(ivUserPic);//user data asynchronous update, thus use imageUri directly
                                database.getReference().child("User").child(globalVars.getLoginUser().getId()).child("headImage").setValue(imageUri);
                                pbUserPage.setVisibility(View.GONE);
                                showToast(UserPage.this,"New profile pic is all set!");
                            }
                        });


                    })
                    .addOnFailureListener(e -> showToast(UserPage.this,"Oops! Something is wrong! Details:"+e.getMessage()));
        } else {
            showToast(UserPage.this,"Sorry, you haven't picked a pic yet.");
        }
    }
}