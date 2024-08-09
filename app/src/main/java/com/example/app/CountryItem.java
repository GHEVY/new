package com.example.app;

import java.util.UUID;

public class CountryItem {

    private UUID id;
    private int image;
    private String name;
    private boolean isFavorite;
    private String continent;

    public CountryItem(UUID id) {
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

    public String getName() {
        return name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }


}
