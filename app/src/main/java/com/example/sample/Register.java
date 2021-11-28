package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText edtUsername,edtFirstName,edtEmail,edtPassword,edtConfPassword;
    Button btnSignUp;
    String username,fName,pswd,email,confpswd;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        System.out.println(emailStr);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
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
                }
                else if(!pswd.equals(confpswd)){
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if(pswd.length()<6){
                    Toast.makeText(Register.this, "Password should be greater than or equal to 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!validate(email)){
                    Toast.makeText(Register.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                } else{
                    String method="register";
                    BackgroundTask bgTask=new BackgroundTask(Register.this);
                    bgTask.execute(method,username,fName,email,pswd);
                }
            }
        });
    }
    public void openLogin(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}