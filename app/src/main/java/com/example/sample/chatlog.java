package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class chatlog extends AppCompatActivity {
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlog);
    }

    public void openContacts(View view){
        Intent intent = new Intent(this,ActivityContacts.class);
        startActivity(intent);
    }


}
