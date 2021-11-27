package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
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

public class EditPostLoadBgTask extends AsyncTask<Void,Void,String> {
    int postId;
    Context ctx;
    Activity activity;
    TextView edtPostText;
    ImageView edtPostPic;
    public EditPostLoadBgTask(int postId, Context ctx) {
        this.postId = postId;
        this.ctx = ctx;
        activity=(Activity) ctx;
        edtPostPic=activity.findViewById(R.id.edtPostPic);
        edtPostText=activity.findViewById(R.id.edtPostText);
    }
    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(activity.getString(R.string.editPostLoadUrl));
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
            return jsonString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(!s.equals("")) {

                        JSONObject jsonObject = new JSONObject(s);
                        JSONObject postObject = jsonObject.getJSONArray("post").getJSONObject(0);
                        edtPostText.setText(postObject.getString("postText"));
                        String postImageURL=postObject.getString("postImageURL");
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        System.out.println(postImageURL);
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
                        if(!(postImageURL.equals("null"))){
                            Glide.with(ctx).asBitmap().load("http://10.0.2.2/konnectIt/posts_image/"+postId+".png").into(edtPostPic);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
