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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private int userId,accessedByUser;
    private ProfileViewsAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btnFriendsList;
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Tabbed Activity
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ProfileViewsAdapter(getSupportFragmentManager());

        //viewPagerAdapter
        Profile_info infopage = new Profile_info();

        viewPagerAdapter.AddFragment(infopage, "");
        viewPagerAdapter.AddFragment(new Profile_post(), "");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info);
        tabLayout.getTabAt(1).setIcon(R.drawable.posticon);

        String method="load";
        TextView Fullname = findViewById(R.id.fullname);
        TextView Username = findViewById(R.id.username);
        ProfileBackgroundTask bgTask=new ProfileBackgroundTask(this,userId, Fullname, Username, infopage);
        bgTask.execute(method);
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);
        if(accessedByUser==userId){
            //BOTTOMBAR NAVIGATION


            bottomNavigationView.setSelectedItemId(R.id.myprofile);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.news:
                            Intent intent=new Intent(getApplicationContext(),NewsFeed.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            return true;
                    }switch (item.getItemId()){
                        case R.id.notifs:
                            Intent intent=new Intent(getApplicationContext(),Notifications.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            return true;

                    }switch (item.getItemId()){
                        case R.id.friendRequests:
                            Intent intent=new Intent(getApplicationContext(),FriendRequests.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            overridePendingTransition(0,0);
                            return true;

                    }
                    return false;
                }
            });
        }else{
            bottomNavigationView.setVisibility(View.GONE);
        }
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
            startActivity(intent);
        }

        return true;
    }
}