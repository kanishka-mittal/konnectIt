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

public class NotifRecViewAdapter extends RecyclerView.Adapter<NotifRecViewAdapter.ViewHolder>{
    private ArrayList<Notification> notifications=new ArrayList<>();
    private Context context;
    private int userId;
    Activity activity;
    public NotifRecViewAdapter(ArrayList<Notification> notifications, Context context,int userId) {
        this.notifications = notifications;
        this.context = context;
        this.userId=userId;
        activity=(Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notifs_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification=notifications.get(position);
        int id=notifications.get(position).getId();
        holder.notification.setText(notifications.get(position).getNotif());
        holder.userName.setText(notifications.get(position).getSendUser());
        if(!(notifications.get(position).getImageUrl().equals("null"))){
            Glide.with(context).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(notifications.get(position).getSendUserId())+".png").into(holder.profilepic);
        }
        holder.notifLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.beginDelayedTransition(holder.cardLinearLayout,new AutoTransition());
                if(holder.profilepic.getVisibility()==View.GONE){
                    holder.profilepic.setVisibility(View.VISIBLE);
                    holder.dustbin.setVisibility(View.GONE);
                    holder.userName.setTextColor(((Activity)context).getResources().getColor(R.color.yellow));
                }else{
                    holder.profilepic.setVisibility(View.GONE);
                    holder.dustbin.setVisibility(View.VISIBLE);
                    holder.userName.setTextColor(((Activity)context).getResources().getColor(R.color.red));
                }
            }
        });
        holder.dustbin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotifBackgroundTask bgTask=new NotifBackgroundTask(context,userId);
                bgTask.execute("remove",Integer.toString(id));
                notifications.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.btnSeePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notification.getNotifType()>0){
                    Intent intent=new Intent(context,Post.class);
                    intent.putExtra("postId",notification.getPostId());
                    intent.putExtra("userId",userId);
                    activity.startActivity(intent);
                }else{
                    SeePostBackgroundTask seePostBackgroundTask =new SeePostBackgroundTask(userId,notification.getPostId(),context);
                    seePostBackgroundTask.execute("privacy");
                    //check private or public account
                    //check friends or not
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout notifListItemParent;
        private ImageView profilepic;
        private ImageView dustbin;
        private LinearLayout notifLinearLayout;
        private TextView userName;
        private TextView notification;
        private LinearLayout cardLinearLayout;
        private Button btnSeePost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification=itemView.findViewById(R.id.notification);
            notifListItemParent=itemView.findViewById(R.id.notifListItemParent);
            profilepic=itemView.findViewById(R.id.profilepic);
            dustbin=itemView.findViewById(R.id.dustbin);
            cardLinearLayout=itemView.findViewById(R.id.cardLinearLayout);
            userName=itemView.findViewById(R.id.userName);
            notifLinearLayout=itemView.findViewById(R.id.notifLinearLayout);
            btnSeePost=itemView.findViewById(R.id.btnSeePost);
        }
    }
}
