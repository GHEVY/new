package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.UUID;

public class ImageItem {

    private UUID id;
    private int image;
    private String text;
    private boolean isFavorite;
    private String continent;

    public ImageItem(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getContinent() {
        return continent;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean fav) {
        isFavorite = fav;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }


}
