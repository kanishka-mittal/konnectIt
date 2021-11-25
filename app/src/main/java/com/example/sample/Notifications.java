package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    private int userId;
    private ImageButton btnSearch;
    private String searchTxt;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }
        btnSearch=findViewById(R.id.btnSearch);
        edtSearch=findViewById(R.id.edtSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTxt=edtSearch.getText().toString();
                Intent intent = new Intent(Notifications.this,Search.class);
                intent.putExtra("searchTxt",searchTxt);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        NotifBackgroundTask bgTask=new NotifBackgroundTask(this,userId);
        bgTask.execute("load");
    }
}