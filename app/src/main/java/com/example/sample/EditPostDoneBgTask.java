package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class EditPostDoneBgTask extends AsyncTask<Void,Void,Void> {
    int postId;
    String postText;
    String convertImage;
    Context ctx;
    Activity activity;

    public EditPostDoneBgTask(int postId, String postText, String convertImage, Context ctx) {
        this.postId = postId;
        this.postText = postText;
        this.convertImage = convertImage;
        this.ctx = ctx;
        activity=(Activity) ctx;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        StringBuilder stringBuilder=new StringBuilder();

        try{
            URL url=new URL(activity.getString(R.string.editPostDoneUrl));
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
            String data= URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8") + "&" + URLEncoder.encode("postText", "UTF-8") + "=" + URLEncoder.encode(postText, "UTF-8") + "&" + URLEncoder.encode("image_path", "UTF-8") + "=" + URLEncoder.encode(convertImage, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
            stringBuilder=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            System.out.println("Yo");
            System.out.println(stringBuilder.toString().trim());
            bufferedReader.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
