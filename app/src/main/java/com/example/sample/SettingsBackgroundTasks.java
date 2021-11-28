package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
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

public class SettingsBackgroundTasks extends AsyncTask<String,Void,String> {
    Context ctx;
    Activity activity;
    int userID;
    String method;
    TextView deleteProfile,logout;
    Switch hideAge,hideGender,hidePosts,hideInterests,hideMobile;
    Button savesettings;
    int hidepost,hideage,hidemobile,hidegender,hideinterests;

    public SettingsBackgroundTasks(Context ctx,int userID) {
        this.ctx = ctx;
        activity=(Activity) ctx;
        this.userID=userID;
        deleteProfile=activity.findViewById(R.id.settingsdeleteprofile);
        logout=activity.findViewById(R.id.settingslogout);
        hideAge=activity.findViewById(R.id.switchhideAge);
        hideGender=activity.findViewById(R.id.switchhideGender);
        hideInterests=activity.findViewById(R.id.switchhideInterests);
        hidePosts=activity.findViewById(R.id.switchhidePosts);
        hideMobile=activity.findViewById(R.id.switchhideMobile);
        savesettings=activity.findViewById(R.id.btnsave);
    }

    public SettingsBackgroundTasks(Context ctx,int userID,int hideage,int hideinterests,int hidepost,int hidemobile,int hidegender) {
        this.ctx = ctx;
        activity=(Activity) ctx;
        this.userID=userID;
        this.hidepost=hidepost;
        this.hideage=hideage;
        this.hidegender=hidegender;
        this.hideinterests=hideinterests;
        this.hidemobile=hidemobile;
    }


    @Override
    protected String doInBackground(String... strings) {
        method=strings[0];
        if(strings[0].equals("settingsload")){
            try {
                URL url = new URL(activity.getString(R.string.settingsLoadUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8");
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
        else if(strings[0].equals("settingssave")) {
            try {
                URL url = new URL(activity.getString(R.string.settingsSaveUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8") + "&" + URLEncoder.encode("hidepost", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(hidepost), "UTF-8") + "&" + URLEncoder.encode("hidegender", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(hidegender), "UTF-8") + "&" + URLEncoder.encode("hideage", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(hideage), "UTF-8") + "&" + URLEncoder.encode("hidemobile", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(hidemobile), "UTF-8") + "&" + URLEncoder.encode("hideinterests", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(hideinterests), "UTF-8");
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
                System.out.println(response);
                return response;
            } catch (MalformedURLException | ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(strings[0].equals("deleteprofile")) {
            try {
                URL url = new URL(activity.getString(R.string.deleteprofileUrl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userID), "UTF-8");
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
                System.out.println(response);
                return response;
            } catch (MalformedURLException | ProtocolException e) {
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
                if(method.equals("settingsload")){
                    try{
                        System.out.println(jsonString);
                        if(!jsonString.equals("")){
                            JSONObject jsonObject=new JSONObject(jsonString);
                            JSONObject postObject=jsonObject.getJSONArray("settingsresponse").getJSONObject(0);

                            hidepost=Integer.valueOf(postObject.getString("private"));
                            hideage=Integer.valueOf(postObject.getString("ageprivate"));
                            hidegender=Integer.valueOf(postObject.getString("genderprivate"));
                            hideinterests=Integer.valueOf(postObject.getString("bioprivate"));
                            hidemobile=Integer.valueOf(postObject.getString("mobileprivate"));

                            if(hidepost==1){
                                hidePosts.setChecked(true);
                            }else{hidePosts.setChecked(false);}

                            if(hideage==1){
                                hideAge.setChecked(true);
                            }else{hideAge.setChecked(false);}

                            if(hidegender==1){
                                hideGender.setChecked(true);
                            }else{hideGender.setChecked(false);}

                            if(hideinterests==1){
                                hideInterests.setChecked(true);
                            }else{hideInterests.setChecked(false);}

                            if(hidemobile==1){
                                hideMobile.setChecked(true);
                            }else{hideMobile.setChecked(false);}

                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else if(method.equals("deleteprofile")){
                    Intent intent=new Intent(activity,Register.class);
                    activity.startActivity(intent);
                    activity.finishAffinity();
                }
            }
        });

    }
}
