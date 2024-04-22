package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> categories;
    Context context;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate  = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        holder.txtCategory.setText(categories.get(position).getCategoryName());

        switch (position){
            case 0:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_electronics);
                break;
            }
            case 1:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_clothes);
                break;
            }
            case 2:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_furniture);
                break;
            }
            case 3:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_books);
                break;
            }
            case 4:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_sports);
                break;
            }
            case 5:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_toys);
                break;
            }
            case 6:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_beauty);
                break;
            }
            case 7:{
                holder.imgCategory.setBackgroundResource(R.drawable.category_others);
                break;
            }


        }

        int drawableResourceId = context.getResources().getIdentifier(categories.get(position).getImagePath()
        ,"drawable",holder.itemView.getContext().getOpPackageName());
        Glide.with(context)
                .load(categories.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.imgCategory);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategory;
        ImageView imgCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            imgCategory = itemView.findViewById(R.id.imgCategory);
        }
    }
}
