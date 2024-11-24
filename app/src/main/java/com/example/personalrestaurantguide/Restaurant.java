package com.example.personalrestaurantguide;

public class Restaurant {
    private String name;
    private String address;
    private String tags;
    private int imageResId;

    public Restaurant(String name, String address, String tags, int imageResId) {
        this.name = name;
        this.address = address;
        this.tags = tags;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTags() {
        return tags;
    }

    public int getImageResId() {
        return imageResId;
    }
}
