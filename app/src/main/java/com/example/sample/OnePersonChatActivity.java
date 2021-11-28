package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnePersonChatActivity extends AppCompatActivity {
    EditText message;
    Button send;
    ListView chatListView;
    MessageListAdapter arrayAdapter;
    String email;
//    ArrayAdapter arrayAdapter;
//    ArrayList<String> messages=new ArrayList<>();
    ArrayList<Message> messages=new ArrayList<>();
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_person_chat);
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);
        chatListView=findViewById(R.id.chatListView);
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
        String otherEmail=intent.getStringExtra("email");
        email=mAuth.getCurrentUser().getEmail();
        setTitle("Chat With "+otherEmail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> messageData=new HashMap<>();
                if(message.getText().toString().isEmpty()){
                    Toast.makeText(OnePersonChatActivity.this, "Put a message", Toast.LENGTH_SHORT).show();
                }else{
                    messageData.put("sender",email);
                    messageData.put("receiver",otherEmail);
                    messageData.put("message",message.getText().toString());
                    messageData.put("time",new SimpleDateFormat("yyyy/MM/dd HH.mm").format(new Date()));
                }
                databaseReference.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count;
                        if(snapshot.exists()){
                            count=(int)(snapshot.getChildrenCount())+1;
                        }else{
                            count=1;
                        }
                        databaseReference.child("chats").child(String.valueOf(count)).setValue(messageData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    message.setText("");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(OnePersonChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        databaseReference.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    messages.clear();
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                        Boolean x=dataSnapshot.child("receiver").getValue().toString().equals(otherEmail);
                        Boolean y=dataSnapshot.child("sender").getValue().toString().equals(email);
                        Boolean a=dataSnapshot.child("receiver").getValue().toString().equals(email);
                        Boolean b=dataSnapshot.child("sender").getValue().toString().equals(otherEmail);
                        if((x && y) || (a && b)){
                            String message=dataSnapshot.child("message").getValue().toString();
//                            messages.add(message);
                            messages.add(new Message(dataSnapshot.child("sender").getValue().toString(),dataSnapshot.child("message").getValue().toString(),dataSnapshot.child("time").getValue().toString()));
                        }
                    }
//                    arrayAdapter=new ArrayAdapter(OnePersonChatActivity.this, android.R.layout.simple_list_item_1,messages);
                    arrayAdapter=new MessageListAdapter(OnePersonChatActivity.this, R.layout.message_list_item,messages,email);
                    chatListView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}