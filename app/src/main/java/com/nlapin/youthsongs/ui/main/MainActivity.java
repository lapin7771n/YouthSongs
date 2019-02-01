package com.nlapin.youthsongs.ui.main;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.MainActivityRouter;
import com.nlapin.youthsongs.ui.favsonglist.FavoritesFragment;
import com.nlapin.youthsongs.ui.home.HomeFragment;
import com.nlapin.youthsongs.ui.settings.SettingsFragment;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends AppCompatActivity
        implements MainActivityContract.View {

    @BindView(R.id.bottomNavBar) BottomNavigationView bottomNavBar;
    @BindView(R.id.mainFrame) FrameLayout contentFrame;

    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private SettingsFragment settingsFragment;

    MainActivityRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //TODO create landscape orientation handling
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ButterKnife.bind(this);

        router = new MainActivityRouter(this);

        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        settingsFragment = new SettingsFragment();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
