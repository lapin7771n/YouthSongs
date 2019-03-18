package com.nlapin.youthsongs.ui;

import android.os.Bundle;

import com.nlapin.youthsongs.BaseRouter;
import com.nlapin.youthsongs.ui.about.AboutActivity;
import com.nlapin.youthsongs.ui.songScreen.SongActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivityRouter extends BaseRouter {

    private AppCompatActivity activity;

    public MainActivityRouter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    public void switchFragment(Fragment fragment) {
        openFragment(fragment);
    }

    public void openSongScreen(int songId) {
        Bundle bundle = new Bundle();
        bundle.putInt(SongActivity.Constants.SONG_ID, songId);
        openActivityWithExtra(new SongActivity(), bundle);
    }

    public void openAboutAppScreen(){
        openActivity(new AboutActivity());
    }
}
