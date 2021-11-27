package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChatActivity extends AppCompatActivity {
    int userId;
    int friendId;
    EditText sendMsg;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
            friendId=extras.getInt("friendId");
        }
        ChatBackgroundTask chatBackgroundTask=new ChatBackgroundTask(userId,friendId,this);
        chatBackgroundTask.execute("load");
        sendMsg=findViewById(R.id.sendMsg);
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatBackgroundTask chatBackgroundTask1=new ChatBackgroundTask(userId,friendId,ChatActivity.this);
                chatBackgroundTask1.execute("send",sendMsg.getText().toString());
            }
        });
    }
}