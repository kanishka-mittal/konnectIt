package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendRecViewAdapter extends RecyclerView.Adapter<FriendRecViewAdapter.ViewHolder> {

    private ArrayList<Friend> friends=new ArrayList<>();
    Context ctx;
    int userId;
    Activity activity;
    public FriendRecViewAdapter(ArrayList<Friend> friends, Context ctx,int userId) {
        this.friends = friends;
        this.ctx = ctx;
        this.userId=userId;
        activity=(Activity) ctx;
    }

    @NonNull
    @Override
    public FriendRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRecViewAdapter.ViewHolder holder, int position) {
        holder.firstName.setText(friends.get(position).getFirstName());
        holder.userName.setText(friends.get(position).getUserName());
        int friendId=friends.get(position).getUserId();
        holder.remBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendBackgroundTask bgTask=new FriendBackgroundTask(ctx,userId);
                bgTask.execute("remFriend",Integer.toString(friendId));
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
        private Button remBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            firstName=itemView.findViewById(R.id.firstName);
            friendListItemParent=itemView.findViewById(R.id.friendListItemParent);
            remBtn=itemView.findViewById(R.id.remBtn);
        }
    }

}
