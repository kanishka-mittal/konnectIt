package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Friends extends AppCompatActivity {
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }
        String method="load";
        FriendBackgroundTask bgTask=new FriendBackgroundTask(this,userId);
        bgTask.execute(method);
    }
}