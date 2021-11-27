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
    private int postID,userID;
    Activity activity;

    public Comments_Recview_Adapter(ArrayList<Comments> comments, Context ctx, int postId, int userId) {
        this.userID=userId;
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
        Comments comments=comments_list.get(position);
        int commentID=comments_list.get(position).getcommentId();
//        String commentText=comments_list.get(position).getcommentText();
//        String username=comments_list.get(position).getUserName();
        int commentuserId=comments_list.get(position).getUserId();

        holder.commentText.setText(comments_list.get(position).getcommentText());
        holder.userName.setText(comments_list.get(position).getUserName());
        Glide.with(ctx).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(commentuserId)+".png").into(holder.profilepic);

        if (commentuserId==userID){
            holder.dustbin.setVisibility(View.VISIBLE);
        }

        holder.commentText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,comment_reply.class);
                intent.putExtra("commentId",commentID);
                intent.putExtra("userId",userID);
//                intent.putExtra("commentuserId",commentuserId);
//                intent.putExtra("commentText",commentText);
//                intent.putExtra("username",username);
                activity.startActivity(intent);
            }

        });

        holder.dustbin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    DeleteCommentBgTask deleteCommentBgTask=new DeleteCommentBgTask(ctx,commentID);
                    deleteCommentBgTask.execute();
                    comments_list.remove(comments);
                    notifyDataSetChanged();
//                Intent intent=new Intent(activity,comment_reply.class);
//                intent.putExtra("commentId",commentID);
//                intent.putExtra("userId",userID);
////                intent.putExtra("commentuserId",commentuserId);
////                intent.putExtra("commentText",commentText);
////                intent.putExtra("username",username);
//                activity.startActivity(intent);
            }

        });
        Post_bgTasks bgTask=new Post_bgTasks(ctx,userID,holder);
        bgTask.execute("isCommentLiked",Integer.toString(commentID));
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post_bgTasks post_bgTasks =new Post_bgTasks(ctx,userID,holder);
                post_bgTasks.execute("unlikeComment",Integer.toString(commentID));
                holder.dislike.setVisibility(View.VISIBLE);
                holder.like.setVisibility(View.GONE);
//                holder.numLikes.setText(Integer.toString(post.getNumLikes()-1));
//                post.setNumLikes(post.getNumLikes()-1);
            }
        });
        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("%%%%%%%%%%%%%%%%%%");
                Post_bgTasks post_bgTasks =new Post_bgTasks(ctx,userID,holder);
                post_bgTasks.execute("likeComment",Integer.toString(commentID));
                holder.dislike.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
//                holder.numLikes.setText(Integer.toString(post.getNumLikes()+1));
//                post.setNumLikes(post.getNumLikes()+1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments_list.size();
    }
    public void addItem(){

    }
    public void setComments(ArrayList<Comments> comments) {
        this.comments_list = comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,commentText,numLikes;
        private RelativeLayout commentsListItemParent;
        private ImageView profilepic,dustbin;
        ImageView like,dislike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userNameComment);
            commentText=itemView.findViewById(R.id.commentText);
            commentsListItemParent=itemView.findViewById(R.id.comments_listParent);
            profilepic=itemView.findViewById(R.id.profilepiccomment);
            dustbin=itemView.findViewById(R.id.dustbin);
            like=itemView.findViewById(R.id.like);
            dislike=itemView.findViewById(R.id.dislike);
            numLikes=itemView.findViewById(R.id.numLikes);
        }
    }

}
