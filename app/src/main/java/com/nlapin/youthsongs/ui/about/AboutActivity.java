package com.nlapin.youthsongs.ui.about;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.nlapin.youthsongs.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.backBtn) ImageButton backBtn;
    @BindView(R.id.aboutScreenPager) ViewPager aboutScreenPager;
    @BindView(R.id.aboutScreenTabs) TabLayout aboutScreenTabs;
    private AboutScreenRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTheme(R.style.AboutScreenTheme);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        router = new AboutScreenRouter(this);

        backBtn.setOnClickListener(v -> onBackPressed());

        aboutScreenPager.setAdapter(new AboutScreenPagerAdapter(getSupportFragmentManager()));

        aboutScreenTabs.setupWithViewPager(aboutScreenPager);
    }

    public AboutScreenRouter getRouter() {
        return router;
    }
}
