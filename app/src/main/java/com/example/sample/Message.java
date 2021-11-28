package com.example.sample;

import java.sql.Time;

public class Message {
    String senderEmail,msgTxt,timestamp;

    public Message(String senderEmail, String msgTxt, String timestamp) {
        this.senderEmail = senderEmail;
        this.msgTxt = msgTxt;
        this.timestamp = timestamp;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }


    public String getMsgTxt() {
        return msgTxt;
    }

    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
