package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.foodapp.Adapter.WishListAdapter;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.Helper.TinyDB;
import com.example.foodapp.databinding.ActivityWishlistBinding;
import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity {

    private ActivityWishlistBinding binding;
    private TinyDB tinyDB;
    private ArrayList<ItemsModel> wishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(this);
        wishList = tinyDB.getListObject("WishList");
        if (wishList == null) wishList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WishListAdapter adapter = new WishListAdapter(wishList, this);
        binding.recyclerView.setAdapter(adapter);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
