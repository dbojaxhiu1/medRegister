package com.example.medregister;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.medregister.adapters.HealthyTipsAdapter;
import com.example.medregister.models.HealthyTip;

import java.util.ArrayList;
import java.util.List;

public class HealthyTipsActivity extends AppCompatActivity {

    private static final String TAG = "HealthyTipsActivity";

    ViewPager viewPagerTip;
    HealthyTipsAdapter healthyTipsAdapter;
    List<HealthyTip> healthyTips = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthytips);
        Log.d(TAG, "onCreate: started.");
        setTitle(getString(R.string.healthy_tips_title));

        viewPagerTip = findViewById(R.id.viewPager);

        healthyTips.add(new HealthyTip(R.drawable.healthy_food, getString(R.string.healthy_tip_1_title), getString(R.string.tip_1)));
        healthyTips.add(new HealthyTip(R.drawable.salt, getString(R.string.healthy_tip_2_title), getString(R.string.tip_2)));
        healthyTips.add(new HealthyTip(R.drawable.fats, getString(R.string.healthy_tip_3_title), getString(R.string.tip_3)));
        healthyTips.add(new HealthyTip(R.drawable.alcohol, getString(R.string.healthy_tip_4_title), getString(R.string.tip_4)));

        healthyTipsAdapter = new HealthyTipsAdapter(healthyTips, HealthyTipsActivity.this);
        viewPagerTip.setAdapter(healthyTipsAdapter);
        viewPagerTip.setPadding(130, 0, 130, 0);

    }
}
