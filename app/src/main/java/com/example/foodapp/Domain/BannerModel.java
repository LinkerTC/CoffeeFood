package com.example.foodapp.Domain;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class BannerModel {
    @NotNull
    private String url;

    public BannerModel() {
        this.url = "";
    }

    public BannerModel(@NotNull String url) {
        this.url = url;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BannerModel that = (BannerModel) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "BannerModel{url='" + url + "'}";
    }
}
