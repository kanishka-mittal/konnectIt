package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

public class BackgroundTask extends AsyncTask<String,String,String> {
    Context ctx;
    Activity activity;
    String method;
//    AlertDialog.Builder builder;
//    AlertDialog alertDialog;
    int userId;
    BackgroundTask(Context ctx){
        this.ctx=ctx;
        activity=(Activity) ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //builder=new AlertDialog.Builder(ctx);
        //builder.setTitle("Login Information");
    }
    @Override
    protected String doInBackground(String... params) {
        String regUrl="http://10.0.2.2//konnectit/register.php";
        String logInUrl="http://10.0.2.2//konnectit/login.php";
        method=params[0];
        if(method.equals("register")) {
            String username = params[1];
            String fName = params[2];
            String email = params[3];
            String pswd = params[4];
            try {
                URL url = new URL(regUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("fName", "UTF-8") + "=" + URLEncoder.encode(fName, "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pswd, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                //System.out.println(response);
                is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException | ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("login")){
            String username = params[1];
            String pswd = params[2];
            try {
                URL url = new URL(logInUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pswd, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("user_info");
                JSONObject userObject=jsonArray.getJSONObject(0);
                userId=userObject.getInt("user_id");
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException | ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        if(method.equals("register")){
            Intent intent=new Intent(activity,EditProfile.class);
            intent.putExtra("userId",Integer.valueOf(result));
            activity.startActivity(intent);
            activity.finish();
            Toast.makeText(ctx, "Registration Success", Toast.LENGTH_SHORT).show();
        }else{
//            builder.setPositiveButton("Notifications", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Intent intent = new Intent(ctx,Notifications.class);
//                    intent.putExtra("userId",userId);
//                    ctx.startActivity(intent);
//                }
//            });
//            builder.setNegativeButton("Friends", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Intent intent = new Intent(ctx,Friends.class);
//                    intent.putExtra("userId",userId);
//                    ctx.startActivity(intent);
//                }
//            });
//            alertDialog = builder.create();
//            alertDialog.show();
            //TODO currently being redirected to notifications page,,,, chnage this to news feed page
            Intent intent=new Intent(activity,NewsFeed.class);
            intent.putExtra("userId",userId);
            activity.startActivity(intent);
            activity.finish();
        }
    }



}
