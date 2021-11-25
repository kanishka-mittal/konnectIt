package com.example.sample.chat;

public class ChatList {
    private String username;
    private String friendname;
    private String message;
    private String date;
    private String time;

    public ChatList(String username, String friendname, String message, String date, String time) {
        this.username = username;
        this.friendname = friendname;
        this.message = message;
        this.date = date;
        this.time = time;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return friendname;
    }

    public void setName(String name) {
        this.friendname = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
