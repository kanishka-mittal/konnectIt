package com.example.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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

public class EditprofileoptionbgTasks extends AsyncTask<String,Void,String> {
    int userId;
    String lastname,bio,gender,mobnum,age;
    Context ctx;
    Activity activity;
    EditText lastName,Bio,Gender,mobNum,Age;
    Bitmap bitmap;
    String method;
    ImageView profilepic;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArrayVar;
    String convertImage;

    public EditprofileoptionbgTasks(int userId, Context ctx, String convertImage,String Gender,String mobNum,String Bio,String lastName,String Age) {
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

    public EditprofileoptionbgTasks(int userId, Context ctx,EditText Gender,EditText mobNum,EditText Age,EditText Bio,EditText lastName ) {
        this.userId = userId;
        this.ctx = ctx;
        this.Gender=Gender;
        this.Age=Age;
        this.mobNum=mobNum;
        this.Bio=Bio;
        this.lastName=lastName;
        activity=(Activity) ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        method=strings[0];
        if(strings[0].equals("saveprofile")) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(activity.getString(R.string.uploadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader;
                BufferedWriter bufferedWriter;
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                System.out.println("##################################################################");
                System.out.println(convertImage);
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("image_path", "UTF-8") + "=" + URLEncoder.encode(convertImage, "UTF-8") + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8") + "&" + URLEncoder.encode("Bio", "UTF-8") + "=" + URLEncoder.encode(bio, "UTF-8") + "&" + URLEncoder.encode("mobNum", "UTF-8") + "=" + URLEncoder.encode(mobnum, "UTF-8") + "&" + URLEncoder.encode("Age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&" + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
                //String data= URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8")+ "&" + URLEncoder.encode("Bio", "UTF-8") + "=" + URLEncoder.encode(bio, "UTF-8")+ "&" + URLEncoder.encode("mobNum", "UTF-8") + "=" + URLEncoder.encode(mobnum, "UTF-8")+ "&" + URLEncoder.encode("Age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")+ "&" + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
                System.out.println(userId);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(stringBuilder.toString());
            return stringBuilder.toString();
        }
        else if(strings[0].equals("loadprofilescreen")){
            try {
                URL url = new URL(activity.getString(R.string.profileLoadUrl));
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        if(method.equals("loadprofilescreen")){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        System.out.println(jsonString);
                        if(!jsonString.equals("")){
                            JSONObject jsonObject=new JSONObject(jsonString);
                            JSONObject editprofileObject=jsonObject.getJSONArray("editprofileresponse").getJSONObject(0);
                            if(!editprofileObject.getString("lastName").equals("null")) {
                                lastName.setText(editprofileObject.getString("lastName"));
                            }
                            if(!editprofileObject.getString("bio").equals("null")) {
                                Bio.setText(editprofileObject.getString("bio"));
                            }
                            if(!editprofileObject.getString("gender").equals("null")) {
                                Gender.setText(editprofileObject.getString("gender"));
                            }
                            if(!editprofileObject.getString("mobile").equals("null")) {
                                mobNum.setText(editprofileObject.getString("mobile"));
                            }
                            if(!editprofileObject.getString("age").equals("null")) {
                                Age.setText(editprofileObject.getString("age"));
                            }
                            profilepic=activity.findViewById(R.id.uploadProfilePicoption);

//                            lastName.setText(editprofileObject.getString("lastName"));
//                            Bio.setText(editprofileObject.getString("bio"));
//                            Gender.setText(editprofileObject.getString("gender"));
//                            mobNum.setText(editprofileObject.getString("mobile"));
//                            Age.setText(editprofileObject.getString("age"));
                            if(!(editprofileObject.getString("imageurl").equals("null"))) {
                                Glide.with(ctx).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/" + Integer.toString(userId) + ".png").into(profilepic);
                            }

                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
