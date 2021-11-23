package com.example.sample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class contactsAdapter extends RecyclerView.Adapter<contactsAdapter.contactsViewHolder> {
    private static final String tag="Number";
    private Context ctx;
    private List<contacts> contactList;


    public contactsAdapter(Context ctx, List<contacts> contactList) {
        this.ctx = ctx;
        this.contactList = contactList;
    }


    @Override
    public contactsAdapter.contactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.contact_list, parent,false);
        return new contactsAdapter.contactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(contactsViewHolder holder, int position) {
        contacts ct = contactList.get(position);
        Glide.with(ctx).load(ct.getImage()).into(holder.imageView);
        holder.uname.setText(String.valueOf(ct.getUsername()));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    class contactsViewHolder extends RecyclerView.ViewHolder {
        TextView uname;
        ImageView imageView;

        public contactsViewHolder(View itemView) {
            super(itemView);
            uname = itemView.findViewById(R.id.uname);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
