package com.nlapin.youthsongs.ui.songscreen;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nlapin.youthsongs.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class SongActivity
        extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.songTextTV)
    TextView songTextTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
    }
}
