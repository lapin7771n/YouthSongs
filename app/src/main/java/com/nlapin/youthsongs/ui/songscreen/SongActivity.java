package com.nlapin.youthsongs.ui.songscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.Song;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongActivity
        extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.songTextTV)
    TextView songTextTV;

    public static final String SONG_NUMBER_KEY = "songNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        ButterKnife.bind(this);
        setUpActionBar();

        SongViewModel songViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        int id = getIntent().getIntExtra(SONG_NUMBER_KEY, -1);

        LiveData<Song> songLD = songViewModel.getSongById(id);
        songLD.observe(this, song -> {
            songTextTV.setText(song.getText());
            getSupportActionBar().setTitle(song.getName());
        });
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public static Intent start(Context from, int id) {
        Intent intent = new Intent(from, SongActivity.class);
        intent.putExtra(SONG_NUMBER_KEY, id);
        return intent;
    }
}
