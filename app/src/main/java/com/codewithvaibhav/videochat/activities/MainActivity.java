package com.codewithvaibhav.videochat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codewithvaibhav.videochat.R;
import com.codewithvaibhav.videochat.databinding.ActivityMainBinding;
import com.codewithvaibhav.videochat.models.User;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.codewithvaibhav.videochat.activities.RewardActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    long coinss;
    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private int requestCode = 1;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        database.getReference().child("profiles")
                .child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        user = snapshot.getValue(User.class);
                        coinss = user.getCoins();

                        binding.coinss.setText("You have: " + coinss);

                        Glide.with(MainActivity.this)
                                .load(user.getProfile())
                                .into(binding.profilePicture);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionsGranted()){
                    if(coinss > 5){
                        coinss = coinss-5;
                        database.getReference().child("profiles")
                                .child(currentUser.getUid())
                                .child("coins")
                                .setValue(coinss);
                        Intent intent = new Intent(MainActivity.this,ConnectingActivity.class);
                        intent.putExtra("profile",user.getProfile());
                        startActivity(intent);
//                        startActivity(new Intent(MainActivity.this, ConnectingActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Insufficient Coins", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    askPermissions();
                }
            }
        });
        

        binding.rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RewardActivity.class));
            }
        });
    }

    void askPermissions(){
        ActivityCompat.requestPermissions(this,permissions,requestCode);
    }

    private boolean isPermissionsGranted(){
        for (String permission: permissions){
            if (ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}