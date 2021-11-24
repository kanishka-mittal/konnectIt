package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Post extends AppCompatActivity {
    int postId,userId;
    Button btnpostComment;
    String commenttext;
    EditText commentText;
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

        commentText= findViewById(R.id.editTextComment);
        btnpostComment = findViewById(R.id.buttoncomment);
        btnpostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commenttext=commentText.getText().toString();
                if(commenttext.equals("")){
                    Toast.makeText(Post.this, "Please fill comment text field", Toast.LENGTH_SHORT).show();
                }
                else{
                    //String method="post";
                    String method="addcomment";
                    Post_bgTasks bgTask=new Post_bgTasks(Post.this,postId,userId,commenttext);
                    bgTask.execute(method);
                    finish();
                    Intent intent=new Intent(Post.this,Post.class);
                    intent.putExtra("postId",postId);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }

            }
        });


    }
}