package com.example.medregister.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.medregister.R;
import com.example.medregister.models.HealthyTip;

import java.util.List;

public class HealthyTipsAdapter extends PagerAdapter {

    List<HealthyTip> healthyTips;
    private Context context;

    public HealthyTipsAdapter(List<HealthyTip> healthyTips, Context context) {
        this.healthyTips = healthyTips;
        this.context = context;
    }

    @Override
    public int getCount() {
        return healthyTips.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate layout
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.list_healthy_tips, container, false);

        ImageView imageHealthyTip;
        TextView title;
        TextView tip;
        // Get references to UI widgets
        imageHealthyTip = itemView.findViewById(R.id.image_healthy_tip);
        title = itemView.findViewById(R.id.title_healthy_tip);
        tip = itemView.findViewById(R.id.tip_text);

        imageHealthyTip.setImageResource(healthyTips.get(position).getTipImage());
        title.setText(healthyTips.get(position).getTipTitle());
        tip.setText(healthyTips.get(position).getTipText());

        container.addView(itemView, 0);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
