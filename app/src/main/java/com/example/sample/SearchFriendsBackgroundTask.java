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

public class SearchFriendsBackgroundTask extends AsyncTask<Void,Friend,Void> {
    Context ctx;
    Activity activity;
    int userId;
    int accessedByUser;
    String searchTxt;
    RecyclerView searchFriendsRecView;
    FriendRecViewAdapter friendRecViewAdapter;
    ArrayList<Friend> friends;

    public SearchFriendsBackgroundTask(Context ctx, int userId, String searchTxt, int accessedByUser) {
        this.ctx = ctx;
        activity=(Activity)ctx;
        this.userId = userId;
        this.searchTxt = searchTxt;
        this.accessedByUser=accessedByUser;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchFriendsRecView=activity.findViewById(R.id.searchFriendsRecView);
                    friends=new ArrayList<>();
                    friendRecViewAdapter=new FriendRecViewAdapter(friends,ctx,userId,accessedByUser);
                    searchFriendsRecView.setAdapter(friendRecViewAdapter);
                    searchFriendsRecView.setLayoutManager(new LinearLayoutManager(ctx));
                }
            });

            URL url=new URL(activity.getString(R.string.searchFriendsUrl));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            String data=URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("searchTxt", "UTF-8") + "=" + URLEncoder.encode(searchTxt, "UTF-8");
            //String data=URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
            System.out.println(userId);
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
            if(!jsonString.equals("")){
                JSONObject jsonObject=new JSONObject(jsonString);
                JSONArray jsonArray=jsonObject.getJSONArray("friends");
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
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Friend... values) {
        friends.add(values[0]);
        friendRecViewAdapter.notifyDataSetChanged();
    }



}
