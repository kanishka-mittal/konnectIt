package com.example.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ChatUser> {

    private int resourceLayout;
    private Context mContext;
    public ListAdapter(Context context, int resource, List<ChatUser> items) {
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
        ChatUser chatUser = getItem(position);
        if (chatUser != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.userName);
            TextView tt2 = (TextView) v.findViewById(R.id.firstName);
            if (tt1 != null) {
                tt1.setText(chatUser.getUserName());
            }
            if (tt2 != null) {
                tt2.setText(chatUser.getFirstName());
            }
        }
        return v;
    }

}
