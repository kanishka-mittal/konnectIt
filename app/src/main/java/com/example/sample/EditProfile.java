package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfile extends AppCompatActivity {
    private MaterialButton btnSubmit;
    private int userId;
    EditText lastName,Bio,Gender,mobNum,Age;
    String lastname,bio,gender,mobnum,age;
    ImageView uploadProfilePic;
    private Bitmap bitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArrayVar;
    String convertImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
            System.out.println(userId);
        }
        Gender=findViewById(R.id.edtGender1);
        mobNum=findViewById(R.id.edtMobile1);
        Bio=findViewById(R.id.edtBio1);
        lastName=findViewById(R.id.edtLastName1);
        Age=findViewById(R.id.edtAge1);

        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender=Gender.getText().toString();
                mobnum=mobNum.getText().toString();
                bio=Bio.getText().toString();
                lastname=lastName.getText().toString();
                age=Age.getText().toString();

                if(lastname.equals("")&&age.equals("")&&gender.equals("")&&mobnum.equals("")&&bio.equals("")){
                    Toast.makeText(EditProfile.this, "Please fill atleast one field!", Toast.LENGTH_SHORT).show();
                }
                else if(!gender.equals("Male")&&!gender.equals("Female")&&!gender.equals("Others")&&!gender.equals("")){
                    Toast.makeText(EditProfile.this, "Gender format : Male,Female,Others", Toast.LENGTH_SHORT).show();
                }
                else{
                    EditProfileBackgroundTask editProfileBackgroundTask=new EditProfileBackgroundTask(userId,EditProfile.this,convertImage,gender,mobnum,bio, lastname,age);
                    editProfileBackgroundTask.execute();
                    Intent intent=new Intent(EditProfile.this,NewsFeed.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                    finish();
                }
            }
        });
        uploadProfilePic=findViewById(R.id.uploadProfilePic);
        uploadProfilePic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });
    }

    public void nextScreen(View view){
        finish();
        Intent intent=new Intent(EditProfile.this,Notifications.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
    //TODO read
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                uploadProfilePic.setImageBitmap(bitmap);
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