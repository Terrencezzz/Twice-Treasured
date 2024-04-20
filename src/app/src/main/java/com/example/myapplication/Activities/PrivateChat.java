package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.R;

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
    private RecyclerView recyclerViewMessages; // RecyclerView to display messages
    private List<String> messagesList = new ArrayList<>(); // List to hold messages
    private SimpleTextAdapter adapter; // Adapter for RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        // Initialize components from the layout
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonBack = findViewById(R.id.buttonBack); // Initialize the back button
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        // Setup the adapter and layout manager for RecyclerView
        adapter = new SimpleTextAdapter(messagesList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);

        // Set onClickListener for the send button
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim(); // Get text from EditText
                if (!message.isEmpty()) {
                    messagesList.add(message); // Add message to the list
                    adapter.notifyDataSetChanged(); // Notify adapter that data has changed
                    editTextMessage.setText(""); // Clear the input field
                    recyclerViewMessages.scrollToPosition(messagesList.size() - 1); // Scroll to the last message
                }
            }
        });

        // Set onClickListener for the back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the activity and return to the previous screen
            }
        });
    }

    // Adapter class for the RecyclerView
    private class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
        private List<String> localDataSet;

        public SimpleTextAdapter(List<String> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Inflate the custom layout for each item
            TextView v = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // Set the text of the TextView to the message at the current position
            viewHolder.getTextView().setText(localDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        // ViewHolder class to hold the view components for each item
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;

            public ViewHolder(TextView v) {
                super(v);
                textView = v; // Initialize the TextView
            }

            public TextView getTextView() {
                return textView; // Getter method for TextView
            }
        }
    }
}
