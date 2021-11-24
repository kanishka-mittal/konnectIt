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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Comments_Recview_Adapter extends RecyclerView.Adapter<Comments_Recview_Adapter.ViewHolder> {

    private ArrayList<Comments> comments_list=new ArrayList<>();
    Context ctx;
    int postID;
    Activity activity;
    public Comments_Recview_Adapter(ArrayList<Comments> comments, Context ctx, int postId) {
        this.comments_list = comments;
        this.ctx = ctx;
        this.postID=postId;
        activity=(Activity) ctx;
    }

    @NonNull
    @Override
    public Comments_Recview_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Comments_Recview_Adapter.ViewHolder holder, int position) {
        holder.commentText.setText(comments_list.get(position).getcommentText());
        holder.userName.setText(comments_list.get(position).getUserName());
        int userId=comments_list.get(position).getUserId();
        Glide.with(ctx).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(userId)+".png").into(holder.profilepic);
    }

    @Override
    public int getItemCount() {
        return comments_list.size();
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments_list = comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,commentText;
        private RelativeLayout commentsListItemParent;
        private ImageView profilepic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userNameComment);
            commentText=itemView.findViewById(R.id.commentText);
            commentsListItemParent=itemView.findViewById(R.id.comments_listParent);
            profilepic=itemView.findViewById(R.id.profilepiccommemt);
        }
    }

}
