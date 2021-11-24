package com.example.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Newsfeedpostbgtasks extends AsyncTask<Void,Void,String> {

    String postText;
    int userId;
    Context ctx;
    Activity activity;
    Bitmap bitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArrayVar;
    final String convertImage;

    public Newsfeedpostbgtasks(int userId, Context ctx, Bitmap bitmap,String posttext) {

        this.postText=posttext;
        this.userId = userId;
        this.ctx = ctx;
        this.bitmap = bitmap;
        activity=(Activity) ctx;
        byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byteArrayVar=byteArrayOutputStream.toByteArray();
        convertImage= Base64.encodeToString(byteArrayVar,Base64.DEFAULT);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(ctx,"Posted Successfully!",Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        StringBuilder stringBuilder=new StringBuilder();

        try{
            URL url=new URL(activity.getString(R.string.postUrl));
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
            String data= URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postText", "UTF-8") + "=" + URLEncoder.encode(postText, "UTF-8") + "&" + URLEncoder.encode("image_path", "UTF-8") + "=" + URLEncoder.encode(convertImage, "UTF-8");
            System.out.println(userId);
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
        return stringBuilder.toString();
    }
}
