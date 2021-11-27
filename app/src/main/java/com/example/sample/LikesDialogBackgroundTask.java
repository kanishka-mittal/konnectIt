package com.example.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class LikesDialogBackgroundTask extends AsyncTask<String,LikeByModel,Void> {
    Dialog dialog;
    Context ctx;
    Activity activity;
    RecyclerView likesRecView;
    LikesRecViewAdapter likesRecViewAdapter;
    ArrayList<LikeByModel> likes;
    public LikesDialogBackgroundTask( Dialog dialog, Context ctx) {
        this.dialog = dialog;
        this.ctx = ctx;
        activity=(Activity) ctx;
    }

    @Override
    protected Void doInBackground(String... strings) {
        if(strings[0].equals("loadLikesPosts")){
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        likesRecView=dialog.findViewById(R.id.likesRecView);
                        likes=new ArrayList<>();
                        likesRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        likesRecViewAdapter=new LikesRecViewAdapter(likes,ctx);
                        likesRecView.setAdapter(likesRecViewAdapter);
                    }
                });
                URL url = new URL(activity.getString(R.string.likesLoadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
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
                    JSONArray jsonArray=jsonObject.getJSONArray("likes");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject likeObject=jsonArray.getJSONObject(count);
                        count++;
                        LikeByModel like=new LikeByModel(likeObject.getString("userName"),likeObject.getString("firstName"),likeObject.getString("imageurl"),likeObject.getInt("user_id"));
                        publishProgress(like);
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
        }else if(strings[0].equals("loadLikesComments")){
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        likesRecView=dialog.findViewById(R.id.likesRecView);
                        likes=new ArrayList<>();
                        likesRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        likesRecViewAdapter=new LikesRecViewAdapter(likes,ctx);
                        likesRecView.setAdapter(likesRecViewAdapter);
                    }
                });
                URL url = new URL(activity.getString(R.string.likesLoadCommentsUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("commentId", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
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
                    JSONArray jsonArray=jsonObject.getJSONArray("likes");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject likeObject=jsonArray.getJSONObject(count);
                        count++;
                        LikeByModel like=new LikeByModel(likeObject.getString("userName"),likeObject.getString("firstName"),likeObject.getString("imageurl"),likeObject.getInt("user_id"));
                        publishProgress(like);
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
        }else if(strings[0].equals("loadLikesReplies")){
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        likesRecView=dialog.findViewById(R.id.likesRecView);
                        likes=new ArrayList<>();
                        likesRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        likesRecViewAdapter=new LikesRecViewAdapter(likes,ctx);
                        likesRecView.setAdapter(likesRecViewAdapter);
                    }
                });
                URL url = new URL(activity.getString(R.string.likesLoadRepliesUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("replyId", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
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
                    JSONArray jsonArray=jsonObject.getJSONArray("likes");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject likeObject=jsonArray.getJSONObject(count);
                        count++;
                        LikeByModel like=new LikeByModel(likeObject.getString("userName"),likeObject.getString("firstName"),likeObject.getString("imageurl"),likeObject.getInt("user_id"));
                        publishProgress(like);
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
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(LikeByModel... values) {
        likes.add(values[0]);
        likesRecViewAdapter.notifyDataSetChanged();
    }
}
