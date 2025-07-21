package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.Helper.ChangeNumberItemsListener;
import com.example.foodapp.databinding.ViewholderCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {

    private ArrayList<ItemsModel> listItemSelected;
    private Context context;
    private ChangeNumberItemsListener changeNumberItemListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<ItemsModel> listItemSelected, Context context,
                       ChangeNumberItemsListener changeNumberItemListener) {
        this.listItemSelected = listItemSelected;
        this.context = context;
        this.changeNumberItemListener = changeNumberItemListener;
        this.managmentCart = new ManagmentCart(context);
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;

        public Viewholder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding = ViewholderCartBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ItemsModel item = listItemSelected.get(position);

        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.feeEachItem.setText("$" + item.getPrice());
        holder.binding.totalEachItem.setText("$" + (item.getNumberInCart() * item.getPrice()));
        holder.binding.numberInCardTxt.setText(String.valueOf(item.getNumberInCart()));

        // Load ảnh sản phẩm
        Glide.with(holder.itemView.getContext())
                .load(item.getPicUrl().get(0))
                .centerCrop()
                .into(holder.binding.picCart);

        // Xử lý tăng số lượng
        holder.binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.plusItem(listItemSelected, holder.getAdapterPosition(), new ChangeNumberItemsListener() {
                    @Override
                    public void onChanged() {
                        notifyDataSetChanged();
                        if (changeNumberItemListener != null) changeNumberItemListener.onChanged();
                    }
                });
            }
        });

        // Xử lý giảm số lượng
        holder.binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.minusItem(listItemSelected, holder.getAdapterPosition(), new ChangeNumberItemsListener() {
                    @Override
                    public void onChanged() {
                        notifyDataSetChanged();
                        if (changeNumberItemListener != null) changeNumberItemListener.onChanged();
                    }
                });
            }
        });

        // Xử lý xóa sản phẩm khỏi giỏ
        holder.binding.removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.romveItem(listItemSelected, holder.getAdapterPosition(), new ChangeNumberItemsListener() {
                    @Override
                    public void onChanged() {
                        notifyDataSetChanged();
                        if (changeNumberItemListener != null) changeNumberItemListener.onChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }
}
