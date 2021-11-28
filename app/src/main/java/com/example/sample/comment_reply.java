package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class comment_reply extends AppCompatActivity {
    int commentId,userId,commentuserId;
    Button btnpostReply;
    String replytext;
    EditText replyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_reply);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            commentId = extras.getInt("commentId");
            userId=extras.getInt("userId");
        }
        SingleCommentBgTask singleCommentBgTask=new SingleCommentBgTask(this,commentId,userId);
        singleCommentBgTask.execute("load");

        String method="replyload";
        RecyclerView repliesRecView;
        Replies_recview_Adapter RepliesRecViewAdapter;
        ArrayList<Replies> replies;
        comments_bgtasks bgTask=new comments_bgtasks(comment_reply.this,commentId,userId);
        bgTask.execute(method);


        replyText= findViewById(R.id.editTextCommentpage);
        btnpostReply = findViewById(R.id.buttonCommentpage);
        btnpostReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replytext=replyText.getText().toString();
                if(replyText.equals("")){
                    Toast.makeText(comment_reply.this, "Please fill reply text field", Toast.LENGTH_SHORT).show();
                }
                else{
                    String method="addreply";

                    comments_bgtasks bgTask=new comments_bgtasks(comment_reply.this,commentId,userId,replytext);
                    bgTask.execute(method);
                    Intent intent=new Intent(comment_reply.this,comment_reply.class);
                    intent.putExtra("commentId",commentId);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);
        if(true){
            //BOTTOMBAR NAVIGATION


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
                            finishAffinity();
                            return true;
                    }

                    switch (item.getItemId()){
                        case R.id.news:
                            Intent intent=new Intent(getApplicationContext(),NewsFeed.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finishAffinity();
                            return true;
                    }switch (item.getItemId()){
                        case R.id.notifs:
                            Intent intent=new Intent(getApplicationContext(),Notifications.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finishAffinity();
                            return true;

                    }switch (item.getItemId()){
                        case R.id.friendRequests:
                            Intent intent=new Intent(getApplicationContext(),FriendRequests.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finishAffinity();
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