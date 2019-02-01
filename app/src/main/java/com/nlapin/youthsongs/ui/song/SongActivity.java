package com.nlapin.youthsongs.ui.song;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.data.SongsRepository;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SongActivity
        extends AppCompatActivity
        implements SongContract.View {

    private Unbinder unbinder;
    private SongContract.Presenter presenter;
    private ActionBar supportActionBar;
    private MenuItem favoriteBtn;

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.songTextTV) TextView songTextTV;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        unbinder = ButterKnife.bind(this);
        setUpAppBar();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int songID = extras.getInt(Constants.SONG_ID);

        presenter = new SongPresenter(getSongsRepository(), getFavoriteSongsRepository());
        presenter.attachView(this);
        presenter.loadSong(songID);

    }

    @Override
    public void showFavorite() {
        favoriteBtn.setChecked(true);
    }

    @Override
    public void hideFavorite() {
        favoriteBtn.setChecked(false);
    }

    @Override
    public void setToolbar(String songInfo) {
        supportActionBar.setTitle(songInfo);
    }

    @Override
    public void setSongText(String songText) {
        songTextTV.setText(Html.fromHtml(songText));
    }

    @Override
    public void setSongTextSize(int songTextSize) {

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
        presenter.setUpFavoriteBtn();
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
                } else {
                    item.setChecked(true);
                    item.setIcon(R.drawable.ic_fav_checked);
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
}
