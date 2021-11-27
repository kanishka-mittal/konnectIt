package com.example.sample;

import android.app.Activity;
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

public class ChatBackgroundTask extends AsyncTask<String,Message,Void> {
    int userId;
    int friendId;
    Context ctx;
    Activity activity;
    RecyclerView chatRecView;
    ChatRecViewAdapter chatRecViewAdapter;
    ArrayList<Message> messages;
    String method="";
    public ChatBackgroundTask(int userId, int friendId, Context ctx) {
        this.userId = userId;
        this.friendId = friendId;
        this.ctx = ctx;
        activity=(Activity) ctx;
    }

    @Override
    protected Void doInBackground(String... strings) {
        method=strings[0];
        if(strings[0].equals("load")){
            try{
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatRecView=activity.findViewById(R.id.chatRecView);
                        messages=new ArrayList<>();
                        chatRecViewAdapter =new ChatRecViewAdapter(ctx,userId,friendId,messages);
                        chatRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        chatRecView.setAdapter(chatRecViewAdapter);
                    }
                });
                URL url = new URL(activity.getString(R.string.chatLoadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("friendId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(friendId), "UTF-8");
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
                    JSONArray jsonArray=jsonObject.getJSONArray("messages");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject msgobject=jsonArray.getJSONObject(count);
                        count++;
                        Message message=new Message(msgobject.getInt("senderId"),msgobject.getInt("recipientId"),msgobject.getString("timestamp"),msgobject.getString("msgTxt"));
                        publishProgress(message);
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
            try{
                URL url = new URL(activity.getString(R.string.sendMsgUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("senderId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("recipientId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(friendId), "UTF-8") + "&" + URLEncoder.encode("msgTxt", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
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
//                    JSONArray jsonArray=jsonObject.getJSONArray("messages");
//                    System.out.println(jsonArray);
//                    int count=0;
//                    while(count<jsonArray.length()){
//                        JSONObject msgobject=jsonArray.getJSONObject(count);
//                        count++;
//                        Message message=new Message(msgobject.getInt("senderId"),msgobject.getInt("recipientId"),msgobject.getString("timestamp"),msgobject.getString("msgTxt"));
//                        publishProgress(message);
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
    protected void onProgressUpdate(Message... values) {
        messages.add(values[0]);
        chatRecViewAdapter.notifyDataSetChanged();
    }
}
