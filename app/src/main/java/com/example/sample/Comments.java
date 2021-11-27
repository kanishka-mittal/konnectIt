package com.example.sample;

public class Comments {
    private String userName,commentText;
    private int userId,commentId,postID,numLikes;

    public Comments(String userName, String commenttext,int userId,int commentId,int postID,int numLikes) {
        this.userName = userName;
        this.commentText = commenttext;
        this.commentId=commentId;
        this.userId=userId;
        this.postID=postID;
        this.numLikes=numLikes;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getcommentId() {
        return commentId;
    }

    public void setcommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getcommentText() { return commentText; }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "userName='" + userName + '\'' +
                ", postText='" + commentText + '\'' +
                '}';
    }
}
