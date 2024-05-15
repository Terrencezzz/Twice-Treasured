package com.example.myapplication.common;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Activities.UserDetailPage;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.LocationResultListener;
import com.example.myapplication.basicClass.OnDataUpdatedListener;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.basicClass.UserLoggedInState;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CommonHelper {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    public static void showToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Dialog
     * */
    public static void showAlertDialog(Context context, String title, String message,
                                       String positiveButtonLabel, DialogInterface.OnClickListener positiveButtonClickListener,
                                       String negativeButtonLabel, DialogInterface.OnClickListener negativeButtonClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(positiveButtonLabel, positiveButtonClickListener);

        if (negativeButtonLabel != null && negativeButtonClickListener != null) {
            alertDialogBuilder.setNegativeButton(negativeButtonLabel, negativeButtonClickListener);
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void refreshLoginUser(OnDataUpdatedListener listener) {

        FirebaseDatabase database = Database.getDatabase();
        GlobalVariables globalVars = GlobalVariables.getInstance();
        String userEmail = globalVars.getLoginUser().getEmail();
        DatabaseReference db = database.getReference("User");
        Query query = db.orderByChild("email").equalTo(userEmail);
        ArrayList<User> users = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    users.clear();
                    for (DataSnapshot issue:snapshot.getChildren()){
                        users.add(issue.getValue(User.class));
                    }
                    if(users.size()>0){
                        //GlobalVariables.getInstance().setLoginUser(users.get(0));
                        globalVars.setState(new UserLoggedInState());
                        globalVars.addLoginUser(users.get(0));
                        listener.onDataUpdated();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     *  Encapsulated location retrieval function
     *  Call it e.g. getLastLocation(UserDetailPage.this, UserDetailPage.this, address -> editLocation.setText(address.getLocality()));
     *  Tip:
     *  1.You need to allow My Application access your location in "While using the app"
     *  2.You can change your location in Extended Controls(three dots)
     *  3.Do not forget to override onRequestPermissionsResult, mimic it in UserDetailPage.java
     *  4.address, Type of Address, you can get different types of results from address. e.g.getLocality(), getCountryName() etc.
     * */
    public static void getLastLocation(Context context, Activity activity, LocationResultListener listener) {
        FusedLocationProviderClient fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(context);
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            LocationRequest locationRequest = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(2000)
                    .setMaxUpdateDelayMillis(100)
                    .build();
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {}

            };
            LocationServices.getFusedLocationProviderClient(context)
                    .requestLocationUpdates(locationRequest, locationCallback, null);

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if(location != null){
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                Address address = addresses.get(0);
                                if (listener != null) {
                                    listener.onLocationResult(address);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
        }else{
            //askPermission
            ActivityCompat.requestPermissions(activity,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
        return sdf.format(new Date());
    }
}
