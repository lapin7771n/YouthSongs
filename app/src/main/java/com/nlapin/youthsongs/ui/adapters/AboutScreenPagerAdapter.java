package com.nlapin.youthsongs.ui.adapters;

import com.nlapin.youthsongs.ui.AppAboutFragment;
import com.nlapin.youthsongs.ui.MarkAboutFragment;
import com.nlapin.youthsongs.ui.NikitaAboutFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author nikita on 17,February,2019
 */
public class AboutScreenPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 3;

    private final NikitaAboutFragment nikitaAboutFragment;
    private final MarkAboutFragment markAboutFragment;
    private final AppAboutFragment appAboutFragment;


    AboutScreenPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
        nikitaAboutFragment = new NikitaAboutFragment();
        markAboutFragment = new MarkAboutFragment();
        appAboutFragment = new AppAboutFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return appAboutFragment;
            case 1:
                return nikitaAboutFragment;
            case 2:
                return markAboutFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
