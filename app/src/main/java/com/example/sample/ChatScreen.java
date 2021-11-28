package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatScreen extends AppCompatActivity {
    ListView userListView;
    ListAdapter arrayAdapter;
    ArrayList<ChatUser> users=new ArrayList<>();
    FirebaseAuth mauth;
    DatabaseReference databaseReference;
    Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        userListView=findViewById(R.id.userListView);
        mauth=FirebaseAuth.getInstance();
        signOut=findViewById(R.id.signOut);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        FirebaseUser mFirebaseUser = mauth.getCurrentUser();
                        if(mFirebaseUser!=null){
                            if(!dataSnapshot.child("email").getValue().toString().equals(mauth.getCurrentUser().getEmail())){
                                users.add(new ChatUser(dataSnapshot.child("username").getValue().toString(),dataSnapshot.child("email").getValue().toString()));
                            }
                        }else{
                            Toast.makeText(ChatScreen.this, "Kyaa karen", Toast.LENGTH_SHORT).show();
                        }
                        
                    }
                    arrayAdapter=new ListAdapter(ChatScreen.this, R.layout.chat_user_list_item,users);
                    userListView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatScreen.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                Intent intent=new Intent(ChatScreen.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ChatScreen.this,OnePersonChatActivity.class);
                intent.putExtra("email",users.get(i).getEmail());
                startActivity(intent);
            }
        });
    }
}