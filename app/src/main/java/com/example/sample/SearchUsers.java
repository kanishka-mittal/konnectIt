package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchUsers extends AppCompatActivity {
    private ImageButton btnSearch;
    private String searchTxt;
    private EditText edtSearch;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchTxt = extras.getString("searchTxt");
            userId=extras.getInt("userId");
        }
        btnSearch=findViewById(R.id.btnSearch);
        edtSearch=findViewById(R.id.edtSearch);
        edtSearch.setText(searchTxt);
        SearchUsersBackgroundTask bgSearchTask=new SearchUsersBackgroundTask(this,userId,searchTxt);
        bgSearchTask.execute("load");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTxt=edtSearch.getText().toString();
                Intent intent = new Intent(SearchUsers.this, SearchUsers.class);
                intent.putExtra("searchTxt",searchTxt);
                intent.putExtra("userId",userId);
                startActivity(intent);
                finish();
            }
        });
    }
}