package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Post extends AppCompatActivity {
    int postId,userId;
    Button btnpostComment;
    String commenttext;
    EditText commentText;
    ImageView share;
    public Context ctx;
    int accessedByUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt("postId");
            userId=extras.getInt("userId");
        }
        SinglePostBgTask singlePostBgTask =new SinglePostBgTask(this,postId,userId);
        try {
            singlePostBgTask.execute("load").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        share=findViewById(R.id.sharepostpage);
        ctx=this;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.dialog_layout);
                DialogBackgroundTask bgTask=new DialogBackgroundTask(dialog,ctx,userId,postId);
                bgTask.execute("load");
                dialog.show();
            }
        });
        String method="commentsload";
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
                    String method="addcomment";
                    Post_bgTasks bgTask=new Post_bgTasks(Post.this,postId,userId,commenttext);
                    bgTask.execute(method);
                    finish();
                    Intent intent=new Intent(Post.this,Post.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("postId",postId);
                    startActivity(intent);
                }
//
            }
        });

    }
}