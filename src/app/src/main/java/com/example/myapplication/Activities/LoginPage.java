package com.example.myapplication.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.basicClass.UserLoggedInState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    private Button register;
    private Button login;
    private EditText account;
    private EditText password;
    private FirebaseAuth auth;

    /**
     * This function checks the user account.
     * @return the correctness of the Account.
     */
    private boolean checkAccount() {
        return true;
    }

    /**
     * This function checks the user password.
     * @return the correctness of the Password.
     */
    private boolean checkPassword() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password_Login);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterActivity.class));
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_account = account.getText().toString();
                String txt_password = password.getText().toString();
                if (!txt_account.isEmpty() && !txt_password.isEmpty()) {
                    loginUser(txt_account, txt_password);
                } else {
                    Toast.makeText(getApplicationContext(), "Account and Password can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String txtAccount, String txtPassword) {
        auth.signInWithEmailAndPassword(txtAccount,txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResult) {
                if (authResult.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();

                        // Retrieve user information from the database and set it to GlobalVariables
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User").child(uid);
                        userReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    DataSnapshot snapshot = task.getResult();
                                    String name = snapshot.child("name").getValue(String.class);
                                    String email = snapshot.child("email").getValue(String.class);
                                    String password = snapshot.child("password").getValue(String.class);
                                    String headImage = snapshot.child("headImage").getValue(String.class);
                                    String location = snapshot.child("location").getValue(String.class);

                                    // Create an instance of the user with no-argument constructor
                                    User loginUser = new User();
                                    loginUser.setId(uid);
                                    loginUser.setName(name);
                                    loginUser.setEmail(email);
                                    loginUser.setPassword(password);
                                    loginUser.setHeadImage(headImage);
                                    loginUser.setLocation(location);

                                    // Set GlobalVariables to logged-in state
                                    GlobalVariables globalVars = GlobalVariables.getInstance();
                                    globalVars.setLoginUser(loginUser);
                                    globalVars.setState(new UserLoggedInState());

                                    // Go to HomePage
                                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to retrieve user data!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
