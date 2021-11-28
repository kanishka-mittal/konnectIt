package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample.EditProfile;
import com.example.sample.Profile_info;
import com.example.sample.R;

import org.json.JSONArray;
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
import java.net.URL;
import java.net.URLEncoder;

public class NewsFeedGetNameBGTask extends AsyncTask<Void, Void, String> {
    Context ctx;
    Activity activity;
    int userId;
    String firstName;

    public NewsFeedGetNameBGTask(Context ctx, int userId) {
        this.ctx = ctx;
        activity=(Activity) ctx;
        this.userId = userId;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String info) {
        super.onPreExecute();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONArray array = new JSONArray(info);
                    JSONObject profileinfo = array.getJSONObject(0);

                    try{
                        firstName = profileinfo.getString("firstName");
                        TextView firstname = activity.findViewById(R.id.homename);
                        firstname.setText(firstName);
                    }
                    catch (org.json.JSONException e){
                        e.printStackTrace();
                    }
                }
                catch(org.json.JSONException e){
                    Toast.makeText(ctx,info,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected String doInBackground(Void... voids){
        String info="";
        try{
            URL url = new URL(activity.getString(R.string.profileUrl));
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
            info = bufferedReader.readLine();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;

    }
}

