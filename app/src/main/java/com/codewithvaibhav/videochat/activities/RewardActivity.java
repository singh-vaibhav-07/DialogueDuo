package com.codewithvaibhav.videochat.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codewithvaibhav.videochat.R;
import com.codewithvaibhav.videochat.databinding.ActivityRewardBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardActivity extends AppCompatActivity {

    ActivityRewardBinding binding;
    private RewardedAd mRewardedAd;
    FirebaseDatabase database;
    String currentUid;
    int coins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRewardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        currentUid = FirebaseAuth.getInstance().getUid();

        loadAd();

        database.getReference().child("profiles")
                .child(currentUid)
                .child("coins")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        coins = snapshot.getValue(Integer.class);
                        binding.coins.setText(String.valueOf(coins));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = RewardActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd();
                            coins = coins + 200;
                            database.getReference().child("profiles")
                                    .child(currentUid)
                                    .child("coins")
                                    .setValue(coins);
                            binding.vid1icon.setImageResource(R.drawable.check);

                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {

                }
            }
        });
        binding.video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = RewardActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd();
                            coins = coins + 300;
                            database.getReference().child("profiles")
                                    .child(currentUid)
                                    .child("coins")
                                    .setValue(coins);
                            binding.vid2icon.setImageResource(R.drawable.check);

                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {

                }
            }
        });
        binding.video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = RewardActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd();
                            coins = coins + 400;
                            database.getReference().child("profiles")
                                    .child(currentUid)
                                    .child("coins")
                                    .setValue(coins);
                            binding.vid3icon.setImageResource(R.drawable.check);

                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {

                }
            }
        });
        binding.video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = RewardActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd();
                            coins = coins + 500;
                            database.getReference().child("profiles")
                                    .child(currentUid)
                                    .child("coins")
                                    .setValue(coins);
                            binding.vid4icon.setImageResource(R.drawable.check);

                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {

                }
            }
        });
        binding.video5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = RewardActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            loadAd();
                            coins = coins + 1000;
                            database.getReference().child("profiles")
                                    .child(currentUid)
                                    .child("coins")
                                    .setValue(coins);
                            binding.vid5icon.setImageResource(R.drawable.check);

                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {

                }
            }
        });
    }

    void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }

}