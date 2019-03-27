package com.nlapin.youthsongs.ui;

import android.app.ActivityOptions;
import android.os.Bundle;

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

    public void openSongScreen(int songId, ActivityOptions activityOptions) {
        Bundle bundle = new Bundle();
        bundle.putInt(SongActivity.Constants.SONG_ID, songId);

        //If android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP
        //activity options will be not null
        if (activityOptions != null) {
            openActivityWithExtra(new SongActivity(), bundle, activityOptions);
        } else {
            openActivityWithExtra(new SongActivity(), bundle);
        }
    }

    public void openAboutAppScreen(ActivityOptions activityOptions) {
        openActivityWithTransition(new AboutActivity(), activityOptions);
    }
}
