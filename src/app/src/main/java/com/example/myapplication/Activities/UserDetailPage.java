package com.example.myapplication.Activities;

import static com.example.myapplication.common.CommonHelper.refreshLoginUser;
import static com.example.myapplication.common.CommonHelper.showToast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Timer;
import java.util.TimerTask;

public class UserDetailPage extends Page {

    FirebaseStorage storage;
    FirebaseDatabase database;
    FirebaseAuth auth;
    StorageReference storageReference;
    GlobalVariables globalVars;

    private ImageView ivUserPic;
    private EditText editName;
    private EditText editUID;
    private EditText editEmail;
    private EditText editLocation;
    private EditText editPwd;
    private EditText editRPwd;
    private ConstraintLayout clRPwd;
    private ImageView btnRelocation;
    private Button btnSave;
    private Button btnCancel;
    private ProgressBar pbUserDetail;
    private CardView user_detail_pic;
    private ImageView user_detail_btnBack;

    private ActivityResultLauncher<String> mGetContent;
    private String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_page);

        database = Database.getDatabase();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        globalVars = GlobalVariables.getInstance();

        ivUserPic = findViewById(R.id.ivUserPic);
        editName = findViewById(R.id.editName);
        editUID = findViewById(R.id.editUID);
        editEmail = findViewById(R.id.editEmail);
        editLocation = findViewById(R.id.editLocation);
        editPwd = findViewById(R.id.editPwd);
        editRPwd = findViewById(R.id.editRPwd);
        clRPwd = findViewById(R.id.clRPwd);
        btnRelocation = findViewById(R.id.btnRelocation);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        pbUserDetail = findViewById(R.id.pbUserDetail);
        user_detail_pic = findViewById(R.id.user_detail_pic);
        user_detail_btnBack =findViewById(R.id.user_detail_btnBack);


        btnSave.setOnClickListener(view -> {
            if(Validate()){

                DatabaseReference user = database.getReference().child("User").child(globalVars.getLoginUser().getId());
                user.child("name").setValue(editName.getText().toString());
                user.child("location").setValue(editLocation.getText().toString());
                if(!editPwd.getText().toString().equals("")) {
                    FirebaseUser fbUser = auth.getCurrentUser();
                    String newPassword = editPwd.getText().toString();
                    fbUser.updatePassword(newPassword)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    user.child("password").setValue(newPassword);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showToast(UserDetailPage.this,"Password failed to update, detail:"+e.getMessage());
                                }
                            });


                }

                //Synchronous loginUser
                refreshLoginUser(()->{
                        ReloadPage();
                        showToast(UserDetailPage.this,"Successfully updated.");
                });




            }
        });
        btnCancel.setOnClickListener(view -> goUserPage());
        user_detail_btnBack.setOnClickListener(view -> goUserPage());
        btnRelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        clRPwd.setVisibility(View.GONE);
        editPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {clRPwd.setVisibility(View.VISIBLE);}
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}


        });
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::handleImageSelection);
        user_detail_pic.setOnClickListener(view -> mGetContent.launch("image/*"));

        ReloadPage();
    }

    private Boolean Validate(){
        User loginUser = globalVars.getLoginUser();
        boolean result = true;
        String userName = editName.getText().toString();
        String location = editLocation.getText().toString();
        String pwd = editPwd.getText().toString();
        String rPwd = editRPwd.getText().toString();
        if(userName.equals("")){
            showToast(UserDetailPage.this,"Sorry, name hasn't been filled correctly.");
            result = false;
        }
        if(location.equals("")){
            showToast(UserDetailPage.this,"Sorry, location hasn't been picked correctly.");
            result = false;
        }
        if(!pwd.equals("")){
            if(pwd.length()<6){
                showToast(UserDetailPage.this,"Sorry, pwd should have 6+ characters.");
                result = false;
            }
            if(!pwd.equals(rPwd)){
                showToast(UserDetailPage.this,"Sorry, En-Pwd and Re-Pwd do not match .");
                result = false;
            }
        }

        return result;

    }
    private void ReloadPage(){

        User loginUser = globalVars.getLoginUser();
        Glide.with(UserDetailPage.this).load(loginUser.getHeadImage()).into(ivUserPic);
        editName.setText(loginUser.getName());
        editUID.setText(loginUser.getId());
        editEmail.setText(loginUser.getEmail());
        editLocation.setText(loginUser.getLocation());
        editPwd.setText("");
        editRPwd.setText("");
        clRPwd.setVisibility(View.GONE);
        View rootView = findViewById(android.R.id.content);
        rootView.clearFocus();
    }

    private void handleImageSelection(Uri uri) {

        if (uri != null) {
            StorageReference storageReference = storage.getReference();
            StorageReference fileRef = storageReference.child("headImages/" + System.currentTimeMillis() + ".png");
            pbUserDetail.setVisibility(View.VISIBLE);
            fileRef.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get downloadUrl in Storage
                        fileRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                            imageUri = uri1.toString();
                            Glide.with(UserDetailPage.this).load(imageUri).into(ivUserPic);//user data asynchronous update, thus use imageUri directly
                            database.getReference().child("User").child(globalVars.getLoginUser().getId()).child("headImage").setValue(imageUri);
                            pbUserDetail.setVisibility(View.GONE);
                            showToast(UserDetailPage.this,"New profile pic is all set!");
                        });


                    })
                    .addOnFailureListener(e -> showToast(UserDetailPage.this,"Oops! Something is wrong! Details:"+e.getMessage()));
        } else {
            showToast(UserDetailPage.this,"Sorry, you haven't picked a pic yet.");
        }
    }
}