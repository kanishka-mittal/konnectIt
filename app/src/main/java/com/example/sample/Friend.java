package com.example.sample;

public class Friend {
    private String userName,firstName;
    private int userId;

    public Friend(String userName, String firstName,int userId) {
        this.userName = userName;
        this.firstName = firstName;
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    @Override
    public String toString() {
        return "Friend{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
