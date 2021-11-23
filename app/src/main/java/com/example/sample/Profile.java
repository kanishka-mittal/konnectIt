package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private int userId;
    private ProfileViewsAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId=extras.getInt("userId");
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        TextView Fullname = findViewById(R.id.fullname);
        TextView Username = findViewById(R.id.username);
        ProfileBackgroundTask bgTask=new ProfileBackgroundTask(this,userId, Fullname, Username, infopage);
        bgTask.execute();
    }

}