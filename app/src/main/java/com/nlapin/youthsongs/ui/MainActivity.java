package com.nlapin.youthsongs.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.local.SongDao;
import com.nlapin.youthsongs.data.remote.SongCloudRepository;
import com.nlapin.youthsongs.ui.favsongscreen.FavoritesFragment;
import com.nlapin.youthsongs.ui.homescreen.HomeFragment;
import com.nlapin.youthsongs.ui.settingsscreen.SettingsFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends AppCompatActivity {

    @Inject
    SongCloudRepository songCloudRepository;

    @Inject
    SongDao songLocalRepository;

    @BindView(R.id.bottomNavBar)
    BottomNavigationView bottomNavBar;
    @BindView(R.id.mainFrame)
    FrameLayout contentFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        FirebaseAnalytics.getInstance(this);
        YouthSongsApp.getComponent().inject(this);
        MainActivityRouter router = new MainActivityRouter(this);

        HomeFragment homeFragment = new HomeFragment();
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        router.switchFragment(homeFragment);
        bottomNavBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeTab:
                    router.switchFragment(homeFragment);
                    return true;

                case R.id.favoriteTab:
                    router.switchFragment(favoritesFragment);
                    return true;

                case R.id.settingsTab:
                    router.switchFragment(settingsFragment);
                    return true;

                default:
                    return false;
            }
        });
    }
}
