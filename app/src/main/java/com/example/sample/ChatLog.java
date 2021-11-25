package com.example.sample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sample.messages.messagesAdapter;
import com.example.sample.messages.messagesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatLog extends AppCompatActivity {
    private String username;
    private List<messagesList> messageslist=new ArrayList<>();
    private RecyclerView messagesRecyclerView;
    private boolean dataSet=false;
    private messagesAdapter MessagesAdapter;
    private int unseenMessages=0;
    private String lastMessage="";
    private String chatKey="";

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://konnectit-4ef05-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlog);

        final CircleImageView userProfilePic=findViewById(R.id.user_profile_pic);
        messagesRecyclerView=findViewById(R.id.messagesRecyclerView);
        username=getIntent().getStringExtra("username");

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        MessagesAdapter=new messagesAdapter(messageslist,ChatLog.this);
        messagesRecyclerView.setAdapter(MessagesAdapter);
        
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading.....");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final String profilePicUrl=snapshot.child("users").child("username").child("profile_pic").getValue(String.class);
                if(!profilePicUrl.isEmpty()) {
                    Picasso.get().load(profilePicUrl).into(userProfilePic);
                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageslist.clear();
                    unseenMessages=0;
                    lastMessage="";
                    chatKey="";
                    for(DataSnapshot dataSnapShot:snapshot.child("users").getChildren())
                    {
                        final String getusername=dataSnapShot.getKey();
                        dataSet=false;

                        if(!getusername.equals(username)){
                            final String getProfilePic=dataSnapShot.child("profile_pic").getValue(String.class);



                            databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int getChatCounts=(int)snapshot.getChildrenCount();

                                    if(getChatCounts>0)
                                    {
                                        for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                                        {
                                            final String getKey=dataSnapshot1.getKey();
                                            chatKey=getKey;

                                            if(dataSnapshot1.hasChild("user_1")&&dataSnapshot1.hasChild("user_2"))
                                            {
                                                final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                                final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                                if ((getUserOne.equals(getusername) && getUserTwo.equals(username)) || (getUserOne.equals(username) && getUserTwo.equals(getusername))) {
                                                    for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {
                                                        final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                        final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(ChatLog.this, getKey));

                                                        lastMessage = chatDataSnapshot.child("msg").getValue(String.class);
                                                        if (getMessageKey > getLastSeenMessage) {
                                                            unseenMessages++;
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                    if(!dataSet)
                                    {
                                        dataSet=true;
                                        messagesList messages_list=new messagesList(getusername,lastMessage,getProfilePic,unseenMessages,chatKey);
                                        messageslist.add(messages_list);
                                        MessagesAdapter.updateData(messageslist);
                                    }



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

}
