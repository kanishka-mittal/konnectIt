package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        }
        NewsFeedBackgroundTask bgTask=new NewsFeedBackgroundTask(this,userId);
        bgTask.execute("loadNF");
        btnAddPost=findViewById(R.id.btnAddPost);
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewsFeed.this,NewsFeedAdd.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


        //BOTTOMBAR NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);

        bottomNavigationView.setSelectedItemId(R.id.news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.myprofile:
                        Intent intent=new Intent(getApplicationContext(),Profile.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("accessedByUser",userId);
                        startActivity(intent);

                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }

                switch (item.getItemId()){
                    case R.id.notifs:
                        Intent intent=new Intent(getApplicationContext(),Notifications.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("accessedByUser",userId);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                switch (item.getItemId()){
                    case R.id.friendRequests:
                        Intent intent=new Intent(getApplicationContext(),FriendRequests.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);

                        overridePendingTransition(0,0);
                        finish();
                        return true;

                }
                return false;
            }
        });

    }

}