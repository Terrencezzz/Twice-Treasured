package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

import java.util.List;

/**
 * Adapter for the RecyclerView that displays Product details.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;  // List of products to display in RecyclerView

    /**
     * Provides a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productId;
        private final TextView price;
        private final TextView category;

        /**
         * Constructor for ViewHolder, references TextViews from the layout.
         *
         * @param view The container view which holds the TextViews.
         */
        public ViewHolder(View view) {
            super(view);
            productId = view.findViewById(R.id.product_id);
            price = view.findViewById(R.id.price);
            category = view.findViewById(R.id.category);
        }

        /**
         * Binds data to the TextViews within each item of the RecyclerView.
         *
         * @param product Product object containing the data to be displayed.
         */
        public void bind(Product product) {
            productId.setText(product.getProductID());
            price.setText(product.getPrice());
            category.setText(product.getCategory());
        }
    }

    /**
     * Constructor for the ProductAdapter.
     *
     * @param dataSet List of Product objects to be displayed.
     */
    public ProductAdapter(List<Product> dataSet) {
        productList = dataSet;
    }

    /**
     * Create new views (invoked by the layout manager).
     *
     * @param viewGroup The parent view that will hold the new view.
     * @param viewType The type of view to create.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * Replace the contents of a view (invoked by the layout manager).
     *
     * @param viewHolder The view holder whose contents need to be updated.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(productList.get(position));
    }

    /**
     * Return the size of your dataset (invoked by the layout manager).
     *
     * @return The number of items in the productList.
     */
    @Override
    public int getItemCount() {
        return productList.size();
    }
}
