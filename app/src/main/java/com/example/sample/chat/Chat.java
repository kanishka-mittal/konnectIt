package com.example.sample.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sample.MemoryData;
import com.example.sample.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://konnectit-4ef05-default-rtdb.asia-southeast1.firebasedatabase.app/");

    private List<ChatList> chatLists = new ArrayList<>();
    private String chatKey;
    String getUserName = "";
    private RecyclerView chattingRecyclerView;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backBtn = findViewById(R.id.BackBtn);
        final TextView nameTV = findViewById(R.id.name);
        final EditText messageEditText = findViewById(R.id.messageEditTxt);
        final ImageView sendBtn = findViewById(R.id.sendBtn);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        chattingRecyclerView = findViewById(R.id.chattingRecyclerview);

        final String username = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");

        getUserName = MemoryData.getData(Chat.this);
        nameTV.setText(username);
        Picasso.get().load(getProfilePic).into(profilePic);

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter=new ChatAdapter(chatLists,Chat.this);
        chattingRecyclerView.setAdapter(chatAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);

                    }
                }

                if(snapshot.hasChild("chat"))
                {
                    if(snapshot.child("chat").child("chatKey").hasChild("messages"))
                    {
                        chatLists.clear();
                        for(DataSnapshot messagesSnapshot:snapshot.child("chat").child("chatKey").child("messages").getChildren())
                        {
                            if(messagesSnapshot.hasChild("msg")&&messagesSnapshot.hasChild("fromuser")) {

                                final String messageTimeStamps = messagesSnapshot.getKey();
                                final String getUsername=messagesSnapshot.child("fromuser").getValue(String.class);
                                final String getMsg=messagesSnapshot.child("msg").getValue(String.class);



                                Timestamp timestamp=new Timestamp(Long.parseLong(messageTimeStamps));
                                Date date=new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                ChatList chatList=new ChatList(getUserName,getUsername,getMsg,simpleDateFormat.format(date),simpleTimeFormat.format(timestamp));
                                chatLists.add(chatList);

                                if(loadingFirstTime || Long.parseLong(messageTimeStamps) >Long.parseLong(MemoryData.getLastMsgTS(Chat.this,chatKey)))
                                {
                                    loadingFirstTime=false;
                                    MemoryData.saveLastMsgTS(messageTimeStamps, chatKey, Chat.this);


                                    chatAdapter.updateChatList(chatLists);
                                    chattingRecyclerView.scrollToPosition(chatLists.size()-1);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                final String getTxtMessage = messageEditText.getText().toString();


                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserName);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(username);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimeStamp).child("msg").setValue(getTxtMessage);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimeStamp).child("username").setValue(getUserName);

                messageEditText.setText("");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}