package com.example.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageListAdapter extends ArrayAdapter<Message> {
    private int resourceLayout;
    private Context mContext;
    public MessageListAdapter(Context context, int resource, List<Message> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
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
//        if (chatUser != null) {
//            TextView tt1 = (TextView) v.findViewById(R.id.userName);
//            TextView tt2 = (TextView) v.findViewById(R.id.email);
//            if (tt1 != null) {
//                tt1.setText(chatUser.getUserName());
//            }
//            if (tt2 != null) {
//                tt2.setText(chatUser.getEmail());
//            }
//        }
        return v;
    }
}
