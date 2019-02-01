package com.nlapin.youthsongs.ui;

import android.os.Bundle;

import com.nlapin.youthsongs.BaseRouter;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.song.SongActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivityRouter extends BaseRouter {

    private AppCompatActivity activity;

    public MainActivityRouter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction =
                activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.mainFrame, fragment);
        transaction.commit();
    }

    public void openSongScreen(int songId) {
        Bundle bundle = new Bundle();
        bundle.putInt(SongActivity.Constants.SONG_ID, songId);
        openActivityWithExtra(new SongActivity(), bundle);
    }
}
