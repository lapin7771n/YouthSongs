package com.nlapin.youthsongs.ui.songscreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.models.Song;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongActivity
        extends AppCompatActivity {

    public static final String SONG_NUMBER_KEY = "songNumber";

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.songTextTV)
    TextView songTextTV;
    @BindView(R.id.appBarCover)
    ImageView appBarCover;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private SongViewModel songViewModel;

    private static final int SONG_NOT_FOUND = -1;

    public static Intent start(Context from, int id) {
        Intent intent = new Intent(from, SongActivity.class);
        intent.putExtra(SONG_NUMBER_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        ButterKnife.bind(this);
        YouthSongsApp.getComponent().inject(this);

        setUpActionBar();
        songViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        int id = getIntent().getIntExtra(SONG_NUMBER_KEY, SONG_NOT_FOUND);

        songViewModel.getSongById(id).observe(this, this::parseSongToView);

        FirebaseAnalytics.getInstance(this);
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void parseSongToView(Song song) {
        String coverUrl = song.getCoverUrl();
        if (coverUrl == null || coverUrl.isEmpty()) {
            appBarCover.setImageResource(R.color.colorPrimaryDark);
        } else {
            Uri uri = Uri.parse(coverUrl);
            Glide.with(this)
                    .load(uri)
                    .into(appBarCover);
        }
        collapsingToolbar.setTitle(song.getName());
        toolbar.setSubtitle("Number " + song.getId());
        songTextTV.setText(song.getText());
    }
}
