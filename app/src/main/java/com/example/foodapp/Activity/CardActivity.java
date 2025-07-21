package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.foodapp.Adapter.CartAdapter;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.Helper.ChangeNumberItemsListener;
import com.example.foodapp.databinding.ActivityCardBinding;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {

    private ActivityCardBinding binding;
    private ManagmentCart managmentCart;
    private Random random = new Random();
    private double tax = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        calculateCart();
        setVariable();
        initCartList();
    }

    // Thiết lập các sự kiện
    private void setVariable() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Tính toán tổng tiền, thuế, phí giao hàng và cập nhật UI
    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 15;

        tax = ((managmentCart.getTotalFee() * percentTax) * 100) / 100.0;
        double total = ((managmentCart.getTotalFee() + tax + delivery) * 100) / 100.0;
        double itemTotal = (managmentCart.getTotalFee() * 100) / 100.0;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.totalTaxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText(String.format("$%.2f", total));
    }

    // Hiển thị danh sách giỏ hàng và lắng nghe thay đổi số lượng
    private void initCartList() {
        binding.listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<ItemsModel> cartList = managmentCart.getListCart();
        CartAdapter adapter = new CartAdapter(cartList, this, new ChangeNumberItemsListener() {
            @Override
            public void onChanged() {
                calculateCart();
            }
        });
        binding.listView.setAdapter(adapter);
    }
}
