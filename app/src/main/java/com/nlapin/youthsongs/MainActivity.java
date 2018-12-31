package com.nlapin.youthsongs;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlapin.youthsongs.adapters.DataAdapter;
import com.nlapin.youthsongs.fragments.FeaturesFragment;
import com.nlapin.youthsongs.fragments.HomeFragment;
import com.nlapin.youthsongs.fragments.SettingsFragment;
import com.nlapin.youthsongs.fragments.SongFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavBar) BottomNavigationView bottomNavBar;
    @BindView(R.id.mainFrame) FrameLayout mainFrame;

    private HomeFragment homeFragment;
    private FeaturesFragment featuresFragment;
    private SettingsFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ButterKnife.bind(this);

        new DataAdapter(this);

        homeFragment = new HomeFragment();
        homeFragment.setParentActivity(MainActivity.this);
        featuresFragment = new FeaturesFragment();
        featuresFragment.setParentActivity(MainActivity.this);
        settingsFragment = new SettingsFragment();

        setFragment(homeFragment);

        bottomNavBar.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener());
    }

    private class OnNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.homeTab:
                    setFragment(homeFragment);
                    return true;

                case R.id.featuredTab:
                    setFragment(featuresFragment);
                    return true;

                case R.id.settingsTab:
                    setFragment(settingsFragment);
                    return true;

                default:
                    return false;
            }
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, fragment);
        if (fragment instanceof SongFragment) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
