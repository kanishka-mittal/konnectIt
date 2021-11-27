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

public class DialogBackgroundTask extends AsyncTask<String,Friend,Void> {
    int userId,postId;
    Dialog dialog;
    Context ctx;
    Activity activity;
    RecyclerView sendToRecView;
    SendToRecViewAdapter sendToRecViewAdapter;
    ArrayList<Friend> friends;
    public DialogBackgroundTask( Dialog dialog, Context ctx,int userId,int postId) {
        this.userId = userId;
        this.dialog = dialog;
        this.ctx = ctx;
        this.postId=postId;
        activity=(Activity) ctx;
    }

    @Override
    protected Void doInBackground(String... strings) {
        if(strings[0].equals("load")){
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendToRecView=dialog.findViewById(R.id.sendToRecView);
                        friends=new ArrayList<>();
                        sendToRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        sendToRecViewAdapter=new SendToRecViewAdapter(friends,ctx,userId,postId);
                        sendToRecView.setAdapter(sendToRecViewAdapter);
                    }
                });
                URL url = new URL(activity.getString(R.string.friendUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
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
                //System.out.println(jsonString);
                if(!jsonString.equals("")){
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("friends");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject friendObject=jsonArray.getJSONObject(count);
                        count++;
                        Friend friend=new Friend(friendObject.getString("userName"),friendObject.getString("firstName"),friendObject.getInt("user_id"),friendObject.getString("imageurl"));
                        publishProgress(friend);
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
        }else if(strings[0].equals("send")){
//            System.out.println("Here");
//            System.out.println(strings[1]);
//            System.out.println(postId);
//            System.out.println(userId);
            try {
                System.out.println("Herere");
                URL url = new URL(activity.getString(R.string.sharePostUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("friendId", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8");
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
//                if(!jsonString.equals("")){
//                    JSONObject jsonObject=new JSONObject(jsonString);
//                    JSONArray jsonArray=jsonObject.getJSONArray("friends");
//                    System.out.println(jsonArray);
//                    int count=0;
//                    while(count<jsonArray.length()){
//                        JSONObject friendObject=jsonArray.getJSONObject(count);
//                        count++;
//                        Friend friend=new Friend(friendObject.getString("userName"),friendObject.getString("firstName"),friendObject.getInt("user_id"),friendObject.getString("imageurl"));
//                        publishProgress(friend);
//                    }
//                }

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
    protected void onProgressUpdate(Friend... values) {
        friends.add(values[0]);
        sendToRecViewAdapter.notifyDataSetChanged();
    }
}
