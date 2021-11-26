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

public class SearchUsersBackgroundTask extends AsyncTask<String,User,Void> {
    Context ctx;
    Activity activity;
    int userId;
    String searchTxt;
    RecyclerView searchUsersRecView;
    SearchUsersRecViewAdapter searchUsersRecViewAdapter;
    ArrayList<User> users;
    String method;

    public SearchUsersBackgroundTask(Context ctx, int userId, String searchTxt) {
        this.ctx = ctx;
        activity=(Activity)ctx;
        this.userId = userId;
        this.searchTxt = searchTxt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        searchUsersRecView=activity.findViewById(R.id.searchUsersRecView);
        users=new ArrayList<>();
        searchUsersRecView.setLayoutManager(new LinearLayoutManager(ctx));
        searchUsersRecViewAdapter=new SearchUsersRecViewAdapter(users,ctx,userId);
        searchUsersRecView.setAdapter(searchUsersRecViewAdapter);
    }

    @Override
    protected Void doInBackground(String... params) {
        method=params[0];
        if(method.equals("load")){
            try{
                URL url=new URL(activity.getString(R.string.searchUsersUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data=URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("searchTxt", "UTF-8") + "=" + URLEncoder.encode(searchTxt, "UTF-8");
                //String data=URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
                //System.out.println(userId);
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
                if(!(jsonString.equals(""))){
                    System.out.println(jsonString);
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("users");
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject friendObject=jsonArray.getJSONObject(count);
                        count++;
                        User user=new User(friendObject.getString("userName"),friendObject.getString("firstName"),friendObject.getInt("user_id"));
                        publishProgress(user);
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
        }else if(method.equals("addFriend")){

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(User... values) {
        users.add(values[0]);
        searchUsersRecViewAdapter.notifyDataSetChanged();
    }



}
