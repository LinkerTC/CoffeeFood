package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.Helper.TinyDB;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ItemsModel item;
    private ManagmentCart managmentCart;
    private String selectedSize = "Small";
    private ArrayList<ItemsModel> wishList;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy dữ liệu sản phẩm
        item = (ItemsModel) getIntent().getSerializableExtra("object");
        if (item == null) {
            finish();
            return;
        }

        // Khởi tạo Helper, List
        tinyDB = new TinyDB(this);
        wishList = tinyDB.getListObject("WishList");
        if (wishList == null) wishList = new ArrayList<>();
        managmentCart = new ManagmentCart(this);

        // Hiển thị dữ liệu sản phẩm
        if (item.getPicUrl() != null && !item.getPicUrl().isEmpty()) {
            Glide.with(this)
                    .load(item.getPicUrl().get(0))
                    .into(binding.picMain);
        }
        binding.titleBtn.setText(item.getTitle());
        binding.descriptionTxt.setText(item.getDescription());
        binding.priceTxt.setText("$" + item.getPrice());
        binding.ratingTxt.setText(String.valueOf(item.getRating()));
        binding.numberInCardTxt.setText(String.valueOf(item.getNumberInCart()));

        // Xử lý icon Yêu Thích ban đầu
        updateFavBtnColor();

        // Sự kiện nút "tim" (yêu thích)
        binding.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav()) {
                    // Xoá khỏi wishList
                    for (int i = 0; i < wishList.size(); i++) {
                        if (wishList.get(i).getTitle().equals(item.getTitle())) {
                            wishList.remove(i);
                            break;
                        }
                    }
                    tinyDB.putListObject("WishList", wishList);
                    Toast.makeText(DetailActivity.this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    wishList.add(item);
                    tinyDB.putListObject("WishList", wishList);
                    Toast.makeText(DetailActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
                updateFavBtnColor();
                // Nếu cần cập nhật badge ngoài MainActivity, có thể gửi broadcast hoặc callback
            }
        });

        // Xử lý chọn size (Small, Medium, Large)
        initSizeList();

        // Thêm vào giỏ hàng
        binding.addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numInCart;
                try {
                    numInCart = Integer.parseInt(binding.numberInCardTxt.getText().toString());
                } catch (NumberFormatException e) {
                    numInCart = 1;
                }
                item.setNumberInCart(numInCart);
                // item.setSize(selectedSize); // Nếu model có trường size
                managmentCart.insertItems(item);
                Toast.makeText(DetailActivity.this, "Added to your Cart", Toast.LENGTH_SHORT).show();
            }
        });

        // Tăng số lượng
        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numInCart = item.getNumberInCart() + 1;
                item.setNumberInCart(numInCart);
                binding.numberInCardTxt.setText(String.valueOf(numInCart));
            }
        });

        // Giảm số lượng
        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numInCart = item.getNumberInCart();
                if (numInCart > 1) {
                    numInCart--;
                    item.setNumberInCart(numInCart);
                    binding.numberInCardTxt.setText(String.valueOf(numInCart));
                }
            }
        });

        // Nút Back
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Hàm kiểm tra đã yêu thích
    private boolean isFav() {
        for (ItemsModel i : wishList) {
            if (i.getTitle().equals(item.getTitle()))
                return true;
        }
        return false;
    }

    // Đổi màu icon tim (dùng ColorFilter thay vì nhiều drawable)
    private void updateFavBtnColor() {
        if (isFav()) {
            // Đỏ khi đã yêu thích
            binding.favBtn.setColorFilter(getResources().getColor(R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            // Xám nếu chưa yêu thích
            binding.favBtn.setColorFilter(getResources().getColor(R.color.darkBrown), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    // Khởi tạo xử lý chọn size
    private void initSizeList() {
        binding.smallBtn.setBackgroundResource(R.drawable.brown_storke_bg);
        binding.mediumBtn.setBackgroundResource(0);
        binding.largeBtn.setBackgroundResource(0);

        binding.smallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSize = "Small";
                binding.smallBtn.setBackgroundResource(R.drawable.brown_storke_bg);
                binding.mediumBtn.setBackgroundResource(0);
                binding.largeBtn.setBackgroundResource(0);
            }
        });

        binding.mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSize = "Medium";
                binding.smallBtn.setBackgroundResource(0);
                binding.mediumBtn.setBackgroundResource(R.drawable.brown_storke_bg);
                binding.largeBtn.setBackgroundResource(0);
            }
        });

        binding.largeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSize = "Large";
                binding.smallBtn.setBackgroundResource(0);
                binding.mediumBtn.setBackgroundResource(0);
                binding.largeBtn.setBackgroundResource(R.drawable.brown_storke_bg);
            }
        });
    }
}
