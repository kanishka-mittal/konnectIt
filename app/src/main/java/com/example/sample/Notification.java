package com.example.sample;

public class Notification {
    private String notif;
    private String sendUser;
    private int id;

    public Notification(String notif, String sendUser, int id) {
        this.notif = notif;
        this.sendUser = sendUser;
        this.id = id;
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

