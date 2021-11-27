package com.example.sample;

import java.sql.Time;

public class Message {
    int senderId,recipientId;
    String time,messageTxt;

    public Message(int senderId, int recipientId, String time, String messageTxt) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.time = time;
        this.messageTxt=messageTxt;
    }

    public String getMessageTxt() {
        return messageTxt;
    }





    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
