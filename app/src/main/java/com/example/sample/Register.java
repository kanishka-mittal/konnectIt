package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://konnectit-4ef05-default-rtdb.asia-southeast1.firebasedatabase.app/");
    EditText edtUsername, edtFirstName, edtEmail, edtPassword, edtConfPassword;
    Button btnSignUp;
    String username, fName, pswd, email, confpswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = findViewById(R.id.regUsername);
        edtFirstName = findViewById(R.id.regFirstName);
        edtEmail = findViewById(R.id.regEmail);
        edtPassword = findViewById(R.id.regPwd1);
        edtConfPassword = findViewById(R.id.regPwd2);
        btnSignUp = findViewById(R.id.btnSignUp);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading.....");

        if (MemoryData.getData(this).isEmpty()) {
            Intent intent = new Intent(Register.this, ChatLog.class);
            intent.putExtra("username", MemoryData.getData(this));
            startActivity(intent);
            //finish();

        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                username = edtUsername.getText().toString();
                fName = edtFirstName.getText().toString();
                pswd = edtPassword.getText().toString();
                email = edtEmail.getText().toString();
                confpswd = edtConfPassword.getText().toString();
                if (username.equals("") || fName.equals("") || pswd.equals("") || email.equals("") || confpswd.equals("")) {
                    Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (!pswd.equals(confpswd)) {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {



                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            databaseReference.child("users").child("username").setValue(username);
                            MemoryData.saveData(username, Register.this);
                            Intent intent = new Intent(Register.this, ChatLog.class);
                            intent.putExtra("username", username);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });

                    //Intent intent = new Intent(Register.this, ChatLog.class);
                    //intent.putExtra("username", username);
                    //startActivity(intent);

                    String method = "register";
                    BackgroundTask bgTask = new BackgroundTask(Register.this);
                    bgTask.execute(method, username, fName, email, pswd);
                    finish();

                }

                //Intent intent = new Intent(Register.this, Dashboard.class);
                //startActivity(intent);
            }
        });
    }

    public void openLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}