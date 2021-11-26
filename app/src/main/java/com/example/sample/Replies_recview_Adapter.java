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

public class Replies_recview_Adapter extends RecyclerView.Adapter<Replies_recview_Adapter.ViewHolder> {

    private ArrayList<Replies> replies_list=new ArrayList<>();
    Context ctx;
    int commentID,userID;
    Activity activity;
    public Replies_recview_Adapter(ArrayList<Replies> replies, Context ctx, int commentID,int userId) {
        this.replies_list = replies;
        this.ctx = ctx;
        this.commentID=commentID;
        this.userID=userId;
        activity=(Activity) ctx;
    }

    @NonNull
    @Override
    public Replies_recview_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.replies_list,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Replies_recview_Adapter.ViewHolder holder, int position) {
        holder.replyText.setText(replies_list.get(position).getReplyText());
        int replyId=replies_list.get(position).getReplyId();
        holder.userName.setText(replies_list.get(position).getUserName());
        int replyuserId=replies_list.get(position).getUserId();
        Glide.with(ctx).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(replyuserId)+".png").into(holder.profilepic);

        if (userID==replyuserId){
            holder.dustbinreply.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return replies_list.size();
    }

    public void setReplies(ArrayList<Replies> replies) {
        this.replies_list = replies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,replyText;
        private RelativeLayout repliesListItemParent;
        private ImageView profilepic,dustbinreply;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userNameReply);
            replyText=itemView.findViewById(R.id.replyText);
            repliesListItemParent=itemView.findViewById(R.id.replies_listParent);
            profilepic=itemView.findViewById(R.id.profilepicreply);
            dustbinreply=itemView.findViewById(R.id.dustbinreply);
        }
    }

}
