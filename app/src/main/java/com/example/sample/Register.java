package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText edtUsername,edtFirstName,edtEmail,edtPassword,edtConfPassword;
    Button btnSignUp;
    String username,fName,pswd,email,confpswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername=findViewById(R.id.regUsername);
        edtFirstName=findViewById(R.id.regFirstName);
        edtEmail=findViewById(R.id.regEmail);
        edtPassword=findViewById(R.id.regPwd1);
        edtConfPassword=findViewById(R.id.regPwd2);
        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=edtUsername.getText().toString();
                fName=edtFirstName.getText().toString();
                pswd=edtPassword.getText().toString();
                email=edtEmail.getText().toString();
                confpswd=edtConfPassword.getText().toString();
                if(username.equals("")||fName.equals("")||pswd.equals("")||email.equals("")||confpswd.equals("")){
                    Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else if(!pswd.equals(confpswd)){
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }else{
                    String method="register";
                    BackgroundTask bgTask=new BackgroundTask(Register.this);
                    bgTask.execute(method,username,fName,email,pswd);
//                    finish();
//                    Intent intent = new Intent(Register.this,MainActivity.class);
//                    startActivity(intent);
                }

//                Intent intent = new Intent(Register.this,Dashboard.class);
//                startActivity(intent);
            }
        });
    }
    public void openLogin(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}