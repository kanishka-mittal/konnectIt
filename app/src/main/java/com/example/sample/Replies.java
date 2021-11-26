package com.example.sample;

public class Replies {
    private String userName,replyText;
    private int userId,replyId,commentID;

    public Replies(String userName, String replytext,int userId,int replyId,int commentID) {
        this.userName = userName;
        this.replyText = replytext;
        this.replyId=replyId;
        this.userId=userId;
        this.commentID=commentID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReplyText() { return replyText; }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    @Override
    public String toString() {
        return "Replies{" +
                "userName='" + userName + '\'' +
                ", replyText='" + replyText + '\'' +
                '}';
    }
}
