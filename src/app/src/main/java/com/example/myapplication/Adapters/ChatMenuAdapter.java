package com.example.myapplication.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.MessageBuble;
import com.example.myapplication.basicClass.User;

import java.util.List;

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
            //holder.userImage.setVisibility(View.GONE);
        }

        User user = usersList.get(position);
        holder.userName.setText(user.getName());
        holder.userEmail.setText(user.getEmail());
        //holder.userImage.setImageURI((Uri.parse(user.getHeadImage())));

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userEmail;
        //ImageView userImage;
        RelativeLayout relativeLayout;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_recycler_name);
            userEmail = itemView.findViewById(R.id.user_recycler_email);
            //userImage = itemView.findViewById(R.id.menu_person_holder);
            relativeLayout = itemView.findViewById(R.id.chat_menu_recycler_relative_Layout);
        }
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
