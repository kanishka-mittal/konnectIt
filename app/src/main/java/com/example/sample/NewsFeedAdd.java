package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NewsFeedAdd extends AppCompatActivity {
    EditText edtpostText;
    Button btnPost;
    String postText;
    private int userId;
    ImageView uploadPostPic;
    private Bitmap bitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArrayVar;
    String convertImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_add);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }
        edtpostText=findViewById(R.id.postText);
        btnPost=findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postText=edtpostText.getText().toString();
                if(postText.equals("")){
                    Toast.makeText(NewsFeedAdd.this, "Please fill text field", Toast.LENGTH_SHORT).show();
                }
                else{
                    //String method="post";
                    Newsfeedpostbgtasks newsfeedpostbgtasks=new Newsfeedpostbgtasks(userId,NewsFeedAdd.this,convertImage,postText);
                    newsfeedpostbgtasks.execute();
                    finish();
//
//                    Intent intent=new Intent(NewsFeedAdd.this,NewsFeed.class);
//                    intent.putExtra("userId",userId);
//                    startActivity(intent);
                }

            }
        });

        uploadPostPic=findViewById(R.id.uploadPostPic);
        uploadPostPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });
    }
    //TODO read
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                uploadPostPic.setImageBitmap(bitmap);
                byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byteArrayVar=byteArrayOutputStream.toByteArray();
                convertImage= Base64.encodeToString(byteArrayVar,Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}