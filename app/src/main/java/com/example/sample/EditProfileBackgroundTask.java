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

public class EditProfileBackgroundTask extends AsyncTask<Void,Void,String> {
    int userId;
    String lastname,bio,gender,mobnum,age;
    Context ctx;
    Activity activity;
    Bitmap bitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArrayVar;
    final String convertImage;

    public EditProfileBackgroundTask(int userId, Context ctx, String convertImage,String Gender,String mobNum,String Bio,String lastName,String Age) {
        this.userId = userId;
        this.gender=Gender;
        this.age=Age;
        this.lastname=lastName;
        this.bio=Bio;
        this.mobnum=mobNum;
        this.ctx = ctx;
        this.convertImage=convertImage;
        activity=(Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder stringBuilder=new StringBuilder();
        try{
            URL url=new URL(activity.getString(R.string.uploadUrl));
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
            System.out.println("##################################################################");
            System.out.println(convertImage);
            String data= URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("image_path", "UTF-8") + "=" + URLEncoder.encode(convertImage, "UTF-8")+ "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8")+ "&" + URLEncoder.encode("Bio", "UTF-8") + "=" + URLEncoder.encode(bio, "UTF-8")+ "&" + URLEncoder.encode("mobNum", "UTF-8") + "=" + URLEncoder.encode(mobnum, "UTF-8")+ "&" + URLEncoder.encode("Age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")+ "&" + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
            //String data= URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8")+ "&" + URLEncoder.encode("Bio", "UTF-8") + "=" + URLEncoder.encode(bio, "UTF-8")+ "&" + URLEncoder.encode("mobNum", "UTF-8") + "=" + URLEncoder.encode(mobnum, "UTF-8")+ "&" + URLEncoder.encode("Age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")+ "&" + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
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
