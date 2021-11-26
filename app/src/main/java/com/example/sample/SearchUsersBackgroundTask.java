package com.example.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class SearchUsersBackgroundTask extends AsyncTask<String,User,Void> {
    Context ctx;
    Activity activity;
    int userId;
    String searchTxt;
    RecyclerView searchUsersRecView;
    SearchUsersRecViewAdapter searchUsersRecViewAdapter;
    ArrayList<User> users;
    String method;
    AlertDialog.Builder builder;
    public SearchUsersBackgroundTask(Context ctx, int userId, String searchTxt) {
        this.ctx = ctx;
        activity=(Activity)ctx;
        this.userId = userId;
        this.searchTxt = searchTxt;
        builder=new AlertDialog.Builder(ctx);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        method=params[0];
        if(method.equals("load")){
            try{
                searchUsersRecView=activity.findViewById(R.id.searchUsersRecView);
                users=new ArrayList<>();
                searchUsersRecViewAdapter=new SearchUsersRecViewAdapter(users,ctx,userId);
                searchUsersRecView.setAdapter(searchUsersRecViewAdapter);
                searchUsersRecView.setLayoutManager(new LinearLayoutManager(ctx));
                URL url=new URL(activity.getString(R.string.searchUsersUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data=URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("searchTxt", "UTF-8") + "=" + URLEncoder.encode(searchTxt, "UTF-8");
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
                        User user=new User(friendObject.getString("userName"),friendObject.getString("firstName"),friendObject.getInt("user_id"),friendObject.getString("imageurl"));
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
            try{
                URL url=new URL(activity.getString(R.string.addFriendUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String otherUserId=params[1];
                System.out.println(userId);
                System.out.println(otherUserId);
                String data=URLEncoder.encode("user1", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("user2", "UTF-8") + "=" + URLEncoder.encode(otherUserId, "UTF-8");
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
                String responseString=stringBuilder.toString().trim();
                System.out.println(responseString);
                if(responseString.equals("Already Sent")){
                    builder.setMessage("You have aleady sent a friend request to this user....Wait for response :(");
                    builder.setCancelable(true);
                }else if(responseString.equals("Already Received")){
                    builder.setMessage("you have a pending friend request from this user");
                    builder.setPositiveButton("Pending Friend Requests", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(ctx, "Functionality to be added", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setCancelable(true);
                }else if(responseString.equals("Sent")){
                    builder.setMessage("Friend Request Sent");
                    builder.setCancelable(true);
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
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(User... values) {
        if(method.equals("load")){
            users.add(values[0]);
            searchUsersRecViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPostExecute(Void unused) {
        if(method.equals("addFriend")){
            builder.show();
        }
    }
}
