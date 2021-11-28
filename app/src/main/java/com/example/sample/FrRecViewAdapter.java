package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FrRecViewAdapter extends RecyclerView.Adapter<FrRecViewAdapter.ViewHolder> {
    ArrayList<FrModel> friendRequests;
    Context ctx;
    int userId;

    public FrRecViewAdapter(ArrayList<FrModel> friendRequests, Context ctx, int userId) {
        this.friendRequests = friendRequests;
        this.ctx = ctx;
        this.userId = userId;
    }

    public void setFriendRequests(ArrayList<FrModel> friendRequests) {
        this.friendRequests = friendRequests;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FrRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.fr_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FrRecViewAdapter.ViewHolder holder, int position) {
        FrModel frModel=friendRequests.get(position);
        System.out.println(frModel.getFirstName());
        holder.userName.setText(frModel.getUserName());
        holder.firstName.setText(frModel.getFirstName());
        if(!(frModel.getImageURL().equals("null"))){
            Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(frModel.getUserId())+".png").into(holder.profilePic);
        }
        holder.frLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.accept.getVisibility()==View.VISIBLE){
                    holder.accept.setVisibility(View.GONE);
                    holder.dustbin.setVisibility(View.VISIBLE);
                    holder.userName.setTextColor(((Activity)ctx).getResources().getColor(R.color.red));
                }else{
                    holder.accept.setVisibility(View.VISIBLE);
                    holder.dustbin.setVisibility(View.GONE);
                    holder.userName.setTextColor(((Activity)ctx).getResources().getColor(R.color.yellow));
                }
            }
        });
        holder.dustbin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FrBackgroundTask bgTask=new FrBackgroundTask(ctx,userId);
            bgTask.execute("delete",Integer.toString(frModel.getUserId()));
                //System.out.println(frModel.getUserId());
                friendRequests.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrBackgroundTask bgTask=new FrBackgroundTask(ctx,userId);
                bgTask.execute("accept",Integer.toString(frModel.getUserId()));
                //System.out.println(frModel.getUserId());
                friendRequests.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName,firstName;
        ImageView accept,dustbin,profilePic;
        LinearLayout frLinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.username);
            firstName=itemView.findViewById(R.id.firstName);
            accept=itemView.findViewById(R.id.accept);
            dustbin=itemView.findViewById(R.id.dustbin);
            profilePic=itemView.findViewById(R.id.profilepic);
            frLinearLayout=itemView.findViewById(R.id.frLinearLayout);
        }
    }
}
