package com.example.foodapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.ItemsListActivity;
import com.example.foodapp.Domain.CategoryModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ViewholderCategoryBinding;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    private List<CategoryModel> items;
    private Context context;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public CategoryAdapter(List<CategoryModel> items) {
        this.items = items;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCategoryBinding binding;

        public Viewholder(@NonNull ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderCategoryBinding binding = ViewholderCategoryBinding
                .inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
        final CategoryModel item = items.get(position);
        holder.binding.titleCat.setText(item.getTitle());

        holder.binding.getRoot().setOnClickListener(v -> {
            lastSelectedPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
            // Nếu muốn delay hiệu ứng, giữ nguyên Handler như bản Kotlin
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, ItemsListActivity.class);
                    intent.putExtra("id", String.valueOf(item.getId()));
                    intent.putExtra("title", item.getTitle());
                    ContextCompat.startActivity(context, intent, null);
                }
            }, 500);
        });

        if (selectedPosition == position) {
            holder.binding.titleCat.setBackgroundResource(R.drawable.brown_full_corner_bg);
            holder.binding.titleCat.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.binding.titleCat.setBackgroundResource(R.drawable.white_full_corner_bg);
            holder.binding.titleCat.setTextColor(context.getResources().getColor(R.color.darkBrown));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

