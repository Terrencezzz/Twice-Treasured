package com.example.myapplication.Activities;


import static com.example.myapplication.common.CommonHelper.*;
import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
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
import com.example.myapplication.basicClass.LocationResultListener;
import com.example.myapplication.basicClass.Notice;
import com.example.myapplication.basicClass.NoticeFactory;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.basicClass.UserLoggedOutState;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
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

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_page);

        database = Database.getDatabase();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        globalVars = GlobalVariables.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
        user_detail_btnBack = findViewById(R.id.user_detail_btnBack);
        btnSave.setOnClickListener(view -> {
            if (Validate()) {
                showAlertDialog(UserDetailPage.this,"Security Alert","Confirm modification of information?",
                        "Confirm",(dialog, which) -> {
                            updateUserInfo();
                        },"Cancel", (dialog, which) -> dialog.dismiss());
            }
        });
        btnCancel.setOnClickListener(view -> goUserPage());
        user_detail_btnBack.setOnClickListener(view -> goUserPage());
        btnRelocation.setOnClickListener(view -> {
            getLocation();

        });


        clRPwd.setVisibility(View.GONE);
        editPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                clRPwd.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


        });
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::handleImageSelection);
        user_detail_pic.setOnClickListener(view -> mGetContent.launch("image/*"));

        ReloadPage();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                showToast(UserDetailPage.this, "Required permission");
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void updateUserInfo(){
        DatabaseReference user = database.getReference().child("User").child(globalVars.getLoginUser().getId());
        user.child("name").setValue(editName.getText().toString());
        user.child("location").setValue(editLocation.getText().toString());
        if (!editPwd.getText().toString().equals("")) {
            FirebaseUser fbUser = auth.getCurrentUser();
            String newPassword = editPwd.getText().toString();
            fbUser.updatePassword(newPassword)
                    .addOnSuccessListener(aVoid -> {
                        user.child("password").setValue(newPassword);
                        //send pwd change notice
                        NoticeFactory factory = new NoticeFactory();
                        Notice userNotice = factory.createNotice("User");
                        userNotice.addNotice(globalVars.getLoginUser().getId());
                        //force to log out
                        showAlertDialog(UserDetailPage.this,"Security Alert","You have changed password, please re-login.",
                        "OK", (dialog, which) -> {
                            globalVars.setState(new UserLoggedOutState());
                            globalVars.removeLoginUser();
                            goIntroPage();
                        },null,null);
                    })
                    .addOnFailureListener(e -> showToast(UserDetailPage.this, "Password failed to update, detail:" + e.getMessage()));
        }
        //Synchronous loginUser
        refreshLoginUser(() -> {
            ReloadPage();
            showToast(UserDetailPage.this, "Successfully updated.");
        });
    }
    private Boolean Validate() {
        User loginUser = globalVars.getLoginUser();
        boolean result = true;
        String userName = editName.getText().toString();
        String location = editLocation.getText().toString();
        String pwd = editPwd.getText().toString();
        String rPwd = editRPwd.getText().toString();
        if (userName.equals("")) {
            showToast(UserDetailPage.this, "Sorry, name hasn't been filled correctly.");
            result = false;
        }
        if (location.equals("")) {
            showToast(UserDetailPage.this, "Sorry, location hasn't been picked correctly.");
            result = false;
        }
        if (!pwd.equals("")) {
            if (pwd.length() < 6) {
                showToast(UserDetailPage.this, "Sorry, pwd should have 6+ characters.");
                result = false;
            }
            if (!pwd.equals(rPwd)) {
                showToast(UserDetailPage.this, "Sorry, En-Pwd and Re-Pwd do not match .");
                result = false;
            }
        }

        return result;

    }

    private void ReloadPage() {

        User loginUser = globalVars.getLoginUser();
        Glide.with(UserDetailPage.this).load(loginUser.getHeadImage()).error(R.drawable.default_seller_img).into(ivUserPic);
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
                            showToast(UserDetailPage.this, "New profile pic is all set!");
                        });


                    })
                    .addOnFailureListener(e -> showToast(UserDetailPage.this, "Oops! Something is wrong! Details:" + e.getMessage()));
        } else {
            showToast(UserDetailPage.this, "Sorry, you haven't picked a pic yet.");
        }
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(UserDetailPage.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted

            LocationRequest locationRequest = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(2000)
                    .setMaxUpdateDelayMillis(100)
                    .build();
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                }

            };
            LocationServices.getFusedLocationProviderClient(UserDetailPage.this)
                    .requestLocationUpdates(locationRequest, locationCallback, null);

            // Create location service client
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            // Get last known location
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Location found, convert to city name
                                String cityName = getCityNameFromLocation(location);
                                // Display city name in TextView
                                editLocation.setText(cityName);
                            } else {
                                // No location found
                                showToast(UserDetailPage.this, "Location not available");
                            }
                        }
                    });


        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(UserDetailPage.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private String getCityNameFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getLocality(); // Get city name
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "None";
    }


}