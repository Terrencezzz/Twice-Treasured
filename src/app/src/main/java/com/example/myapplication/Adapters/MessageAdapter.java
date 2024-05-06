package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.MessageBuble;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    Context context;
    String userId; //Id of the user who sent the message

    List<MessageBuble> messageList;

    public MessageAdapter(Context context, String userId, List<MessageBuble> messageList) {
        this.context = context;
        this.userId = userId;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for each item in the RecyclerView.
        View view = LayoutInflater.from(context).inflate(R.layout.message_recycler_send, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        if (messageList.isEmpty()) {
            holder.messageText.setVisibility(View.GONE);
            holder.messageTimestamp.setVisibility(View.GONE);
        }

        MessageBuble messageBuble = messageList.get(position);
        holder.messageText.setText(messageBuble.getMessage());
        holder.messageTimestamp.setText(messageBuble.getTimestamp());
        if (!messageBuble.getSenderId().equals(userId)) {
            //change so that the message bubble appears on the left
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.relativeLayout.setLayoutParams(params);

            //change colour of Bubble
            int receiveColor = ContextCompat.getColor(context, R.color.dark_pink);
            holder.messageText.setBackgroundColor(receiveColor);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView messageTimestamp;
        RelativeLayout relativeLayout;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.bubble_send);
            messageTimestamp = itemView.findViewById(R.id.send_timestamp);
            relativeLayout = itemView.findViewById(R.id.message_recycler_send_relative_Layout);
        }
    }
}
