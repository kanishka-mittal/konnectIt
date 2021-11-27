package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

public class SingleCommentBgTask extends AsyncTask<String,Void,String> {
    int commentId,userId;
    TextView commenttextview;
    TextView usernametextview;
    ImageView profilepic;
    Context ctx;
    Activity activity;
    String method;
    public SingleCommentBgTask(Context ctx, int commentId,int userId) {
        this.ctx = ctx;
        this.userId=userId;
        this.commentId=commentId;
        activity=(Activity) ctx;
        profilepic=activity.findViewById(R.id.profilepicCommentpage);
        commenttextview=activity.findViewById(R.id.commentextCommentpage);
        usernametextview=activity.findViewById(R.id.userNameCommentpage);
    }

    @Override
    protected String doInBackground(String... strings) {
        method=strings[0];
        if(strings[0].equals("load")){
            try {
                URL url = new URL(activity.getString(R.string.singleCommentLoadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("commentId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(commentId), "UTF-8");
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
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        if(method.equals("load")){
            try{
                System.out.println(jsonString);
                if(!jsonString.equals("")){
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONObject commentObject=jsonObject.getJSONArray("singlecomment").getJSONObject(0);
                    usernametextview.setText(commentObject.getString("userName"));
                    if(!(commentObject.getString("imageurl").equals("null"))){
                        Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load(commentObject.getString("imageurl")).into(profilepic);
                    }
                    commenttextview.setText(commentObject.getString("commentText"));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
