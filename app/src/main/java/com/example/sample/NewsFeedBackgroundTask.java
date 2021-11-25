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

public class NewsFeedBackgroundTask extends AsyncTask<Void,PostModel,Void> {
    Context ctx;
    Activity activity;
    RecyclerView postRecView;
    ArrayList posts;
    PostRecViewAdapter postRecViewAdapter;
    int userId;

    public NewsFeedBackgroundTask(Context ctx, int userId) {
        this.ctx = ctx;
        this.userId = userId;
        activity=(Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        postRecView=activity.findViewById(R.id.postRecView);
        posts=new ArrayList();
        postRecViewAdapter=new PostRecViewAdapter(posts,ctx,userId);
        postRecView.setLayoutManager(new LinearLayoutManager(ctx));
        postRecView.setAdapter(postRecViewAdapter);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
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
            System.out.println(jsonString);
            if(!jsonString.equals("")){
                JSONObject jsonObject=new JSONObject(jsonString);
                JSONArray jsonArray=jsonObject.getJSONArray("posts");
                System.out.println(jsonArray);
                int count=0;
                while(count<jsonArray.length()){
                    JSONObject postObject=jsonArray.getJSONObject(count);
                    count++;
                    PostModel postModel=new PostModel(postObject.getString("userName"),postObject.getString("firstName"),postObject.getInt("postId"),postObject.getInt("user_id"),postObject.getInt("numLikes"),postObject.getInt("numComments"));
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
        return null;
    }
    @Override
    protected void onProgressUpdate(PostModel... values) {
        posts.add(values[0]);
        postRecViewAdapter.notifyDataSetChanged();
    }
}
