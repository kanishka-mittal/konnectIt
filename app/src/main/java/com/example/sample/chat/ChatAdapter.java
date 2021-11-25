package com.example.sample.chat;

import com.example.sample.MemoryData;
import com.example.sample.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatList> chatLists;
    private final Context context;
    private String userName;

    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
        this.userName= MemoryData.getData(context);
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        ChatList list2=chatLists.get(position);

        if(list2.getUsername().equals(userName))
        {
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list2.getMessage());
            holder.myTime.setText(list2.getDate()+" "+list2.getTime());
        }
        else
        {
            holder.oppoLayout.setVisibility(View.VISIBLE);
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoMessage.setText(list2.getMessage());
            holder.oppoTime.setText(list2.getDate()+" "+list2.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public void updateChatList(List<ChatList> chatLists)
    {
        this.chatLists=chatLists;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout oppoLayout,myLayout;
        private TextView oppoMessage,myMessage;
        private TextView oppoTime,myTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            oppoLayout=itemView.findViewById(R.id.oppoLayout);
            myLayout=itemView.findViewById(R.id.myLayout);
            oppoMessage=itemView.findViewById(R.id.oppoMessage);
            myMessage=itemView.findViewById(R.id.myMessage);
            oppoTime=itemView.findViewById(R.id.oppoMsgTime);
            myTime=itemView.findViewById(R.id.myMsgTime);
        }
    }
}
