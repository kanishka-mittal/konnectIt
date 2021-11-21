package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class FriendBackgroundTask extends AsyncTask<String,Friend,Void> {
    Context ctx;
    Activity activity;
    RecyclerView friendsRecView;
    FriendRecViewAdapter friendRecViewAdapter;
    ArrayList<Friend> friends;
    int userId;

    public FriendBackgroundTask(Context ctx,int userId) {
        this.ctx = ctx;
        this.userId=userId;
        activity=(Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        friendsRecView=activity.findViewById(R.id.friendsRecView);
        friends=new ArrayList<>();
        friendsRecView.setLayoutManager(new LinearLayoutManager(ctx));
        friendRecViewAdapter=new FriendRecViewAdapter(friends,ctx,userId);
        friendsRecView.setAdapter(friendRecViewAdapter);
    }


    @Override
    protected Void doInBackground(String... params) {
        //String friendUrl="http://10.0.2.2//konnectit/friends.php";
        if(params[0].equals("load")){
            try {
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
                        Friend friend=new Friend(friendObject.getString("userName"),friendObject.getString("firstName"),friendObject.getInt("user_id"));
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
        }else if(params[0].equals("remFriend")){
            try{
                URL url = new URL(activity.getString(R.string.remFriendUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("friendId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
                System.out.println(params[1]);
                System.out.println(userId);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return null;
            }catch (MalformedURLException e) {
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
        friendRecViewAdapter.notifyDataSetChanged();
    }

}
