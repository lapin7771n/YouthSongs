package com.nlapin.youthsongs.ui.songScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.models.Song;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nlapin.youthsongs.data.PreferenceHelper.Constants.APP_PREFERENCES;
import static com.nlapin.youthsongs.data.PreferenceHelper.Constants.PREF_TEXT_SIZE;

public class SongActivity
        extends AppCompatActivity
        implements SongContract.View {

    private Unbinder unbinder;
    private SongContract.Presenter presenter;
    private ActionBar supportActionBar;
    private MenuItem favoriteBtn;

    private Song song;
    private boolean isSongFavorite;

    private boolean songLoaded = false;

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.songTextTV) TextView songTextTV;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        unbinder = ButterKnife.bind(this);
        showProgressBar();

        setUpAppBar();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int songID = extras.getInt(Constants.SONG_ID);

        presenter = new SongPresenter(getSongsRepository(), getFavoriteSongsRepository());
        presenter.attachView(this);
        new LoadSongAsync(songID).execute();
    }

    public void showFavorite() {
        favoriteBtn.setChecked(true);
        favoriteBtn.setIcon(R.drawable.ic_fav_checked);
    }

    @Override
    public void setSong(Song song) {
        this.song = song;
    }

    public void setToolbar(String songInfo) {
        supportActionBar.setTitle(songInfo);
    }

    public void setSongText(String songText) {
        songTextTV.setText(Html.fromHtml(songText));

    }

    @Override
    public void setSongTextSize() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        int textSize = sharedPreferences.getInt(PREF_TEXT_SIZE, 20);
        songTextTV.setTextSize(textSize);
    }

    @Override
    public void setIsFavorite(boolean isFavorite) {
        isSongFavorite = isFavorite;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.song_menu, menu);
        favoriteBtn = menu.findItem(R.id.favoriteBtn);
        new LoadIsFavorite().execute();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.favoriteBtn:
                if (item.isChecked()) {
                    item.setChecked(false);
                    item.setIcon(R.drawable.ic_favorite_border);
                    Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show();
                } else {
                    item.setChecked(true);
                    item.setIcon(R.drawable.ic_fav_checked);
                    Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
                }
                presenter.favoriteClicked(item.isChecked());

        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpAppBar() {
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private SongsRepository getSongsRepository() {
        return ((YouthSongsApp) getApplicationContext())
                .getAppDI()
                .provideSongsRepository();
    }

    private FavoriteSongsRepository getFavoriteSongsRepository() {
        return ((YouthSongsApp) getApplicationContext())
                .getAppDI()
                .provideFavoriteSongRepository();
    }

    public interface Constants {
        String SONG_ID = "songID";
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadSongAsync extends AsyncTask<Void, Void, Void> {

        private int songID;

        LoadSongAsync(int songID) {
            this.songID = songID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            presenter.loadSong(songID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setToolbar(song.getId() + " " + song.getName());
            setSongTextSize();
            setSongText(song.getSongText());

            if (songLoaded) {
                hideProgressBar();
            } else {
                songLoaded = true;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadIsFavorite extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            presenter.setUpFavoriteBtn();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (isSongFavorite) {
                showFavorite();
            }
            if (songLoaded) {
                hideProgressBar();
            } else {
                songLoaded = true;
            }
        }
    }
}
