package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class comment_reply extends AppCompatActivity {
    int commentId,userId,commentuserId;
    Button btnpostReply;
    String replytext;
    EditText replyText;
    TextView commenttextview;
    TextView usernametextview;
    String commentText;
    String username;
    ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_reply);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            commentId = extras.getInt("commentId");
            userId=extras.getInt("userId");
            username=extras.getString("username");
            commentText=extras.getString("commentText");
            commentuserId=extras.getInt("commentuserId");
        }

        profilepic=findViewById(R.id.profilepicCommentpage);
        commenttextview=findViewById(R.id.commentextCommentpage);
        usernametextview=findViewById(R.id.userNameCommentpage);
        commenttextview.setText(commentText);
        usernametextview.setText(username);
        Glide.with(this).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(commentuserId)+".png").into(profilepic);

        String method="replyload";
        comments_bgtasks bgTask=new comments_bgtasks(this,commentId,userId);
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
                    finish();
                    Intent intent=new Intent(comment_reply.this,comment_reply.class);
                    intent.putExtra("commentId",commentId);
                    intent.putExtra("userId",userId);
                    intent.putExtra("commentuserId",commentuserId);
                    intent.putExtra("commentText",commentText);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }

            }
        });

    }
}