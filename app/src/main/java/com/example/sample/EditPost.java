package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditPost extends AppCompatActivity {
    int postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt("postId");
        }
        EditPostLoadBgTask editPostLoadBgTask=new EditPostLoadBgTask(postId,this);
        editPostLoadBgTask.execute();
    }
}