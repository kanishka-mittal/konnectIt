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

public class Post_bgTasks extends AsyncTask<String,Comments,Void> {
    Context ctx;
    Activity activity;
    RecyclerView commentsRecView;
    Comments_Recview_Adapter CommentsRecViewAdapter;
    ArrayList<Comments> comments;
    int postID;
    int userID;
    String commentText;

    public Post_bgTasks(Context ctx,int postID,int userID) {
        this.ctx = ctx;
        this.postID=postID;
        activity=(Activity) ctx;
        this.userID=userID;
    }

    public Post_bgTasks(Context ctx,int postID,int userID,String commentText) {
        this.ctx = ctx;
        this.postID=postID;
        activity=(Activity) ctx;
        this.userID=userID;
        this.commentText=commentText;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(String... params) {
        if(params[0].equals("commentsload")){

            try {
                commentsRecView=activity.findViewById(R.id.recyclerViewcomment);
                comments=new ArrayList<>();
                commentsRecView.setLayoutManager(new LinearLayoutManager(ctx));
                CommentsRecViewAdapter=new Comments_Recview_Adapter(comments,ctx,postID,userID);
                commentsRecView.setAdapter(CommentsRecViewAdapter);
                URL url = new URL(activity.getString(R.string.postloadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postID), "UTF-8");
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
                    JSONArray jsonArray=jsonObject.getJSONArray("comments");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject commentObject=jsonArray.getJSONObject(count);
                        count++;
                        Comments comment=new Comments(commentObject.getString("userName"),commentObject.getString("commentText"),commentObject.getInt("user_id"),commentObject.getInt("commentId"),postID);
                        publishProgress(comment);
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
        }else if(params[0].equals("addcomment")){

            StringBuilder stringBuilder=new StringBuilder();

            try{
                URL url=new URL(activity.getString(R.string.addCommentUrl));
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader;
                BufferedWriter bufferedWriter;
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                bufferedWriter=new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
                String data= URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8") + "&" + URLEncoder.encode("commentText", "UTF-8") + "=" + URLEncoder.encode(commentText, "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postID), "UTF-8");
                System.out.println(postID);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
                stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Comments... values) {
        comments.add(values[0]);
        CommentsRecViewAdapter.notifyDataSetChanged();
    }
}
