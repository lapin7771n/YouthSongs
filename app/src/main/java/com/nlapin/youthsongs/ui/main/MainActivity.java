package com.nlapin.youthsongs.ui.main;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

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
    private static int latestFragment;

    private MainActivityRouter router;
    private MenuItem searchItem;

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

        bottomNavBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeTab:
                    router.switchFragment(homeFragment);
                    latestFragment = Constants.HOME_FRAGMENT;
                    return true;

                case R.id.favoriteTab:
                    router.switchFragment(favoritesFragment);
                    latestFragment = Constants.FAVORITES_FRAGMENT;
                    return true;

                case R.id.settingsTab:
                    router.switchFragment(settingsFragment);
                    latestFragment = Constants.SETTINGS_FRAGMENT;
                    return true;

                default:
                    return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (latestFragment) {
            case Constants.HOME_FRAGMENT:
                router.switchFragment(homeFragment);
                break;
            case Constants.FAVORITES_FRAGMENT:
                router.switchFragment(favoritesFragment);
                break;
            case Constants.SETTINGS_FRAGMENT:
                router.switchFragment(settingsFragment);
                break;
            default:
                router.switchFragment(homeFragment);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        searchItem = menu.findItem(R.id.searchBtn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    public MainActivityRouter getRouter() {
        return router;
    }

    public MenuItem getSearchItem() {
        return searchItem;
    }

    interface Constants {

        //Fragments constants
        int HOME_FRAGMENT = 1;
        int FAVORITES_FRAGMENT = 2;
        int SETTINGS_FRAGMENT = 3;
    }
}
