package com.nlapin.youthsongs.ui;

import android.content.SharedPreferences;
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
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.homescreen.HomeFragment;

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

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        FirebaseAnalytics.getInstance(this);
        YouthSongsApp.getComponent().inject(this);

//        ifFirstStartUp();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, new HomeFragment())
                .commit();
    }

    private void ifFirstStartUp() {
        SharedPreferences sharedPreferences = getSharedPreferences(APPLICATION_SETTINGS_SHARED_PEF,
                MODE_PRIVATE);
        boolean isFirstStartUp = sharedPreferences.getBoolean(IS_FIRST_START_UP_KEY, true);
        if (isFirstStartUp) {
            buildAlertDialog();
            alertDialog.show();
            songCloudRepository.provideAllSongs(
                    songs -> new Thread(() -> {
                        Song[] songArray = songs.toArray(new Song[0]);
                        songLocalRepository.insertAll(songArray);
                    }).start());
        }
    }

    private void buildAlertDialog() {
        alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Please wait...")
                .setMessage("We customize your application. This may take some time ...")
                .create();
    }
}
