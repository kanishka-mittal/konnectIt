package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

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

public class DeleteCommentBgTask extends AsyncTask<Void,Void,Void> {
    Context ctx;
    Activity activity;
    int commentId;
    int postId;
    public DeleteCommentBgTask(Context ctx, int commentId,int postId) {
        this.ctx = ctx;
        this.commentId =commentId;
        activity=(Activity) ctx;
        this.postId=postId;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(activity.getString(R.string.delCommentUrl));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            String data = URLEncoder.encode("commentId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(commentId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8");
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
        return null;
    }
}
