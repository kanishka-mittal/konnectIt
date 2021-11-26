package com.example.sample;

public class FrModel {
    private String userName,firstName,imageURL;
    int userId;

    public FrModel(String userName, String firstName, String imageURL, int userId) {
        this.userName = userName;
        this.firstName = firstName;
        this.imageURL = imageURL;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
