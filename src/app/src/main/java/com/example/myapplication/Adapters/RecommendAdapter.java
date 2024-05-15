package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.Activities.ProductPage;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.Favorite;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    ArrayList<Product> products;
    Context context;
    DatabaseReference favoriteRef;
    User currentUser;

    public RecommendAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
        this.favoriteRef = Database.getDatabase().getReference().child("Favorite");
        this.currentUser = GlobalVariables.getInstance().getLoginUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get the context of the parent
        context = parent.getContext();
        // Inflate the layout for the view holder
        View inflate  = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_recommend,parent,false);
        return new RecommendAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the product at the current position
        Product product = products.get(position);
        // Bind the product to the view holder
        holder.bind(product);

        // Add click listener to navigate to ProductPage
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductPage.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtName,txtPrice;
        ImageView imgProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }

        public void bind(Product product) {
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice());

            // Load image using Glide library with CenterCrop and RoundedCorners transformation
            Glide.with(itemView.getContext())
                    .load(product.getImgLink())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .error(R.drawable.product_page_default_img)
                    .into(imgProduct);
        }

    }
}
