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

import com.bumptech.glide.Glide;

public class Post extends AppCompatActivity {
    int postId,userId,postNumComments,postNumLikes,postUserId;
    Button btnpostComment;
    String commenttext,postFirstName,postUserName,postText,postImageURL,ImageURL;
    EditText commentText;
    TextView userName,firstName,numLikes,numComments,postContent;
    ImageView like,dislike,postImage,profilepic,share,dustbin;
    public Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt("postId");
            userId=extras.getInt("userId");
            postNumLikes=extras.getInt("postNumLikes");
            postUserId=extras.getInt("postUserId");
            postNumComments=extras.getInt("postNumComments");
            postFirstName=extras.getString("postFirstName");
            postUserName=extras.getString("postUserName");
            postText=extras.getString("postText");
            postImageURL=extras.getString("postImageURL");
            ImageURL=extras.getString("ImageURL");
        }

        dustbin=findViewById(R.id.dustbinpostpage);
        userName=findViewById(R.id.userNamepostpage);
        firstName=findViewById(R.id.firstNamepostpage);
//        postListItemParent=findViewById(R.id.postListItemParent);
        like=findViewById(R.id.likepostpage);
        dislike=findViewById(R.id.dislikepostpage);
        postImage=findViewById(R.id.postImagepostpage);
        numComments=findViewById(R.id.numCommentspostpage);
        numLikes=findViewById(R.id.numLikespostpage);
        profilepic=findViewById(R.id.profilepicpostpage);
        postContent=findViewById(R.id.postTextpostpage);
        share=findViewById(R.id.sharepostpage);

        if(userId==postUserId){
            dustbin.setVisibility(View.VISIBLE);
        }

        firstName.setText(postFirstName);
        userName.setText(postUserName);
        numLikes.setText(Integer.toString(postNumLikes));
        numComments.setText(Integer.toString(postNumComments));
        if(!(ImageURL.equals("null"))){
            Glide.with(this).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(postUserId)+".png").into(profilepic);
        }
        //System.out.println(post.getPostImageURL());
        if((postImageURL).equals("null")){
            postImage.setVisibility(View.GONE);
        }else{
            postImage.setVisibility(View.VISIBLE);
            //System.out.println(post.getPostImageURL());
            Glide.with(this).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/posts_image/"+Integer.toString(postId)+".png").into(postImage);
        }
        postContent.setText(postText);
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
                    intent.putExtra("userId",userId);
                    intent.putExtra("postId",postId);
                    intent.putExtra("postNumComments",postNumComments);
                    intent.putExtra("postNumLikes",postNumLikes);
                    intent.putExtra("postFirstName",postFirstName);
                    intent.putExtra("postUserName",postUserName);
                    intent.putExtra("postUserId",postUserId);
                    intent.putExtra("postText",postText);
                    intent.putExtra("postImageURL",postImageURL);
                    intent.putExtra("ImageURL",ImageURL);
                    startActivity(intent);
                }

            }
        });

    }
}