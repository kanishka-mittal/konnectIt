package com.example.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileBackgroundTask extends AsyncTask<String, Void, String>{
    Context ctx;
    Activity activity;
    TextView fullname;
    TextView Username;
    Profile_info infopage;
    int userId;
    String username;
    String firstName;
    String lastName;
    String mobile;
    String email;
    String age;
    String gender;
    String bio;
    String genderprivate,ageprivate,mobileprivate,bioprivate;

    public ProfileBackgroundTask(Context ctx,int userId, TextView fullname, TextView Username, Profile_info infopage) {
        this.ctx = ctx;
        this.userId=userId;
        activity=(Activity) ctx;
        this.fullname =  fullname;
        this.Username = Username;
        this.infopage = infopage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String info) {
        super.onPreExecute();
        if (info != "") {
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

                        System.out.println(info);
                        System.out.println("yahan print kiya info hai");
                        try {

                            username = profileinfo.getString("username");
                            firstName = profileinfo.getString("firstName");
                            lastName = profileinfo.getString("lastName");
                            mobile = profileinfo.getString("mobile");
                            email = profileinfo.getString("email");
                            age = profileinfo.getString("age");
                            gender = profileinfo.getString("gender");
                            bio = profileinfo.getString("bio");
                            System.out.println(profileinfo.getString("genderprivate"));
                            System.out.println("Yahan print kiya hai gender parameter");

                            bioprivate = profileinfo.getString("bioprivate");
                            ageprivate = profileinfo.getString("ageprivate");
                            mobileprivate = profileinfo.getString("mobileprivate");
                            genderprivate = profileinfo.getString("genderprivate");
                            String fn;
                            if (lastName == "null") {
                                fn = firstName;
                            } else {
                                fn = firstName + " " + lastName;
                            }
                            fullname.setText(fn);
                            Username.setText(username);
                            String agetext;
                            if (age == "null"||age.equals("")||ageprivate.equals("1")) {
                                infopage.hideAge();
                            }
                            else {
                                agetext = "Age: " + age + " years";
                                infopage.setAge(agetext);
                            }

                            String mobiletext;
                            if (mobile == "null"||mobile.equals("")||mobileprivate.equals("1")) {
                                infopage.hideMobile();
                            }
                            else {
                                mobiletext = "Mobile: " + mobile;
                                infopage.setMobile(mobiletext);
                            }
                            String emailtext;
                            if (email == "null"||email.equals("")) {
                                infopage.hideEmail();
                            }
                            else {
                                emailtext = "Email: " + email;
                                infopage.setEmail(emailtext);
                            }
                            String gendertext;
                            if (gender == "null"||gender.equals("")||genderprivate.equals("1")) {
                                infopage.hideGender();
                            }
                            else {
                                gendertext = "Gender: " + gender;
                                infopage.setGender(gendertext);
                            }
                            String biotext;
                            if (bio == "null"||bio.equals("")||bioprivate.equals("1")) {
                                infopage.hideBio();
                            }
                            else {
                                biotext = "Bio: " + bio;
                                infopage.setBio(biotext);
                            }
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

        else{

            Intent intent=new Intent(activity,EditProfileOption.class);
            intent.putExtra("userId",userId);
            activity.startActivity(intent);
        }

    }

    @Override
    protected String doInBackground(String... params){
        String info="";
        if(params[0].equals("load")){

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

        }
        return info;

    }
}

