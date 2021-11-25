package com.example.sample.messages;

import com.example.sample.R;
import com.example.sample.chat.Chat;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdapter extends RecyclerView.Adapter<messagesAdapter.MyViewHolder >{

    private List<messagesList> messagelist;
    private final Context context;

    public messagesAdapter(List<messagesList> messagelist,Context context) {
        this.messagelist = messagelist;
        this.context=context;
    }

    @NonNull
    @Override
    public messagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout,null));

    }

    @Override
    public void onBindViewHolder(@NonNull messagesAdapter.MyViewHolder holder, int position) {

        messagesList list2=messagelist.get(position);

        if(!list2.getProfilePic().isEmpty())
        {
            Picasso.get().load(list2.getProfilePic()).into(holder.profilePic);
        }

        holder.lastMessage.setText(list2.getLastMessage());

        if(list2.getUnseenMessages()==0)
        {
            holder.unseenMessages.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#808080"));
              }
        else{
            holder.unseenMessages.setVisibility(View.VISIBLE);
            holder.unseenMessages.setText(list2.getUnseenMessages()+" ");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.white));

        }
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Chat.class);
                intent.putExtra("name",list2.getFname());
                intent.putExtra("profile_pic",list2.getProfilePic());
                intent.putExtra("chat_key",list2.getChatKey());
                context.startActivity(intent);

            }
        });
    }

    public void updateData(List<messagesList> messagelist)
    {
        this.messagelist=messagelist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView profilePic;
        private TextView name;
        private TextView lastMessage;
        private TextView unseenMessages;
        private LinearLayout rootLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic=itemView.findViewById(R.id.profilePicture);
            name=itemView.findViewById(R.id.name);
            lastMessage=itemView.findViewById(R.id.lastMessage);
            unseenMessages=itemView.findViewById(R.id.unseenMessages);
            rootLayout=itemView.findViewById(R.id.rootLayout);

        }
    }
}
