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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.myapplication.Adapters.MessageAdapter;
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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;


/**
 * This activity provides a private chat interface where users
 * can send and receive messages.
 * It features a simple layout with a message input area,
 * a send button, and a list to display the chat history,
 * and a back button to return to the previous page.
 * @author Scott Ferrageau de St Amand (u7303997)
 */
public class PrivateChat extends AppCompatActivity {
    private EditText editTextMessage; // Text field for inputting messages
    private Button buttonSend; // Button to send messages
    private Button buttonBack; // Button to go back
    TextView receiverEmailText; // TextView to show who the message is being sent to.
    private RecyclerView recyclerViewMessages; // RecyclerView to display messages
    private List<String> messagesList = new ArrayList<>(); // List to hold messages
    MessageAdapter messageAdapter;
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

        loginUser = globalVars.getLoginUser();

        //receiverEmailText.setText(otherUser.getEmail());
        //create Id for the environment by sorting the users Ids alphabetically

        String[] userIds = {globalVars.getLoginUser().getId(), otherUser.getId()};
        Arrays.sort(userIds);

        environmentId = "msg" + userIds[0] + userIds[1];

        //find the name for the other User and Set it to the Toolbar
        DatabaseReference userRef = database.getReference("User");
        userRef.child(otherUser.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    User user = task.getResult().getValue(User.class);
                    if (user != null && user.getName() != null) {
                        otherUser.setName(user.getName());
                        receiverEmailText.setText(otherUser.getName());
                    }
                }
            }
        });


        openNewOrExistingEnvironment();

        DatabaseReference environmentRef = reference.child(environmentId);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim(); // Get text from EditText
                if (!message.isEmpty()) {
                    //If there isn't a message already, add the Environment Id to each user so that
                    // the recycler in ChatMenuActivity can update.
                    if (messageEnvironment.getMessageList().isEmpty()) {
                        addMessageIdToUsers();
                    }

                    //update the information regarding most recent message in firebase.
                    messageEnvironment.setRecentMessageTimestamp(CommonHelper.getCurrentTimestamp());
                    messageEnvironment.setRecentSenderId(loginUser.getId());

                    //create a new messageBuble
                    MessageBuble messageBuble = new MessageBuble(message,
                            loginUser.getId(),
                            CommonHelper.getCurrentTimestamp());

                    messageEnvironment.addMessage(messageBuble);

                    //add the message to the database, and reset the message text box.
                    reference.child(environmentId).setValue(messageEnvironment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            editTextMessage.setText("");

                        }
                    });
                }
            }
        });


        //Add a ValueEventListener() so that the user can see message when they are sent.
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateEnvironment(snapshot);
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("MessageEnvironments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Warning", "loadFailed");
            }
        };
        environmentRef.addValueEventListener(eventListener);
    }

    /**
     * Method to be called when Activity is opened.
     * Determines whether or not messages have already been exchanged between the
     * users, and either opens the existing environment or creates a new one.
     */
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

                    // Create Recycler
                    setUpRecycler(messageEnvironment);



                }
            }
        });
    }

    /**
     * Method to setup the recycler when the Activity is started
     * @param messageEnvironment
     */
    void setUpRecycler(MessageEnvironment messageEnvironment) {
        if(messageEnvironment.getMessageList() == null){
            messageAdapter = new MessageAdapter(getApplicationContext(), loginUser.getId(), new ArrayList<MessageBuble>());
        } else{
            messageAdapter = new MessageAdapter(getApplicationContext(), loginUser.getId(), messageEnvironment.getMessageList());
        }
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);
        recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);

    }

    /**
     * Method to update the the recycler when a new message is sent by either user.
     * @param snapshot
     */
    @SuppressLint("NotifyDataSetChanged")
    void updateEnvironment(DataSnapshot snapshot) {
        MessageEnvironment updatedEnvironment = snapshot.getValue(MessageEnvironment.class);
        if (messageEnvironment != null) {
            if (messageEnvironment.getMessageList() != null && messageAdapter != null) {
                assert updatedEnvironment != null;
                if (updatedEnvironment.getMessageList() != null) {
                    messageAdapter.setMessageList(updatedEnvironment.getMessageList());
                    messageAdapter.notifyDataSetChanged();
                    //scroll to the most recent message
                    if (messageAdapter.getItemCount() > 0) {
                        recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
                    }
                }
            }
        }
    }

    /**
     * Method to add the Environment Id to both users, so that the recycler in
     *  ChatMenuActivity is updated.
     */
    void addMessageIdToUsers() {
        //get database reference for users
        DatabaseReference  userRef = database.getReference("User");
        for (String userId : messageEnvironment.getUserIds()) {
            userRef.child(userId).child("messageIds").child(environmentId).setValue(environmentId);
        }
    }
}


