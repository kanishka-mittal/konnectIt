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

public class SearchUsers extends AppCompatActivity {
    private ImageButton btnSearch;
    private String searchTxt;
    private EditText edtSearch;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchTxt = extras.getString("searchTxt");
            userId=extras.getInt("userId");
        }
        btnSearch=findViewById(R.id.btnSearch);
        edtSearch=findViewById(R.id.edtSearch);
        edtSearch.setText(searchTxt);
        SearchUsersBackgroundTask bgSearchTask=new SearchUsersBackgroundTask(this,userId,searchTxt);
        bgSearchTask.execute("load");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTxt=edtSearch.getText().toString();
                Intent intent = new Intent(SearchUsers.this, SearchUsers.class);
                intent.putExtra("searchTxt",searchTxt);
                intent.putExtra("userId",userId);
                startActivity(intent);
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);
        if(true){
            //BOTTOMBAR NAVIGATION


            bottomNavigationView.setSelectedItemId(R.id.myprofile);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.myprofile:
                            Intent intent=new Intent(getApplicationContext(),Profile.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("accessedByUser", userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finish();
                            return true;
                    }

                    switch (item.getItemId()){
                        case R.id.news:
                            Intent intent=new Intent(getApplicationContext(),NewsFeed.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finish();
                            return true;
                    }switch (item.getItemId()){
                        case R.id.notifs:
                            Intent intent=new Intent(getApplicationContext(),Notifications.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finish();
                            return true;

                    }switch (item.getItemId()){
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
        }else{
            bottomNavigationView.setVisibility(View.GONE);
        }
    }
}