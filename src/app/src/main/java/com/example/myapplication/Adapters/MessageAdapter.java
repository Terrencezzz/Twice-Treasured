package com.example.myapplication.Adapters;

import android.content.Context;
import android.media.audiofx.LoudnessEnhancer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.MessageBuble;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for each item in the RecyclerView choose corresponding layout if
        // sending or receiving the message.
        if (viewType == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.messsage_recycler_receive, parent, false);
            return new MessageViewHolderReciever(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.message_recycler_send, parent, false);
            return new MessageViewHolderSender(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MessageViewHolderSender) {
            MessageViewHolderSender senderHolder = (MessageViewHolderSender) holder;
            if (messageList.isEmpty()) {
                senderHolder.messageText.setVisibility(View.GONE);
                senderHolder.messageTimestamp.setVisibility(View.GONE);
            } else {
                MessageBuble messageBuble = messageList.get(position);
                senderHolder.messageText.setText(messageBuble.getMessage());
                senderHolder.messageTimestamp.setText(messageBuble.getTimestamp());
            }
        } else if (holder instanceof MessageViewHolderReciever) {
            MessageViewHolderReciever recieverHolder = (MessageViewHolderReciever) holder;
            if (messageList.isEmpty()) {
                recieverHolder.messageText.setVisibility(View.GONE);
                recieverHolder.messageTimestamp.setVisibility(View.GONE);
            } else {
                MessageBuble messageBuble = messageList.get(position);
                recieverHolder.messageText.setText(messageBuble.getMessage());
                recieverHolder.messageTimestamp.setText(messageBuble.getTimestamp());
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
    @Override
    public int getItemViewType(int postion) {
        MessageBuble messageBuble = messageList.get(postion);
        if (!messageBuble.getSenderId().equals(userId)) {
            return 2;
        } else return 1;
    }

    public static class MessageViewHolderSender extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView messageTimestamp;
        RelativeLayout relativeLayout;
        public MessageViewHolderSender(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.bubble_send);
            messageTimestamp = itemView.findViewById(R.id.send_timestamp);
            relativeLayout = itemView.findViewById(R.id.message_recycler_send_relative_Layout);
        }
    }
    public static class MessageViewHolderReciever extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView messageTimestamp;
        RelativeLayout relativeLayout;
        public MessageViewHolderReciever(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.bubble_receive);
            messageTimestamp = itemView.findViewById(R.id.receive_timestamp);
            relativeLayout = itemView.findViewById(R.id.message_recycler_receive_relative_layout);
        }
    }

    public void setMessageList(List<MessageBuble> messageList) {
        this.messageList = messageList;
    }
}
