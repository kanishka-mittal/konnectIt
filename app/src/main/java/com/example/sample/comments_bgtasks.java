package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class comments_bgtasks extends AsyncTask<String,Replies,String> {
    Context ctx;
    Activity activity;
    RecyclerView repliesRecView;
    Replies_recview_Adapter RepliesRecViewAdapter;
    ArrayList<Replies> replies;
    int commentID;
    int userID;
    String replyText,method;
    Replies_recview_Adapter.ViewHolder holder;

    public comments_bgtasks(Context ctx, Replies_recview_Adapter repliesRecViewAdapter, ArrayList<Replies> replies, int commentID, int userID) {
        this.ctx = ctx;
        RepliesRecViewAdapter = repliesRecViewAdapter;
        this.replies = replies;
        this.commentID = commentID;
        this.userID = userID;
    }

    public comments_bgtasks(Context ctx, int userID, Replies_recview_Adapter.ViewHolder holder) {
        this.ctx = ctx;
        this.userID = userID;
        this.holder = holder;
        activity=(Activity) ctx;
    }

    public comments_bgtasks(Context ctx, int commentID, int userID) {
        this.ctx = ctx;
        this.commentID=commentID;
        activity=(Activity) ctx;
        this.userID=userID;
    }

    public comments_bgtasks(Context ctx,int commentID,int userID,String replyText) {
        this.ctx = ctx;
        this.commentID=commentID;
        activity=(Activity) ctx;
        this.userID=userID;
        this.replyText=replyText;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Doubt

    }


    @Override
    protected String doInBackground(String... params) {
        method=params[0];
        if(params[0].equals("replyload")){
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        repliesRecView=activity.findViewById(R.id.recyclerViewCommentpage);
                        replies=new ArrayList<>();
                        repliesRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        RepliesRecViewAdapter=new Replies_recview_Adapter(replies,ctx,commentID,userID);
                        repliesRecView.setAdapter(RepliesRecViewAdapter);
                    }
                });

                URL url = new URL(activity.getString(R.string.replyloadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("commentId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(commentID), "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                String jsonString=stringBuilder.toString().trim();
                System.out.println(jsonString);
                if(!jsonString.equals("")){
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("replies");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject replyObject=jsonArray.getJSONObject(count);
                        count++;
                        Replies reply=new Replies(replyObject.getString("userName"),replyObject.getString("replyText"),replyObject.getInt("user_id"),replyObject.getInt("replyId"),commentID,replyObject.getInt("numLikes"));
                        publishProgress(reply);
                    }
                }

                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(params[0].equals("addreply")){

            StringBuilder stringBuilder=new StringBuilder();

            try{
//                repliesRecView=activity.findViewById(R.id.recyclerViewCommentpage);
//                replies=new ArrayList<>();
//                repliesRecView.setLayoutManager(new LinearLayoutManager(ctx));
//                RepliesRecViewAdapter=new Replies_recview_Adapter(replies,ctx,commentID,userID);
//                repliesRecView.setAdapter(RepliesRecViewAdapter);
                URL url=new URL(activity.getString(R.string.addReplyUrl));
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader;
                BufferedWriter bufferedWriter;
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                bufferedWriter=new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
                System.out.println(userID);
                String data= URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8") + "&" + URLEncoder.encode("replyText", "UTF-8") + "=" + URLEncoder.encode(replyText, "UTF-8") + "&" + URLEncoder.encode("commentId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(commentID), "UTF-8");
                //Doubt...why?
                System.out.println(commentID);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
                stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                String jsonString=stringBuilder.toString().trim();
                System.out.println(jsonString);
                bufferedReader.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(params[0].equals("isReplyLiked")){
            try {
                URL url = new URL(activity.getString(R.string.isReplyLikedUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8") + "&" + URLEncoder.encode("replyId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                String jsonString=stringBuilder.toString().trim();
//                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//                System.out.println(jsonString);
//                System.out.println("**************************************");
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return jsonString;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(params[0].equals("likeReply")){
            try {
                URL url = new URL(activity.getString(R.string.likeReplyUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8") + "&" + URLEncoder.encode("replyId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                String jsonString=stringBuilder.toString().trim();
                System.out.println(jsonString);
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(params[0].equals("unlikeReply")){
            try {
                URL url = new URL(activity.getString(R.string.unlikeReplyUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8") + "&" + URLEncoder.encode("replyId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                String jsonString=stringBuilder.toString().trim();
                System.out.println(jsonString);
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Replies... values) {
        replies.add(values[0]);
        RepliesRecViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(String s) {
        if(method.equals("isReplyLiked")){
            if(s.equals("true")){
                holder.dislike.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
            }else{
                holder.like.setVisibility(View.GONE);
                holder.dislike.setVisibility(View.VISIBLE);
            }
        }
    }
}
