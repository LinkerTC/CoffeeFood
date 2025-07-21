package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.databinding.ViewholderItemsListBinding;
import com.example.foodapp.databinding.ViewholderPopularBinding;

import java.util.List;

public class ItemsListCategoryAdapter extends RecyclerView.Adapter<ItemsListCategoryAdapter.Viewholder> {
    private List<ItemsModel> items;
    private Context context;

    public ItemsListCategoryAdapter(List<ItemsModel> items) {
        this.items = items;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderItemsListBinding binding;

        public Viewholder(@NonNull ViewholderItemsListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderItemsListBinding binding = ViewholderItemsListBinding
                .inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ItemsModel item = items.get(position);
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("$" + item.getPrice());
        holder.binding.subtitleTxt.setText(String.valueOf(item.getExtra()));

        Glide.with(context)
                .load(item.getPicUrl().get(0))
                .into(holder.binding.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}