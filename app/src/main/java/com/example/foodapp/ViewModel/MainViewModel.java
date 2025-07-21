package com.example.foodapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.Domain.BannerModel;
import com.example.foodapp.Domain.CategoryModel;
import com.example.foodapp.Domain.ItemsModel;
import com.example.foodapp.Repository.MainRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MainRepository repository;

    public MainViewModel() {
        repository = new MainRepository();
    }

    public LiveData<List<BannerModel>> loadBanner() {
        return repository.loadBanner();
    }

    public LiveData<List<CategoryModel>> loadCategory() {
        return repository.loadCategory();
    }

    public LiveData<List<ItemsModel>> loadPopular() {
        return repository.loadPopular();
    }

    public LiveData<List<ItemsModel>> loadItems(String categoryId) {
        return repository.loadItemCategory(categoryId);
    }

}