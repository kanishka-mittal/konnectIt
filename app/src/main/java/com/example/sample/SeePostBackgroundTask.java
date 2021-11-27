package com.example.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

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

public class SeePostBackgroundTask extends AsyncTask<String,Void,String>{
    int userId;
    int postId;
    Context ctx;
    Activity activity;

    public SeePostBackgroundTask(int userId, int postId, Context ctx) {
        this.userId = userId;
        this.postId = postId;
        this.ctx = ctx;
        activity=(Activity) ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        if(params[0].equals("privacy")){
            try {
                URL url = new URL(activity.getString(R.string.isPostVisibleUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8") + "&" + URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
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
                return jsonString;
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
    protected void onPostExecute(String s) {
        if(s.equals("true")){
            Intent intent=new Intent(ctx,Post.class);
            intent.putExtra("userId",userId);
            intent.putExtra("postId",postId);
            activity.startActivity(intent);
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
            builder.setCancelable(true);
            builder.setMessage("Post Not visible to you");
            builder.show();
        }
    }
}
