package com.example.sample.messages;

public class messagesList {
    public String fname,lastMessage,profilePic,chatKey;

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    private int unseenMessages;




    public messagesList(String fname, String lastMessage, String profilePic, int unseenMessages,String chatKey) {
        this.profilePic=profilePic;
        this.fname = fname;
        this.lastMessage=lastMessage;
        this.unseenMessages=unseenMessages;
        this.chatKey=chatKey;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }
}
