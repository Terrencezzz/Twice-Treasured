package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.NotificationPage;
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
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notification,parent,false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewholder holder, int position) {
        Notification notification = notifications.get(position);
        holder.noti_view_title.setText(notification.getNotiTitle());
        //extract the content
        String content = notification.getNotiContents();
        String[] words = content.split("\\s+");
        StringBuilder shortenedContent = new StringBuilder();
        int wordCount = Math.min(3, words.length);
        for (int i = 0; i < wordCount; i++) {
            shortenedContent.append(words[i]).append(" ");
        }
        if (words.length > 3) {
            shortenedContent.append("...");
        }
        holder.noti_view_content.setText(shortenedContent.toString().trim());
        //set read or unread status
        int readStatus = notification.getNotiStatus();
        if(readStatus>0){
            holder.noti_view_unread.setVisibility(View.GONE);
        }
        if(notification.getNotiType().equals("1")){
            holder.noti_view_icon.setImageResource(R.drawable.icon_notice_safe);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(notification.getNotiTitle());
                builder.setMessage(notification.getNotiContents());
                if(readStatus==0) {
                    builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new NotificationPage().readNotice(notification.getNotiID());
                            //update singleItem in recycleView
                            notification.setNotiStatus(1);
                            notifyItemChanged(holder.getAdapterPosition());
                        }
                    });
                }
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{
        TextView noti_view_title,noti_view_content;
        ImageView noti_view_unread,noti_view_icon;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            noti_view_title = itemView.findViewById(R.id.noti_view_title);
            noti_view_content = itemView.findViewById(R.id.noti_view_content);
            noti_view_unread = itemView.findViewById(R.id.noti_view_unread);
            noti_view_icon = itemView.findViewById(R.id.noti_view_icon);
        }
    }
}
