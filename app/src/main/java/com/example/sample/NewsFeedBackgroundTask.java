package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

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

public class NewsFeedBackgroundTask extends AsyncTask<String,PostModel,Boolean> {
    Context ctx;
    Activity activity;
    RecyclerView postRecView;
    ArrayList posts;
    PostRecViewAdapter postRecViewAdapter;
    PostRecViewAdapter.ViewHolder holder;
    int userId,accessedByUser;
    String method="";
    public NewsFeedBackgroundTask(Context ctx, int userId) {
        this.ctx = ctx;
        this.userId = userId;
        activity=(Activity) ctx;
    }
    public NewsFeedBackgroundTask(Context ctx, int userId,int accessedByUser) {
        this.ctx = ctx;
        this.userId = userId;
        activity=(Activity) ctx;
        this.accessedByUser=accessedByUser;

    }
    public NewsFeedBackgroundTask(Context ctx, int userId,PostRecViewAdapter.ViewHolder holder) {
        this.ctx = ctx;
        this.userId = userId;
        activity=(Activity) ctx;
        this.holder=holder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }



    @Override
    protected Boolean doInBackground(String... params) {
        method=params[0];
        if(params[0].equals("loadNF")){
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postRecView=activity.findViewById(R.id.postRecView);
                        posts=new ArrayList();
                        postRecViewAdapter=new PostRecViewAdapter(posts,ctx,userId);
                        postRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        postRecView.setAdapter(postRecViewAdapter);
                    }
                });

                URL url = new URL(activity.getString(R.string.newsFeedLoadUrl));
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
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                System.out.println(jsonString);
                if(!jsonString.equals("")){
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("posts");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject postObject=jsonArray.getJSONObject(count);
                        count++;
                        PostModel postModel=new PostModel(postObject.getString("userName"),postObject.getString("firstName"),postObject.getInt("postId"),postObject.getInt("user_id"),postObject.getInt("numLikes"),postObject.getInt("numComments"),postObject.getString("postText"),postObject.getString("postImageURL"),postObject.getString("imageurl"),postObject.getString("TIMEDIFF(CURRENT_TIMESTAMP, p.postedAt)"));
                        publishProgress(postModel);
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
        else if(params[0].equals("loadPr")) {
            try {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        postRecView = activity.findViewById(R.id.profilePostRecView);
                        posts = new ArrayList();
                        postRecViewAdapter = new PostRecViewAdapter(posts, ctx, userId,accessedByUser);
                        postRecView.setLayoutManager(new LinearLayoutManager(ctx));
                        postRecView.setAdapter(postRecViewAdapter);
                    }
                });
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                URL url = new URL(activity.getString(R.string.ProfileFeedUrl));
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
                System.out.println(data);
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                String jsonString = stringBuilder.toString().trim();
                System.out.println(jsonString);
                if (!jsonString.equals("")) {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("posts");
                    System.out.println(jsonArray);
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject postObject = jsonArray.getJSONObject(count);
                        count++;
                        PostModel postModel = new PostModel(postObject.getString("userName"), postObject.getString("firstName"), postObject.getInt("postId"), postObject.getInt("user_id"), postObject.getInt("numLikes"), postObject.getInt("numComments"), postObject.getString("postText"), postObject.getString("postImageURL"), postObject.getString("imageurl"),postObject.getString("TIMEDIFF(CURRENT_TIMESTAMP, p.postedAt)"));
                        publishProgress(postModel);
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
        else if(params[0].equals("like")){
            try {
                URL url = new URL(activity.getString(R.string.likeUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("postUser", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
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
        }else if(params[0].equals("unlike")){
            try {
                URL url = new URL(activity.getString(R.string.unlikeUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("postUser", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
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
        }else if(params[0].equals("isLiked")){
            try {
                URL url = new URL(activity.getString(R.string.isLikedUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("postUser", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
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
                if(jsonString.equals("true")){
                    return true;
                }else{
                    return false;
                }
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
    protected void onProgressUpdate(PostModel... values) {
        posts.add(values[0]);
        postRecViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(method.equals("isLiked")){
            if(aBoolean==true){
                holder.dislike.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
            }else{
                holder.like.setVisibility(View.GONE);
                holder.dislike.setVisibility(View.VISIBLE);
            }
        }

    }
}
