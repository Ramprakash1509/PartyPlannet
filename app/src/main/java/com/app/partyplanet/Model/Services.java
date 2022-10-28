package com.app.partyplanet.Model;

public class Services {
    int image;
    String description;

    public Services(int image, String description) {
        this.image = image;
        this.description = description;
    }

    public Services() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
