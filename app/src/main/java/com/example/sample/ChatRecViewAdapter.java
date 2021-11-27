package com.example.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRecViewAdapter extends RecyclerView.Adapter<ChatRecViewAdapter.ViewHolder> {
    Context ctx;
    int userId;
    int friendId;
    ArrayList<Message> messages;

    public ChatRecViewAdapter(Context ctx, int userId, int friendId, ArrayList<Message> messages) {
        this.ctx = ctx;
        this.userId = userId;
        this.friendId = friendId;
        this.messages = messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.chat_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecViewAdapter.ViewHolder holder, int position) {
        Message message=messages.get(position);
        if(message.senderId==friendId){
            holder.sendMessageCard.setVisibility(View.GONE);
            holder.recMessageCard.setVisibility(View.VISIBLE);
            holder.msgTxt1.setText(message.getMessageTxt());
            holder.time1.setText(message.getTime());
        }else{
            holder.sendMessageCard.setVisibility(View.VISIBLE);
            holder.recMessageCard.setVisibility(View.GONE);
            holder.msgTxt2.setText(message.getMessageTxt());
            holder.time2.setText(message.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView sendMessageCard,recMessageCard;
        TextView msgTxt1,msgTxt2,time1,time2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sendMessageCard=itemView.findViewById(R.id.sendMessageCard);
            recMessageCard=itemView.findViewById(R.id.recMessageCard);
            msgTxt1=itemView.findViewById(R.id.msgTxt1);
            msgTxt2=itemView.findViewById(R.id.msgTxt2);
            time1=itemView.findViewById(R.id.time1);
            time2=itemView.findViewById(R.id.time2);
        }
    }
}
