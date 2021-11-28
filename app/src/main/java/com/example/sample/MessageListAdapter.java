package com.example.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

import java.util.List;

public class MessageListAdapter extends ArrayAdapter<Message> {
    private int resourceLayout;
    private Context mContext;
    String email;
    public MessageListAdapter(Context context, int resource, List<Message> items,String email) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.email=email;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        Message message = getItem(position);
        if (message != null) {
            CardView recCard=v.findViewById(R.id.recCard);
            CardView sendCard=v.findViewById(R.id.sendCard);
            TextView recMsg=v.findViewById(R.id.recMsg);
            TextView sentMsg=v.findViewById(R.id.sentMsg);
            TextView sendTime=v.findViewById(R.id.sendTime);
            TextView recTime=v.findViewById(R.id.recTime);
            if(message.getSenderEmail().equals(email)){
                recCard.setVisibility(View.GONE);
                sendCard.setVisibility(View.VISIBLE);
                sentMsg.setText(message.getMsgTxt());
                sendTime.setText(message.getTimestamp());
            }else{
                recCard.setVisibility(View.VISIBLE);
                sendCard.setVisibility(View.GONE);
                recMsg.setText(message.getMsgTxt());
                recTime.setText(message.getTimestamp());
            }


        }
        return v;
    }
}
