package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This page is for registration
 * @author Wanzhong Wu
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button registerButton;
    private Button loginButton;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);
        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "To short!", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email, txt_password);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginPage.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser(String txtEmail, String txtPassword) {
        auth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                    String id = task.getResult().getUser().getUid();
                    String name = email.getText().toString();
                    String location = "None";
                    Uri defaultFile = Uri.fromFile(new File("gs://second-hand-market-affd5.appspot.com/uploads/1714311493275.jpg"));

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("id", id);
                    userMap.put("name" , name);
                    userMap.put("email", txtEmail);
                    userMap.put("password", txtPassword);
                    userMap.put("location", location);
                    userMap.put("headImage", defaultFile.toString());

                    DatabaseReference userReference = reference.child("User").child(id);
                    userReference.setValue(userMap);

                    Intent intent = new Intent(RegisterActivity.this, HomePage.class);
                    intent.putExtra("email",txtEmail);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}