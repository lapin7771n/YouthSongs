package com.nlapin.youthsongs.ui.about;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author nikita on 17,February,2019
 */
public class AboutScreenPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 2;

    private final NikitaAboutFragment nikitaAboutFragment;
    private final MarkAboutFragment markAboutFragment;


    public AboutScreenPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
        nikitaAboutFragment = new NikitaAboutFragment();
        markAboutFragment = new MarkAboutFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return nikitaAboutFragment;
            case 1:
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
