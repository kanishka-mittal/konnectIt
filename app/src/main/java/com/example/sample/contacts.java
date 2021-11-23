package com.example.sample;

public class contacts {
    private int uid;
    private String image;
    private String username;

    public contacts(int uid,String image,String username) {
        this.uid=uid;
        this.image = image;
        this.username=username;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}