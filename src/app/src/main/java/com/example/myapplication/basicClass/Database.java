package com.example.myapplication.basicClass;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static FirebaseDatabase database = null;

    private Database() {
    }

    /**
     * Lazy singleton pattern used in database connection
     * Lazy singleton pattern used in database connection
     */
    public static synchronized FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        } else {
            System.out.println("Database instance has been created yet.");
        }

        return database;
    }

    /**
     * Method to upload Product JSON data to Firebase
     * Method to upload Product JSON data to Firebase
     * @param context The context of the application.
     * @param assetFileName The filename of the JSON asset.
     */
    public static void uploadJsonToFirebase(Context context, String assetFileName) {
        DatabaseReference productRef = getDatabase().getReference("Product");

        // Read JSON file using Gson and parse it into a list of Product objects
        Gson gson = new Gson();
        AssetManager assetManager = context.getAssets();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(assetFileName)))) {
            Type productListType = new TypeToken<List<Product>>() {
            }.getType();
            List<Product> productList = gson.fromJson(reader, productListType);

            // Upload the list of Product objects to Firebase
            for (Product product : productList) {
                productRef.child(product.getProductID()).setValue(product);
            }
            System.out.println("JSON data successfully uploaded to Firebase!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
