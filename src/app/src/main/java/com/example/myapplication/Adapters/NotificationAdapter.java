package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {
    ArrayList<Notification> notifications;
    Context context;

    public NotificationAdapter(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notification,parent,false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewholder holder, int position) {
        holder.noti_view_title.setText(notifications.get(position).getNotiTitle());
        holder.noti_view_content.setText((notifications.get(position).getNotiContents()));
        int readStatus = notifications.get(position).getNotiStatus();
        if(readStatus>0){
            holder.noti_view_unread.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView noti_view_title,noti_view_content;
        ImageView noti_view_unread;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            noti_view_title = itemView.findViewById(R.id.noti_view_title);
            noti_view_content = itemView.findViewById(R.id.noti_view_content);
            noti_view_unread = itemView.findViewById(R.id.noti_view_unread);

        }
    }
}
