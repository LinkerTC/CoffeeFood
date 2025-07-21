package com.example.foodapp.Domain;

import androidx.annotation.NonNull;
import java.util.Objects;

public class CategoryModel {
    @NonNull
    private String title;
    private int id;

    // Constructor mặc định
    public CategoryModel() {
        this.title = "";
        this.id = 0;
    }

    // Constructor có tham số
    public CategoryModel(@NonNull String title, int id) {
        this.title = title;
        this.id = id;
    }

    // Getter và Setter cho title
    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // equals, hashCode, toString tương đương data class
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryModel)) return false;
        CategoryModel that = (CategoryModel) o;
        return id == that.id && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }

    @Override
    public String toString() {
        return "CategoryModel{title='" + title + "', id=" + id + '}';
    }
}