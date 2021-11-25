package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;

public class NewsFeed extends AppCompatActivity {
    private int userId;
    private Button btnAddPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
            System.out.println("Yo");
            System.out.println(userId);
            System.out.println("Yo");
        }
        NewsFeedBackgroundTask bgTask=new NewsFeedBackgroundTask(this,userId);
        bgTask.execute();
        btnAddPost=findViewById(R.id.btnAddPost);
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewsFeed.this,NewsFeedAdd.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

    }

}