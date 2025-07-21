package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.foodapp.ViewModel.MainViewModel;
import com.example.foodapp.Adapter.ItemsListCategoryAdapter;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.databinding.ActivityItemsListBinding;

import java.util.List;

public class ItemsListActivity extends AppCompatActivity {

    private ActivityItemsListBinding binding;
    private MainViewModel viewModel = new MainViewModel();
    private String id = "";
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        binding = ActivityItemsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
        initList();
    }

    private void getBundles() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        if (title != null) {
            binding.categoryTxt.setText(title);
        }
    }

    private void initList() {
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.loadItems(id).observe(this, new Observer<List<ItemsModel>>() {
            @Override
            public void onChanged(List<ItemsModel> items) {
                binding.listView.setLayoutManager(new GridLayoutManager(ItemsListActivity.this, 2));
                binding.listView.setAdapter(new ItemsListCategoryAdapter(items));
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.backBtn.setOnClickListener(v -> finish());
    }

    // Nếu bạn dùng edge-to-edge UI:
    private void enableEdgeToEdge() {
        // Nếu có dùng WindowCompat, ViewCompat...
        // WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        // ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), ...);
    }
}