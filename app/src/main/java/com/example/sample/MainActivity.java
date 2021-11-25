package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private String username;
    EditText edtUsername,edtPassword;
    String loginUsername,loginPassword;
    private Button SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=new Intent();
        username=i.getStringExtra("username");
        edtUsername=findViewById(R.id.loginUsername);
        edtPassword=findViewById(R.id.loginPassword);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsername=edtUsername.getText().toString();
                loginPassword=edtPassword.getText().toString();
                if(loginUsername.equals("")||loginPassword.equals("")){
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    String method = "login";
                    BackgroundTask bgTask = new BackgroundTask(MainActivity.this);
                    bgTask.execute(method, loginUsername, loginPassword);
                }
            }
        });
    }
    public void openRegister(View view){
        Intent intent = new Intent(this,Register.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}