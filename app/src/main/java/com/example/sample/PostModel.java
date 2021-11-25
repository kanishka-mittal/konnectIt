package com.example.sample;

public class PostModel {
    String userName,firstName,postText,postImageURL;
    int postId,userId;
    int numLikes,numComments;

    public PostModel(String userName, String firstName, int postId, int userId, int numLikes, int numComments,String postText,String postImageURL) {
        this.userName = userName;
        this.firstName = firstName;
        this.postId = postId;
        this.userId = userId;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.postText=postText;
        this.postImageURL=postImageURL;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

//    public int getNumLikes() {
//        return numLikes;
//    }
//
//    public void setNumLikes(int numLikes) {
//        this.numLikes = numLikes;
//    }
}
