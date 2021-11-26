package com.example.sample;

public class Notification {
    private String notif;
    private String sendUser;
    private int id,sendUserId;
    private String imageUrl;
    public Notification(String notif, String sendUser, int id,int sendUserId,String imageUrl) {
        this.notif = notif;
        this.sendUser = sendUser;
        this.id = id;
        this.sendUserId=sendUserId;
        this.imageUrl=imageUrl;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotif() {
        return notif;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notif='" + notif + '\'' +
                ", sendUser='" + sendUser + '\'' +
                '}';
    }
}

