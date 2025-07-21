package com.example.foodapp.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemsModel implements Serializable {
    private String title = "";
    private String description = "";
    private ArrayList<String> picUrl = new ArrayList<>();
    private double price = 0.0;
    private double rating = 0.0;
    private int numberInCart = 0;
    private String extra = "";

    // Constructors
    public ItemsModel() { }

    public ItemsModel(String title, String description, ArrayList<String> picUrl,
                      double price, double rating, int numberInCart, String extra) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.rating = rating;
        this.numberInCart = numberInCart;
        this.extra = extra;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ArrayList<String> getPicUrl() { return picUrl; }
    public void setPicUrl(ArrayList<String> picUrl) { this.picUrl = picUrl; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getNumberInCart() { return numberInCart; }
    public void setNumberInCart(int numberInCart) { this.numberInCart = numberInCart; }

    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
}
