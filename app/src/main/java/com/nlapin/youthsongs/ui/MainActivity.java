package com.nlapin.youthsongs.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    private static final String TAG = "MainActivity";
    public static final String APPLICATION_SETTINGS_SHARED_PEF = "ApplicationSettings";
    public static final String IS_FIRST_START_UP_KEY = "isFirstStartUp";

    @Inject
    SongCloudRepository songCloudRepository;

    @Inject
    SongDao songLocalRepository;

    @BindView(R.id.bottomNavBar)
    BottomNavigationView bottomNavBar;
    @BindView(R.id.mainFrame)
    FrameLayout contentFrame;

    private MainActivityRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        FirebaseAnalytics.getInstance(this);
        YouthSongsApp.getComponent().inject(this);
        router = new MainActivityRouter(this);

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

    private void buildAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Please wait...")
                .setMessage("We customize your application. This may take some time ...")
                .create();
    }
}
