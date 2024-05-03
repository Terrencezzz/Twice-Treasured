package com.example.myapplication.Activities;

// Android imports
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// RecyclerView imports
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Custom adapters and classes
import com.example.myapplication.Adapters.FavoriteAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

// Java utility imports
import java.util.ArrayList;
import java.util.List;


/**
 * In this page people can check their favorite products.
 */
public class Favorite extends Page {
    // UI elements
    private TextView tvTitle;
    private RecyclerView rvFavorite;
    private ImageButton btn_return_to_home;
    private FavoriteAdapter adapter;
    private CheckBox selectAllCheckbox;
    private Button btnManage;
    private Button btnDelete;
    private LinearLayout bottomBar;

    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Initializing UI elements
        tvTitle = findViewById(R.id.tvTitle);
        btn_return_to_home = findViewById(R.id.btn_return_to_home);
        btn_return_to_home.setOnClickListener(v -> goHomePage());

        rvFavorite = findViewById(R.id.rvFavorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));

        selectAllCheckbox = findViewById(R.id.checkbox_select_all);
        btnManage = findViewById(R.id.btnManage);
        btnDelete = findViewById(R.id.btnDelete);
        bottomBar = findViewById(R.id.bottom_bar);

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite= findViewById(R.id.clFavorite);

        // Initialize favorite products list and set up UI
        initFavoriteProducts();
        setupManageMode();
        setupSelectAllFeature();
        updateSelectAllVisibility(false);
    }


    // Initialize favorite products list
    private void initFavoriteProducts() {
        List<Product> favoriteProducts = loadFavoriteProducts();
        this.adapter = new FavoriteAdapter(favoriteProducts, this::handleProductClick);
        rvFavorite.setAdapter(this.adapter);
    }

    // Handle click on a product
    public void handleProductClick() {
        goHomePage();
    }

    // Set up management mode (for deleting items)
    private void setupManageMode() {
        btnManage.setOnClickListener(v -> {
            try {
                if (adapter.isManageMode()) {
                    adapter.toggleManageMode();
                    btnManage.setText("Manage");
                    btnDelete.setVisibility(View.GONE);
                    updateSelectAllVisibility(false);
                } else {
                    adapter.toggleManageMode();
                    btnManage.setText("Finish");
                    btnDelete.setVisibility(View.VISIBLE);
                    updateSelectAllVisibility(true);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log or handle the error appropriately
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (this.adapter != null) {
                this.adapter.deleteSelectedItems();
                if (adapter.getItemCount() == 0) {
                    btnDelete.setVisibility(View.GONE);
                    updateSelectAllVisibility(false);
                }
            }
        });

        clMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserPage();
            }
        });

        btnTradePlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTradePage();
            }
        });

        clPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goPrivateMenu();}
        });

        clHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomePage();
            }
        });

    }

    // Set up 'Select All' feature
    private void setupSelectAllFeature() {
        selectAllCheckbox.setOnClickListener(v -> {
            boolean isChecked = selectAllCheckbox.isChecked();
            if (isChecked) {
                adapter.selectAll();
            } else {
                adapter.deselectAll();
            }
        });
    }

    // Update visibility of 'Select All' checkbox and bottom bar
    private void updateSelectAllVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        bottomBar.setVisibility(visibility);
        selectAllCheckbox.setVisibility(visibility);
        btnDelete.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private List<Product> loadFavoriteProducts() {
        // Dummy data for demonstration
        List<Product> products = new ArrayList<>();
        products.add(new Product("test","1", "Category", "Nice product Nice productNice productNice productNice productNice productNice productNice productNice product", "100", "New", "Today", "Available", "https://img01.yzcdn.cn/upload_files/2019/12/27/Fk1Z1GGZ-42PJVGrSSHFMrgSO4R8.jpg%21middle.jpg","tom","001","None"));
        products.add(new Product("test","2", "Category", "Another product Another productAnother productAnother productAnother productAnother productAnother productAnother productAnother product", "200", "Used", "Yesterday", "Available", "https://img01.yzcdn.cn/upload_files/2017/11/01/da3c0908669a5d3c43dec36642415254.jpg%21middle.jpg","tom","001","None"));
        products.add(new Product("test","3", "Category", "Awesome product Awesome productAwesome productAwesome productAwesome productAwesome productAwesome productAwesome productAwesome product", "150", "New", "Today", "Available", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fg-search1.alicdn.com%2Fimg%2Fbao%2Fuploaded%2Fi4%2FO1CN01UL3Mg01PROLUi7MLa_%21%210-fleamarket.jpg_300x300.jpg&refer=http%3A%2F%2Fg-search1.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1717053644&t=1c1527ee4c93bfde6992445a253901a9","tom","001","None"));
        products.add(new Product("test","4", "Category", "Fantastic product Fantastic productFantastic productFantastic productFantastic productFantastic productFantastic productFantastic productFantastic product", "180", "Used", "Yesterday", "Available", "https://img1.baidu.com/it/u=4185713029,1649043310&fm=253&fmt=auto&app=138&f=JPEG?w=450&h=600","tom","001","None"));
        products.add(new Product("test","5", "Category", "Superb product Superb productSuperb productSuperb productSuperb productSuperb productSuperb productSuperb productSuperb product", "250", "New", "Today", "Available", "https://img01.yzcdn.cn/upload_files/2020/03/29/FsKNyYUq6i2JbYVSMa-PAyaj_pya.jpg%21middle.jpg","tom","001","None"));
        products.add(new Product("test","6", "Category", "Excellent product Excellent productExcellent productExcellent productExcellent productExcellent productExcellent productExcellent productExcellent product", "300", "Used", "Yesterday", "Available", "https://pic.rmb.bdstatic.com/bjh/down/dc41f9f74d70e08b5877df601c381c42.jpeg@wm_2,t_55m+5a625Y+3L+enn+S4gOermeS6jOaJi+WKnuWFrOWutuWFtw==,fc_ffffff,ff_U2ltSGVp,sz_20,x_13,y_13","tom","001","None"));
        products.add(new Product("test","7", "Category", "Amazing product Amazing productAmazing productAmazing productAmazing productAmazing productAmazing productAmazing productAmazing product", "170", "New", "Today", "Available", "https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2022%2F0628%2F06131eb4j00re6keu001dd200m800aag00g6007h.jpg&thumbnail=660x2147483647&quality=80&type=jpg","tom","001","None"));
        products.add(new Product("test","8", "Category", "Incredible product Incredible productIncredible productIncredible productIncredible productIncredible productIncredible productIncredible productIncredible product", "220", "Used", "Yesterday", "Available", "https://img2.baidu.com/it/u=2984813231,3723666658&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500","tom","001","None"));
        products.add(new Product("test","9", "Category", "Outstanding product Outstanding productOutstanding productOutstanding productOutstanding productOutstanding productOutstanding productOutstanding productOutstanding product", "280", "New", "Today", "Available", "https://img0.baidu.com/it/u=1257588435,2863691321&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500","tom","001","None"));
        products.add(new Product("test","10", "Category", "Spectacular product Spectacular productSpectacular productSpectacular productSpectacular productSpectacular productSpectacular productSpectacular productSpectacular product", "320", "Used", "Yesterday", "Available", "https://img2.baidu.com/it/u=271958556,2894190561&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500","tom","001","None"));

        return products;
    }

}