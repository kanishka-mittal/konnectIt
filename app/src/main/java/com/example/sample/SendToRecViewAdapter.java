package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SendToRecViewAdapter extends RecyclerView.Adapter<SendToRecViewAdapter.ViewHolder> {

    private ArrayList<Friend> friends=new ArrayList<>();
    Context ctx;
    int userId,postId;
    Activity activity;
    public SendToRecViewAdapter(ArrayList<Friend> friends, Context ctx,int userId,int postId) {
        this.friends = friends;
        this.ctx = ctx;
        this.userId=userId;
        activity=(Activity) ctx;
        this.postId=postId;
    }

    @NonNull
    @Override
    public SendToRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.send_to_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendToRecViewAdapter.ViewHolder holder, int position) {
        holder.firstName.setText(friends.get(position).getFirstName());
        holder.userName.setText(friends.get(position).getUserName());
        int friendId=friends.get(position).getUserId();
        if(!(friends.get(position).getImageUrl().equals("null"))){
            Glide.with(ctx).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(friendId)+".png").into(holder.profilepic);
        }
        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBackgroundTask bgTask=new DialogBackgroundTask(null,ctx,userId,postId);
                bgTask.execute("send",Integer.toString(friendId));
                Toast.makeText(ctx, "Post Shared", Toast.LENGTH_SHORT).show();
            }
        });
        holder.profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx,Profile.class);
                intent.putExtra("userId",friendId);
                intent.putExtra("accessedByUser",userId);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,firstName;
        private RelativeLayout friendListItemParent;
        private Button btnSend;
        private ImageView profilepic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            firstName=itemView.findViewById(R.id.firstName);
            friendListItemParent=itemView.findViewById(R.id.friendListItemParent);
            btnSend=itemView.findViewById(R.id.btnSend);
            profilepic=itemView.findViewById(R.id.profilepic);
        }
    }

}
