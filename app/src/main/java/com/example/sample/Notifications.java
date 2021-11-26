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

public class Notifications extends AppCompatActivity {
    private int userId;
    private ImageButton btnSearch;
    private String searchTxt;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }
        btnSearch=findViewById(R.id.btnSearch);
        edtSearch=findViewById(R.id.edtSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTxt=edtSearch.getText().toString();
                Intent intent = new Intent(Notifications.this, SearchUsers.class);
                intent.putExtra("searchTxt",searchTxt);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        NotifBackgroundTask bgTask=new NotifBackgroundTask(this,userId);
        bgTask.execute("load");

        //BOTTOMBAR NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);

        bottomNavigationView.setSelectedItemId(R.id.notifs);
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
                    case R.id.friendRequests:
                        Intent intent=new Intent(getApplicationContext(),FriendRequests.class);
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