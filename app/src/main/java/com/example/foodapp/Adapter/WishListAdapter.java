package com.example.foodapp.Adapter;

import android.app.Activity;
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
import com.example.foodapp.Helper.TinyDB;
import com.example.foodapp.databinding.ViewholderWishlistBinding;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.Viewholder> {

    private ArrayList<ItemsModel> items;
    private Context context;
    private TinyDB tinyDB;

    public WishListAdapter(ArrayList<ItemsModel> items, Context context) {
        this.items = items;
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderWishlistBinding binding;
        public Viewholder(ViewholderWishlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderWishlistBinding binding = ViewholderWishlistBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ItemsModel item = items.get(position);

        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.feeEachItem.setText(String.format("$%.2f", item.getPrice()));

        // Hiển thị subtitle
        if (item.getExtra() != null) {
            holder.binding.subtitleTxt.setText(String.valueOf(item.getExtra()));
        } else {
            holder.binding.subtitleTxt.setText("");
        }

        // Load ảnh sản phẩm
        if (item.getPicUrl() != null && !item.getPicUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getPicUrl().get(0))
                    .centerCrop()
                    .into(holder.binding.pic);
        } else {
            holder.binding.pic.setImageResource(android.R.color.transparent);
        }

        // Xử lý click ảnh: mở chi tiết sản phẩm
        holder.binding.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object", item);
                context.startActivity(intent);
            }
        });

        // Xử lý nút xóa sản phẩm yêu thích
        holder.binding.removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;
                items.remove(pos);
                tinyDB.putListObject("WishList", items);
                notifyItemRemoved(pos);
                // Gọi cập nhật badge nếu cần, theo cấu trúc project của bạn
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
