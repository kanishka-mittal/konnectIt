package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FriendRequests extends AppCompatActivity {
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }
        FrBackgroundTask bgTask=new FrBackgroundTask(this,userId);
        bgTask.execute("load");
        //BOTTOMBAR NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);

        bottomNavigationView.setSelectedItemId(R.id.friendRequests);
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
                        return true;
                }
                switch (item.getItemId()){
                    case R.id.news:
                        Intent intent=new Intent(getApplicationContext(),NewsFeed.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                switch (item.getItemId()){
                    case R.id.notifs:
                        Intent intent=new Intent(getApplicationContext(),Notifications.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);

                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
}