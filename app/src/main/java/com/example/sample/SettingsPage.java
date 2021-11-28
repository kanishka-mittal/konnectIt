package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsPage extends AppCompatActivity {
    TextView deleteProfile,logout;
    Switch hideAge,hideGender,hidePosts,hideInterests,hideMobile;
    LinearLayout Logout,Deleteprofile;
    Button savesettings;
    int hidepost,hideage,hidemobile,hidegender,hideinterests;
    int userId;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }

        ctx=this;
        deleteProfile=findViewById(R.id.settingsdeleteprofile);
        logout=findViewById(R.id.settingslogout);
        hideAge=findViewById(R.id.switchhideAge);
        hideGender=findViewById(R.id.switchhideGender);
        hideInterests=findViewById(R.id.switchhideInterests);
        hidePosts=findViewById(R.id.switchhidePosts);
        hideMobile=findViewById(R.id.switchhideMobile);
        Logout=findViewById(R.id.linearlogout);
        Deleteprofile=findViewById(R.id.lineardelete);


        String method="settingsload";
        SettingsBackgroundTasks bgTask=new SettingsBackgroundTasks(this,userId);
        bgTask.execute(method);
        savesettings=findViewById(R.id.btnsave);
        savesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hidePosts.isChecked()) {
                    hidepost = 1;
                }else{hidepost = 0;}

                if(hideAge.isChecked()) {
                    hideage = 1;
                }else{hideage = 0;}

                if(hideGender.isChecked()) {
                    hidegender = 1;
                }else{hidegender = 0;}

                if(hideMobile.isChecked()) {
                    hidemobile = 1;
                }else{hidemobile = 0;}

                if(hideInterests.isChecked()) {
                    hideinterests = 1;
                }else{hideinterests = 0;}


                String method1="settingssave";
                SettingsBackgroundTasks bgTask=new SettingsBackgroundTasks(ctx,userId,hideage, hideinterests,hidepost,hidemobile,hidegender);
                bgTask.execute(method1);
                Intent intent=new Intent(SettingsPage.this,Profile.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                finishAffinity();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth;
                mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent=new Intent(SettingsPage.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        Deleteprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference= firebaseDatabase.getReference();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid=user.getUid();
                user.delete();
                String method2="deleteprofile";
                SettingsBackgroundTasks bgTask=new SettingsBackgroundTasks(ctx,userId);
                bgTask.execute(method2);
            }
        });

    }
}