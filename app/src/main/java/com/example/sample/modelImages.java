package com.example.sample;

public class modelImages {
    private int userId;
    private String imageURL;

    public modelImages()
    {

    }

    public modelImages(int userId,String imageURL) {
        this.userId = userId;
        this.imageURL=imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
