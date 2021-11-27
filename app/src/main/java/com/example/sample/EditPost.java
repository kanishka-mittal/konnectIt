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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditPost extends AppCompatActivity {
    int postId;
    EditText edtpostText;
    Button btnDone;
    String postText;
    ImageView edtPostPic;
    private Bitmap bitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArrayVar;
    String convertImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt("postId");
        }
        EditPostLoadBgTask editPostLoadBgTask=new EditPostLoadBgTask(postId,this);
        editPostLoadBgTask.execute();
        edtpostText=findViewById(R.id.edtPostText);
        btnDone=findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postText=edtpostText.getText().toString();
                if(postText.equals("")){
                    Toast.makeText(EditPost.this, "Please fill text field", Toast.LENGTH_SHORT).show();
                }
                else{
                    EditPostDoneBgTask editPostDoneBgTask=new EditPostDoneBgTask(postId,postText,convertImage,EditPost.this);
                    editPostDoneBgTask.execute();
                    finish();
//                    Intent intent=new Intent(NewsFeedAdd.this,NewsFeed.class);
//                    intent.putExtra("userId",userId);
//                    startActivity(intent);
                }
            }
        });
        edtPostPic=findViewById(R.id.edtPostPic);
        edtPostPic.setOnClickListener(new View.OnClickListener() {
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
                edtPostPic.setImageBitmap(bitmap);
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







