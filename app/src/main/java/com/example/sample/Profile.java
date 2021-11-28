package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private int userId,accessedByUser;
    private ProfileViewsAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btnFriendsList;
    CircleImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId=extras.getInt("userId");
            accessedByUser=extras.getInt("accessedByUser");
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView head = findViewById(R.id.toolbarTextView);
        setSupportActionBar(toolbar);
        if(userId==accessedByUser){
            head.setText("My Profile");
        }
        else head.setText("User Profile");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Tabbed Activity
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ProfileViewsAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId );
        bundle.putInt("accessedByUser", accessedByUser);
        //viewPagerAdapter
        Profile_info infopage = new Profile_info();
        Profile_post postpage = new Profile_post();
        postpage.setArguments(bundle);
        infopage.setArguments(bundle);
        viewPagerAdapter.AddFragment(infopage, "");
        viewPagerAdapter.AddFragment(postpage, "");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info);
        tabLayout.getTabAt(1).setIcon(R.drawable.posticon);

        userPic=findViewById(R.id.userpic);
        Glide.with(this).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(userId)+".png").into(userPic);
//        String method="load";
//
//        userPic=findViewById(R.id.userpic);
//        Glide.with(this).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(userId)+".png").into(userPic);
//
//        TextView Fullname = findViewById(R.id.fullname);
//        TextView Username = findViewById(R.id.username);
//        ProfileBackgroundTask bgTask=new ProfileBackgroundTask(this,userId, Fullname, Username, infopage);
//        bgTask.execute(method);
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);
        if(true){
            //BOTTOMBAR NAVIGATION


            bottomNavigationView.setSelectedItemId(R.id.myprofile);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.myprofile:
                            Intent intent=new Intent(getApplicationContext(),Profile.class);
                            intent.putExtra("userId",accessedByUser);
                            intent.putExtra("accessedByUser", accessedByUser);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            return true;
                    }

                    switch (item.getItemId()){
                        case R.id.news:
                            Intent intent=new Intent(getApplicationContext(),NewsFeed.class);
                            intent.putExtra("userId",accessedByUser);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finish();
                            return true;
                    }switch (item.getItemId()){
                        case R.id.notifs:
                            Intent intent=new Intent(getApplicationContext(),Notifications.class);
                            intent.putExtra("userId",accessedByUser);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finish();
                            return true;

                    }switch (item.getItemId()){
                        case R.id.friendRequests:
                            Intent intent=new Intent(getApplicationContext(),FriendRequests.class);
                            intent.putExtra("userId",accessedByUser);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            finish();
                            return true;

                    }
                    return false;
                }
            });
        }else{
            bottomNavigationView.setVisibility(View.GONE);
        }

        invalidateOptionsMenu();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profilemenu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        MenuItem item = menu.findItem(R.id.friendicon);
        if(accessedByUser!=userId){
            item.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if(item_id == R.id.editprofile){
            String method2="edit";
            TextView Fullname = findViewById(R.id.fullname);
            TextView Username = findViewById(R.id.username);
            Profile_info infopage = new Profile_info();
            ProfileBackgroundTask bgTask=new ProfileBackgroundTask(this,userId, Fullname, Username, infopage);
            bgTask.execute(method2);
        }

        if(item_id == R.id.friendicon){
            Intent intent=new Intent(Profile.this,Friends.class);
            intent.putExtra("userId",userId);
            intent.putExtra("accessedByUser",accessedByUser);
            startActivity(intent);
            overridePendingTransition(0, 0);

        }
        if(item_id == R.id.settings){
            Intent intent=new Intent(this,SettingsPage.class);
            intent.putExtra("userId",userId);
            this.startActivity(intent);
            overridePendingTransition(0, 0);
        }


        return true;
    }
}