package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class Post_bgTasks extends AsyncTask<String,Comments,Void> {
    Context ctx;
    Activity activity;
    RecyclerView commentsRecView;
    Comments_Recview_Adapter CommentsRecViewAdapter;
    ArrayList<Comments> comments;
    int postID;
    int userID;

    public Post_bgTasks(Context ctx,int postID,int userID) {
        this.ctx = ctx;
        this.postID=postID;
        activity=(Activity) ctx;
        this.userID=userID;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        commentsRecView=activity.findViewById(R.id.recyclerViewcomment);
        comments=new ArrayList<>();
        commentsRecView.setLayoutManager(new LinearLayoutManager(ctx));
        CommentsRecViewAdapter=new Comments_Recview_Adapter(comments,ctx,postID);
        commentsRecView.setAdapter(CommentsRecViewAdapter);
    }


    @Override
    protected Void doInBackground(String... params) {
        //String friendUrl="http://10.0.2.2//konnectit/friends.php";
        if(params[0].equals("postload")){
            try {
                URL url = new URL(activity.getString(R.string.postloadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(postID), "UTF-8");
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
                //System.out.println(jsonString);
                if(!jsonString.equals("")){
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("comments");
                    System.out.println(jsonArray);
                    int count=0;
                    while(count<jsonArray.length()){
                        JSONObject commentObject=jsonArray.getJSONObject(count);
                        count++;
                        Comments comment=new Comments(commentObject.getString("userName"),commentObject.getString("commentText"),commentObject.getInt("user_id"),commentObject.getInt("commentId"),postID);
                        publishProgress(comment);
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
        }else if(params[0].equals("postcomment")){
//            try{
//                URL url = new URL(activity.getString(R.string.remFriendUrl));
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream os = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
//                String data = URLEncoder.encode("friendId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
//                System.out.println(params[1]);
//                System.out.println(postID);
//                bufferedWriter.write(data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                InputStream is = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
//                bufferedReader.close();
//                is.close();
//                httpURLConnection.disconnect();
//                Intent intent=new Intent(activity,Post.class);
//                intent.putExtra("postId",postID);
//                activity.startActivity(intent);
//                activity.finish();
//                return null;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Comments... values) {
        comments.add(values[0]);
        CommentsRecViewAdapter.notifyDataSetChanged();
    }
}
