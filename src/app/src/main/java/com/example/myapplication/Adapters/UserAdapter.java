package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.PrivateChat;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<String> localDataSet;

    public UserAdapter(List<String> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Inflate the custom layout for each item
        TextView v = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, final int position) {
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

