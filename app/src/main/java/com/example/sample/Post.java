package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Post extends AppCompatActivity {
    private int postId,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt("postId");
            userId=extras.getInt("userId");
        }
        String method="postload";
        Post_bgTasks bgTask=new Post_bgTasks(this,postId,userId);
        bgTask.execute(method);
    }
}