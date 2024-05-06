package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.example.myapplication.Adapters.UserAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.MessageBuble;
import com.example.myapplication.basicClass.MessageEnvironment;
import com.example.myapplication.basicClass.User;
import com.example.myapplication.common.CommonHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * This activity provides a private chat interface where users
 * can send and receive messages.
 * It features a simple layout with a message input area,
 * a send button, and a list to display the chat history,
 * and a back button to return to the previous page.
 */
public class PrivateChat extends AppCompatActivity {
    private EditText editTextMessage; // Text field for inputting messages
    private Button buttonSend; // Button to send messages
    private Button buttonBack; // Button to go back
    TextView receiverEmailText; // TextView to show who the message is being sent to.
    private RecyclerView recyclerViewMessages; // RecyclerView to display messages
    private List<String> messagesList = new ArrayList<>(); // List to hold messages
    private UserAdapter adapter; // Adapter for RecyclerView
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String environmentId;
    ArrayList<User> userArrayList;
    User loginUser;
    User otherUser = new User();

    GlobalVariables globalVars = GlobalVariables.getInstance();
    MessageEnvironment messageEnvironment;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        // Initialize components from the layout
        editTextMessage = findViewById(R.id.message_text);
        buttonSend = findViewById(R.id.chat_send_btn);
        buttonBack = findViewById(R.id.chat_back_btn); // Initialize the back button
        recyclerViewMessages = findViewById(R.id.chat_recycler_view);
        receiverEmailText = findViewById(R.id.receiver_email_text);
        auth = FirebaseAuth.getInstance();


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MessageEnvironments");

        // Set onClickListener for the back button
        buttonBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        //Get details of other user from intent
        otherUser.setId(getIntent().getStringExtra("userId"));
        otherUser.setEmail(getIntent().getStringExtra("userEmail"));

        loginUser = globalVars.getLoginUser();

        receiverEmailText.setText(otherUser.getEmail());

        //create Id for the environment by sorting the users Ids alphabetically

        String[] userIds = {globalVars.getLoginUser().getId(), otherUser.getId()};
        Arrays.sort(userIds);

        environmentId = "msg" + userIds[0] + userIds[1];

        openNewOrExistingEnvironment();


        DatabaseReference environmentRef = reference.child(environmentId);
        DatabaseReference messagesRef = environmentRef.child("messages");

        /**
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        // Setup the adapter and layout manager for RecyclerView
        adapter = new UserAdapter(messagesList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);

        */
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim(); // Get text from EditText
                if (!message.isEmpty()) {

                    //update the information regarding most recent message in firebase.
                    messageEnvironment.setRecentMessageTimestamp(CommonHelper.getCurrentTimestamp());
                    messageEnvironment.setRecentSenderId(loginUser.getId());

                    MessageBuble messageBuble = new MessageBuble(message,
                            loginUser.getId(),
                            CommonHelper.getCurrentTimestamp());


                    messageEnvironment.addMessage(messageBuble);

                    //Log.e("checkMessage", messageEnvironment.getMessageList().get(0).toString());
                    reference.child(environmentId).setValue(messageEnvironment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            editTextMessage.setText("");
                        }
                    });

                    //get a unique key for the message
                    /**String messageId = messagesRef.push().getKey();

                    messagesRef.child(messageId).setValue(messageBuble)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                editTextMessage.setText("");
                                            }
                                        }
                                    });
                    */





                    //messagesList.add(message); // Add message to the list
                    //adapter.notifyDataSetChanged(); // Notify adapter that data has changed
                    //editTextMessage.setText(""); // Clear the input field
                    //recyclerViewMessages.scrollToPosition(messagesList.size() - 1); // Scroll to the last message
                }
            }
        });
    }

    void openNewOrExistingEnvironment() {
        reference.child(environmentId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    messageEnvironment = task.getResult().getValue(MessageEnvironment.class);
                    if (messageEnvironment == null) {
                        messageEnvironment = new MessageEnvironment(environmentId,
                                Arrays.asList(loginUser.getId(), otherUser.getId()),
                                CommonHelper.getCurrentTimestamp(),
                                "","", new ArrayList<MessageBuble>());
                        reference.child(environmentId).setValue(messageEnvironment);
                    }
                }
            }
        });
    }








   /** void openNewOrExistingEnvironment() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if there isn't a path for MessageEnvironments in the database, create one
                if (!snapshot.exists()) {
                    DatabaseReference newEnvironmentRef = reference.push();
                    newEnvironmentRef.setValue(true).addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            environmentId = newEnvironmentRef.getKey();
                            setupEnvironment();
                        } else {
                            Toast.makeText(PrivateChat.this, "Failed to create node",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else setupEnvironment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Database error: " + error.getMessage());
            }
        });
    }

    */

    /**
    void openNewOrExistingEnvironment() {
        database.getReference("MessageEnvironments").child(environmentId).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        messageEnvironment = task.getResult().getValue(MessageEnvironment.class);
                        if (messageEnvironment == null) {
                            List<String> userIds = new ArrayList<>();
                            userIds.add(globalVars.getLoginUser().getId());
                            userIds.add(otherUser.getId());
                            messageEnvironment = new MessageEnvironment(
                                    environmentId,
                                    userIds,
                                    CommonHelper.getCurrentTimestamp(),
                                    ""
                            );
                        }


                    }
                    Toast.makeText(PrivateChat.this, "Fail!", Toast.LENGTH_SHORT).show();
                });
    }
     */



}


