package com.example.sample;

public class LikeByModel {
    String userName,firstName,imageurl;
    int userId;
    public LikeByModel(String userName, String firstName,String imageurl,int userId) {
        this.userId=userId;
        this.userName = userName;
        this.firstName = firstName;
        this.imageurl=imageurl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
}
