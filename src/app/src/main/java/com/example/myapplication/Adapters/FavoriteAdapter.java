package com.example.myapplication.Adapters;

// Android imports

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

// RecyclerView imports
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

// Glide library import for image loading
import com.bumptech.glide.Glide;

// Custom class imports
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

// Java utility imports
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter class for managing favorite products
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Product> favoriteItemList;
    private Set<Integer> selectedItems = new HashSet<>();
    private ProductClickListener productClickListener;
    private boolean isManageMode = false;

    public interface ProductClickListener {
        void onProductClick(Product product);
    }

    // Constructor
    public FavoriteAdapter(List<Product> favoriteItemList, ProductClickListener productClickListener) {
        this.favoriteItemList = favoriteItemList;
        this.productClickListener= productClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for a single item view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = favoriteItemList.get(position);
        // Bind product data to UI elements
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("$ " + product.getPrice());
        Glide.with(holder.itemView.getContext()).load(product.getImgLink()).into(holder.productImage);

        // Update view constraints based on management mode
        updateConstraints(holder);
        // Update checkbox state and listener
        updateCheckBox(holder, position);
    }

    // Update view constraints based on management mode
    private void updateConstraints(ViewHolder holder) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) holder.itemView);
        if (isManageMode) {
            constraintSet.connect(R.id.product_image, ConstraintSet.START, R.id.checkBox, ConstraintSet.END, 16);
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            constraintSet.connect(R.id.product_image, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 4);
            holder.checkBox.setVisibility(View.GONE);
        }
        constraintSet.applyTo((ConstraintLayout) holder.itemView);
    }

    // Update checkbox state and listener
    private void updateCheckBox(ViewHolder holder, int position) {
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedItems.contains(position));
        holder.checkBox.setVisibility(isManageMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(position);
            } else {
                selectedItems.remove(position);
            }
            notifyItemChanged(position);
        });
    }


    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productDescription, productPrice;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    // Toggle management mode
    public void toggleManageMode() {
        isManageMode = !isManageMode;
        notifyDataSetChanged();
    }

    // Get selected items
    public Set<Integer> getSelectedItems() {
        return selectedItems;
    }

    // Delete selected items
    public void deleteSelectedItems() {
        List<Product> remainingItems = new ArrayList<>();
        for (int i = 0; i < favoriteItemList.size(); i++) {
            if (!selectedItems.contains(i)) {
                remainingItems.add(favoriteItemList.get(i));
            }
        }
        favoriteItemList = remainingItems;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    // Get total item count
    @Override
    public int getItemCount() {
        return favoriteItemList.size();
    }

    public boolean isManageMode() {
        return isManageMode;
    }

    // Check if in management mode
    public void selectAll() {
        selectedItems.clear();
        for (int i = 0; i < favoriteItemList.size(); i++) {
            selectedItems.add(i);
        }
        notifyDataSetChanged();
    }

    // Select all items
    public void deselectAll() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    // 更新收藏产品列表
    public void setFavoriteItemList(List<Product> newFavoriteItemList) {
        this.favoriteItemList = newFavoriteItemList != null ? new ArrayList<>(newFavoriteItemList) : new ArrayList<>();
        notifyDataSetChanged();
    }

}
