package com.example.sample;

public class Replies {
    private String userName,replyText;
    private int userId,replyId,commentID,numLikes;

    public Replies(String userName, String replytext,int userId,int replyId,int commentID,int numLikes) {
        this.userName = userName;
        this.replyText = replytext;
        this.replyId=replyId;
        this.userId=userId;
        this.commentID=commentID;
        this.numLikes=numLikes;

    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
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
