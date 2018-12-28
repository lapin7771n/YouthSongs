package com.nlapin.youthsongs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.nlapin.youthsongs.adapters.DataAdapter;
import com.nlapin.youthsongs.fragments.FeaturesFragment;
import com.nlapin.youthsongs.fragments.HomeFragment;
import com.nlapin.youthsongs.fragments.SettingsFragment;

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

        ButterKnife.bind(this);

        DataAdapter dataAdapter = new DataAdapter(this);

        homeFragment = new HomeFragment();
        featuresFragment = new FeaturesFragment();
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, fragment);
        transaction.commit();
    }
}
