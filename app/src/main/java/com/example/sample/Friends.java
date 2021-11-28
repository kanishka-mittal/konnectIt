package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class
Friends extends AppCompatActivity {
    private int userId;
    private int accessedByUser;
    private ImageButton btnSearch;
    String searchTxt;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
            accessedByUser=extras.getInt("accessedByUser");
        }
        String method="load";
        FriendBackgroundTask bgTask=new FriendBackgroundTask(this,userId, accessedByUser);
        bgTask.execute(method);
        btnSearch=findViewById(R.id.btnSearch);
        edtSearch=findViewById(R.id.edtSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTxt=edtSearch.getText().toString();
                Intent intent = new Intent(Friends.this,SearchFriends.class);
                intent.putExtra("accessedByUser", accessedByUser);
                intent.putExtra("searchTxt",searchTxt);
                intent.putExtra("userId",userId);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);
        bottomNavigationView.setSelectedItemId(R.id.myprofile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myprofile:
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("accessedByUser", accessedByUser);
                        startActivity(intent);
                        finish();

                        overridePendingTransition(0, 0);
                        return true;
                }
                switch (item.getItemId()) {
                    case R.id.news:
                        Intent intent = new Intent(getApplicationContext(), NewsFeed.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();

                        overridePendingTransition(0, 0);
                        return true;
                }
                switch (item.getItemId()) {
                    case R.id.notifs:
                        Intent intent = new Intent(getApplicationContext(), Notifications.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();

                        overridePendingTransition(0, 0);
                        return true;

                }
                switch (item.getItemId()) {
                    case R.id.friendRequests:
                        Intent intent = new Intent(getApplicationContext(), FriendRequests.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();

                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }
}