package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchUsersRecViewAdapter extends RecyclerView.Adapter<SearchUsersRecViewAdapter.ViewHolder>{
    private ArrayList<User> users=new ArrayList<>();
    Context ctx;
    int userId;
    Activity activity;

    public SearchUsersRecViewAdapter(ArrayList<User> users, Context ctx, int userId) {
        this.users = users;
        this.ctx = ctx;
        this.userId = userId;
        activity=(Activity) ctx;
    }

    @NonNull
    @Override
    public SearchUsersRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
        SearchUsersRecViewAdapter.ViewHolder holder=new SearchUsersRecViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUsersRecViewAdapter.ViewHolder holder, int position) {
        holder.firstName.setText(users.get(position).getFirstName());
        holder.userName.setText(users.get(position).getUserName());
        int otherUserId=users.get(position).getUserId();
        if(!(users.get(position).getImageUrl().equals("null"))){
            Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(users.get(position).getUserId())+".png").into(holder.profilepic);
        }
        holder.profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Send friend request to be able to see profile", Toast.LENGTH_LONG).show();
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchUsersBackgroundTask bgSearchTask=new SearchUsersBackgroundTask(ctx,userId,"");
                bgSearchTask.execute("addFriend",Integer.toString(otherUserId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,firstName;
        private RelativeLayout userListItemParent;
        private Button addBtn;
        private ImageView profilepic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            firstName=itemView.findViewById(R.id.firstName);
            userListItemParent=itemView.findViewById(R.id.userListItemParent);
            addBtn=itemView.findViewById(R.id.addBtn);
            profilepic=itemView.findViewById(R.id.profilepic);
        }
    }

}
