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

public class LikesRecViewAdapter extends RecyclerView.Adapter<LikesRecViewAdapter.ViewHolder> {

    private ArrayList<LikeByModel> likes=new ArrayList<>();
    Context ctx;
    Activity activity;
    public LikesRecViewAdapter(ArrayList<LikeByModel> likes, Context ctx) {
        this.likes = likes;
        this.ctx = ctx;
        activity=(Activity) ctx;
    }

    @NonNull
    @Override
    public LikesRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.likedby_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikesRecViewAdapter.ViewHolder holder, int position) {
        holder.firstName.setText(likes.get(position).getFirstName());
        holder.userName.setText(likes.get(position).getUserName());
        if(!(likes.get(position).getImageurl().equals("null"))){
            Glide.with(ctx).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(likes.get(position).getUserId())+".png").into(holder.profilepic);
        }
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }

    public void setlikes(ArrayList<LikeByModel> likes) {
        this.likes = likes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,firstName;
        private ImageView profilepic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            firstName=itemView.findViewById(R.id.firstName);
            profilepic=itemView.findViewById(R.id.profilepic);
        }
    }

}
