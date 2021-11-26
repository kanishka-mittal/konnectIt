package com.example.sample;

public class Notification {
    private String notif;
    private String sendUser;
    private int id,sendUserId,notifType,postId;
    private String imageUrl;
    public Notification(String notif, String sendUser, int id,int sendUserId,String imageUrl,int notifType,int postId) {
        this.notif = notif;
        this.sendUser = sendUser;
        this.id = id;
        this.sendUserId=sendUserId;
        this.imageUrl=imageUrl;
        this.notifType=notifType;
        this.postId=postId;
    }

    public int getNotifType() {
        return notifType;
    }

    public void setNotifType(int notifType) {
        this.notifType = notifType;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

