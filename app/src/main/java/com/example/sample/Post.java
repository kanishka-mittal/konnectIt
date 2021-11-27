package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
    ImageView share,like,dislike,dustbinpostpage;
    public Context ctx;
    TextView numLikes;
    int accessedByUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt("postId");
            userId=extras.getInt("userId");
            System.out.println(postId);
            System.out.println(userId);
        }
        SinglePostBgTask singlePostBgTask =new SinglePostBgTask(this,postId,userId);
        try {
            singlePostBgTask.execute("load").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SinglePostBgTask singlePostBgTask2 =new SinglePostBgTask(this,postId,userId);
        try {
            singlePostBgTask2.execute("isLiked").get();
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
        numLikes=findViewById(R.id.numLikespostpage);
        numLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(ctx);
                dialog.setContentView(R.layout.likes_dialog_layout);
                LikesDialogBackgroundTask likesDialogBackgroundTask=new LikesDialogBackgroundTask(dialog,ctx);
                likesDialogBackgroundTask.execute("loadLikesPosts",Integer.toString(postId));
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
            }
        });
        like=findViewById(R.id.likepostpage);
        dislike=findViewById(R.id.dislikepostpage);
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinglePostBgTask bgTask=new SinglePostBgTask(ctx,postId,userId);
                bgTask.execute("like",Integer.toString(postId),Integer.toString(userId));
                dislike.setVisibility(View.GONE);
                like.setVisibility(View.VISIBLE);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinglePostBgTask bgTask=new SinglePostBgTask(ctx,postId,userId);
                bgTask.execute("unlike",Integer.toString(postId),Integer.toString(userId));
                dislike.setVisibility(View.VISIBLE);
                like.setVisibility(View.GONE);
            }
        });
        dustbinpostpage=findViewById(R.id.dustbinpostpage);
        dustbinpostpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("here");
                AlertDialog.Builder builder=new AlertDialog.Builder(Post.this);
                builder.setMessage("Are you sure you want to delete the post permanently?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeletePostBgTask deletePostBgTask=new DeletePostBgTask(Post.this,postId);
                        deletePostBgTask.execute();
                        finish();
                    }
                });
                builder.show();
            }
        });
    }
}