package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.squareup.picasso.Picasso;

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
import java.sql.SQLOutput;


public class SinglePostBgTask extends AsyncTask<String,Void,String> {
    Context ctx;
    Activity activity;
    String method,timeDiff;
    int postId,userId;
    TextView userName,firstName,numLikes,numComments,postContent,time;
    ImageView postImage,profilepic,like,dislike,dustbin,btnEditPost;
    EditText commentText;
    Button btnpostComment;
    public SinglePostBgTask(Context ctx, int postId,int userId) {
        this.ctx = ctx;
        this.userId=userId;
        this.postId=postId;
        activity=(Activity) ctx;
        dustbin=activity.findViewById(R.id.dustbinpostpage);
        like=activity.findViewById(R.id.likepostpage);
        dislike=activity.findViewById(R.id.dislikepostpage);
        commentText= activity.findViewById(R.id.editTextComment);
        btnpostComment = activity.findViewById(R.id.buttoncomment);
        userName=activity.findViewById(R.id.userNamepostpage);
        firstName=activity.findViewById(R.id.firstNamepostpage);
        postImage=activity.findViewById(R.id.postImagepostpage);
        numComments=activity.findViewById(R.id.numCommentspostpage);
        numLikes=activity.findViewById(R.id.numLikespostpage);
        profilepic=activity.findViewById(R.id.profilepicpostpage);
        postContent=activity.findViewById(R.id.postTextpostpage);
        btnEditPost=activity.findViewById(R.id.btnEditPost);
        time=activity.findViewById(R.id.timePostPage);
    }

    public  String getTimeString(String TimeDiff){
        String[] sections=TimeDiff.split(":");
        int hours=Integer.parseInt(sections[0]);
        int minutes=Integer.parseInt(sections[1]);
        int seconds=Integer.parseInt(sections[2]);
        if(hours>=24){
            int days=hours/24;
            return days + " day ago";
        }
        else if(hours>=1){
            return hours + " hours ago";
        }
        else return minutes + " minutes ago";
    }

    @Override
    protected String doInBackground(String... strings) {
        method=strings[0];
        if(strings[0].equals("load")){
            try {
                URL url = new URL(activity.getString(R.string.singlePostLoadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8");
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
                System.out.println(jsonString);
                System.out.println("yahan print kiya hai");
                return jsonString;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(strings[0].equals("isLiked")){
            try {
                URL url = new URL(activity.getString(R.string.isLikedUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8");
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
        }else if(method.equals("like")){
            try {
                URL url = new URL(activity.getString(R.string.likeUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8");
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
        }else if(method.equals("unlike")){
            try {
                URL url = new URL(activity.getString(R.string.unlikeUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postId), "UTF-8");
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(method.equals("load")){
                    try{
                        System.out.println(jsonString);
                        if(!jsonString.equals("")){
                            JSONObject jsonObject=new JSONObject(jsonString);
                            JSONObject postObject=jsonObject.getJSONArray("singlepost").getJSONObject(0);
                            userName.setText(postObject.getString("userName"));
                            firstName.setText(postObject.getString("firstName"));
                            timeDiff=postObject.getString("TIMEDIFF(CURRENT_TIMESTAMP, p.postedAt)");
                            String formatedTime= getTimeString(timeDiff);
                            System.out.println(formatedTime);
                            time.setText(formatedTime);
                            System.out.println("Yahan print kiya hai formatted time");
                            if(!(postObject.getString("postImageURL").equals("null"))){
                                Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectIt/posts_image/"+postId+".png").into(postImage);

                            }else{
                                postImage.setVisibility(View.GONE);
                            }
                            if(!(postObject.getString("imageurl").equals("null"))){
                                Glide.with(ctx).asBitmap().placeholder(R.mipmap.ic_user).error(R.mipmap.ic_user).load("http://10.0.2.2/konnectIt/profilepics/"+Integer.toString(postObject.getInt("postedBy"))+".png").into(profilepic);
                            }
                            postContent.setText(postObject.getString("postText"));

                            numComments.setText(Integer.toString(postObject.getInt("numComments")));
                            numLikes.setText(Integer.toString(postObject.getInt("numLikes")));
                            if(postObject.getInt("postedBy")==userId){
                                dustbin.setVisibility(View.VISIBLE);
                                btnEditPost.setVisibility(View.VISIBLE);
                            }else{
                                dustbin.setVisibility(View.GONE);
                                btnEditPost.setVisibility(View.GONE);
                            }
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else if(method.equals("isLiked")){
                    if(jsonString.equals("true")){
                        dislike.setVisibility(View.GONE);
                        like.setVisibility(View.VISIBLE);
                    }else{
                        like.setVisibility(View.GONE);
                        dislike.setVisibility(View.VISIBLE);
                    }
                }else if(method.equals("like")){
                    int numlikes=Integer.valueOf(numLikes.getText().toString());
                    numLikes.setText(Integer.toString(numlikes+1));
                }else if(method.equals("unlike")){
                    int numlikes=Integer.valueOf(numLikes.getText().toString());
                    numLikes.setText(Integer.toString(numlikes-1));
                }
            }
        });

    }
}
