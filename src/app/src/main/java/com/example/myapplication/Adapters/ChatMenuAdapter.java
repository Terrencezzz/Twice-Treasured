package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.PrivateChat;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.User;

import java.util.List;

/**
 * Adaptor to display a Users Current chat activities in a recyclerview.
 * Used in the ChatMenu Activity.
 * @author Scott Ferrageau de St Amand (u7303997)
 */

public class ChatMenuAdapter extends RecyclerView.Adapter<ChatMenuAdapter.UserViewHolder> {

    Context context;

    List<User> usersList;

    public ChatMenuAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ChatMenuAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for each item in the RecyclerView.
        View view = LayoutInflater.from(context).inflate(R.layout.chat_menu_recycler, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMenuAdapter.UserViewHolder holder, int position) {
        if (usersList.isEmpty()) {
            holder.userName.setVisibility(View.GONE);
            holder.userEmail.setVisibility(View.GONE);
        }

        User user = usersList.get(position);
        holder.userName.setText(user.getName());
        holder.userEmail.setText(user.getEmail());

        //Allow user to open chat
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PrivateChat.class);
            intent.putExtra("userId", user.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userEmail;
        //ImageView userImage;
        RelativeLayout relativeLayout;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_recycler_name);
            userEmail = itemView.findViewById(R.id.user_recycler_email);
            relativeLayout = itemView.findViewById(R.id.chat_menu_recycler_relative_Layout);
        }
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
