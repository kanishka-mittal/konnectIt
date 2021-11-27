package com.example.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostRecViewAdapter extends RecyclerView.Adapter<PostRecViewAdapter.ViewHolder> {
    ArrayList<PostModel> posts;
    Context ctx;
    Activity activity;
    int userId;

    public PostRecViewAdapter(ArrayList<PostModel> posts, Context ctx, int userId) {
        this.posts = posts;
        this.ctx = ctx;
        this.userId = userId;
        activity=(Activity) ctx;
    }

    public void setPosts(ArrayList<PostModel> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecViewAdapter.ViewHolder holder, int position) {
        PostModel post=posts.get(position);
        holder.firstName.setText(post.getFirstName());
        holder.userName.setText(post.getUserName());
        holder.numLikes.setText(Integer.toString(post.getNumLikes()));
        holder.numComments.setText(Integer.toString(post.getNumComments()));
        if(!(post.getImageUrl().equals("null"))){
            Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(posts.get(position).getUserId())+".png").into(holder.profilepic);
        }
        //System.out.println(post.getPostImageURL());
        if((post.getPostImageURL()).equals("null")){
            holder.postImage.setVisibility(View.GONE);
        }else{
            holder.postImage.setVisibility(View.VISIBLE);
            //System.out.println(post.getPostImageURL());
            Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/posts_image/"+Integer.toString(post.getPostId())+".png").into(holder.postImage);
        }
        holder.postText.setText(post.getPostText());
        NewsFeedBackgroundTask bgTask=new NewsFeedBackgroundTask(ctx,userId,holder);
        bgTask.execute("isLiked",Integer.toString(post.getPostId()),Integer.toString(post.getUserId()));
        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsFeedBackgroundTask bgTask=new NewsFeedBackgroundTask(ctx,userId);
                bgTask.execute("like",Integer.toString(post.getPostId()),Integer.toString(post.getUserId()));
                holder.dislike.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
                holder.numLikes.setText(Integer.toString(post.getNumLikes()+1));
                post.setNumLikes(post.getNumLikes()+1);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsFeedBackgroundTask bgTask=new NewsFeedBackgroundTask(ctx,userId);
                bgTask.execute("unlike",Integer.toString(post.getPostId()),Integer.toString(post.getUserId()));
                holder.dislike.setVisibility(View.VISIBLE);
                holder.like.setVisibility(View.GONE);
                holder.numLikes.setText(Integer.toString(post.getNumLikes()-1));
                post.setNumLikes(post.getNumLikes()-1);
            }
        });
        holder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx,Post.class);
                intent.putExtra("userId",userId);
                intent.putExtra("postId",post.getPostId());
                activity.startActivity(intent);
            }
        });
        holder.postText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx,Post.class);
                intent.putExtra("userId",userId);
                intent.putExtra("postId",post.getPostId());
                activity.startActivity(intent);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.dialog_layout);
                DialogBackgroundTask bgTask=new DialogBackgroundTask(dialog,ctx,userId,post.getPostId());
                bgTask.execute("load");
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,firstName,numLikes,numComments,postText;
        private RelativeLayout postListItemParent;
        ImageView like,dislike,postImage,profilepic,share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            firstName=itemView.findViewById(R.id.firstName);
            postListItemParent=itemView.findViewById(R.id.postListItemParent);
            like=itemView.findViewById(R.id.like);
            dislike=itemView.findViewById(R.id.dislike);
            postImage=itemView.findViewById(R.id.postImage);
            numComments=itemView.findViewById(R.id.numComments);
            numLikes=itemView.findViewById(R.id.numLikes);
            profilepic=itemView.findViewById(R.id.profilepic);
            postText=itemView.findViewById(R.id.postText);
            share=itemView.findViewById(R.id.share);
        }
    }
}
