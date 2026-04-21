package com.blinge.deliveryguy.model;

public class ImageItem {
    private final String imageUrl;
    private final String description;

    public ImageItem(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
}
