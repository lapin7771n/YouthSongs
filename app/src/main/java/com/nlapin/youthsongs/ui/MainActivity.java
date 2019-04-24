package com.nlapin.youthsongs.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.homescreen.HomeFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, new HomeFragment())
                .commit();

        buildAlertDialog();


//        SongRepository songRepository = YouthSongsApp.getComponent().getSongRepository();
//        songRepository.getAll().observe(MainActivity.this, songs -> {
//            new Thread(() -> {
//                NetworkService networkService = YouthSongsApp.getComponent().getNetworkService();
//                for (Song song : songs) {
//                    Log.d(TAG, "Song id to generate cover - " + song.getId());
//                    if (song.getCoverUrl() != null && !song.getCoverUrl().isEmpty())
//                        continue;
//
//                    Log.d(TAG, "Downloading image from unsplash.com");
//                    networkService.getSongCover(new Callback<UnsplashPhoto>() {
//                        @Override
//                        public void onResponse(Call<UnsplashPhoto> call, Response<UnsplashPhoto> response) {
//                            Log.d(TAG, "Response - " + response.toString());
//                            if (response.body() != null) {
//                                Log.d(TAG, "Responce body - " + response.body().toString());
//                                song.setCoverUrl(response.body().getUrls().getRaw());
//                                Log.d(TAG, "Updating image...");
//                                songRepository.update(song);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<UnsplashPhoto> call, Throwable t) {
//                            throw new RuntimeException();
//                        }
//                    });
//                }
//            }).start();
//
//        alertDialog.dismiss();
//    });


        FirebaseAnalytics.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        alertDialog.dismiss();
    }

    private void buildAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Please wait...")
                .setMessage("We are setting your app up. This will take a time...")
                .create();
    }
}
