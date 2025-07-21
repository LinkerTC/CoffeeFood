package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.PopularAdapter;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.Helper.TinyDB;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.example.foodapp.ViewModel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final MainViewModel viewModel = new MainViewModel();
    private PopularAdapter popularAdapter;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(this);

        initBanner();
        initCategory();
        initPopular();
        initBottomMenu();
        initWishList();
        updateWishListBadge();
        updateCartBadge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWishListBadge();
        updateCartBadge();
    }

    // Cập nhật badge số sản phẩm yêu thích (WishList)
    private void updateWishListBadge() {
        ArrayList<ItemsModel> wishList = tinyDB.getListObject("WishList");
        int count = (wishList != null) ? wishList.size() : 0;
        if (count > 0) {
            binding.wishBadge.setText(String.valueOf(count));
            binding.wishBadge.setVisibility(View.VISIBLE);
        } else {
            binding.wishBadge.setVisibility(View.GONE);
        }
    }

    // Cập nhật badge số lượng sản phẩm trong giỏ hàng (Cart)
    private void updateCartBadge() {
        int count = 0;
        ArrayList<ItemsModel> cartList = new ArrayList<>();
        cartList = new TinyDB(this).getListObject("CartList");
        if (cartList != null) {
            for (ItemsModel item : cartList) {
                count += item.getNumberInCart();
            }
        }
        if (count > 0) {
            binding.cartBadge.setText(count > 99 ? "99+" : String.valueOf(count));
            binding.cartBadge.setVisibility(View.VISIBLE);
        } else {
            binding.cartBadge.setVisibility(View.GONE);
        }
    }

    // Khởi tạo sự kiện cho nút WishList
    private void initWishList() {
        binding.wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Intent intent = new Intent(MainActivity.this, WishListActivity.class);
                startActivity(intent);
            }
        });
    }

    // Khởi tạo sự kiện cho nút Cart
    private void initBottomMenu() {
        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hiển thị danh sách sản phẩm nổi bật và xử lý tìm kiếm theo title
    private void initPopular() {
        binding.progressBarPopulate.setVisibility(View.VISIBLE);
        viewModel.loadPopular().observeForever(items -> {
            binding.recyclerViewPopulate.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            popularAdapter = new PopularAdapter(items);
            binding.recyclerViewPopulate.setAdapter(popularAdapter);
            binding.progressBarPopulate.setVisibility(View.GONE);

            // Thêm tính năng tìm kiếm realtime theo title trong danh sách sản phẩm
            binding.searchEditText.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (popularAdapter != null) {
                        popularAdapter.getFilter().filter(s);
                    }
                }
                @Override public void afterTextChanged(Editable s) { }
            });
        });
    }

    // Hiển thị danh mục sản phẩm dạng ngang
    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        viewModel.loadCategory().observeForever(categories -> {
            binding.categoryView.setLayoutManager(
                    new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
            );
            binding.categoryView.setAdapter(new CategoryAdapter(categories));
            binding.progressBarCategory.setVisibility(View.GONE);
        });
        viewModel.loadCategory();
    }

    // Hiển thị banner trên đầu trang
    private void initBanner() {
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        viewModel.loadBanner().observeForever(bannerList -> {
            if (bannerList != null && !bannerList.isEmpty()) {
                Glide.with(MainActivity.this)
                        .load(bannerList.get(0).getUrl())
                        .into(binding.banner);
                binding.progressBarBanner.setVisibility(View.GONE);
            }
            viewModel.loadBanner();
        });
    }
}
