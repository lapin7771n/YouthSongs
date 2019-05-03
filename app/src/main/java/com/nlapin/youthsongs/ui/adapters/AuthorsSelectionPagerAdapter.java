package com.nlapin.youthsongs.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nlapin.youthsongs.ui.authorsselection.AuthorsSelectionFragment;

public class AuthorsSelectionPagerAdapter extends FragmentPagerAdapter {

    private int pageCount;

    public AuthorsSelectionPagerAdapter(@NonNull FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AuthorsSelectionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.8f;
    }
}
