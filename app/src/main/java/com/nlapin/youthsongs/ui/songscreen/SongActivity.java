package com.nlapin.youthsongs.ui.songscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.remote.AuthorsSelectionRepository;
import com.nlapin.youthsongs.models.FavoriteSong;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.AboutScreenRouter;
import com.nlapin.youthsongs.ui.GlideApp;
import com.nlapin.youthsongs.utils.SongUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscribers.BlockingBaseSubscriber;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class SongActivity
        extends AppCompatActivity {

    private static final String TAG = "SongActivity";


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
    @BindView(R.id.actionBarSubtitle)
    TextView actionBarSubtitle;

    private SongViewModel songViewModel;

    @Inject
    AuthorsSelectionRepository authorsSelectionRepository;

    private int songId;
    private Disposable songSubscriber;
    private FirebaseAnalytics firebaseAnalytics;

    private static final int SONG_NOT_FOUND = -1;
    private static final String SONG_NUMBER_KEY = "songNumber";
    private static final String DEFAULT_IMAGE = "https://images.unsplash.com/photo-1523540500678-a7637c980022?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1267&q=80";

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
        songId = getIntent().getIntExtra(SONG_NUMBER_KEY, SONG_NOT_FOUND);

        songSubscriber = songViewModel.getSongById(songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::parseSongToView);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        authorsSelectionRepository.addViewOnSong(songId);
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        songSubscriber.dispose();
    }

    private void parseSongToView(Song song) {
        String cover = song.getCoverUrlSmall();
        if (cover != null) {
            GlideApp.with(this)
                    .load(cover)
                    .centerCrop()
                    .transform(new BlurTransformation(25, 3))
                    .into(appBarCover);

        } else {
            GlideApp.with(this)
                    .load(DEFAULT_IMAGE)
                    .centerCrop()
                    .transform(new BlurTransformation())
                    .into(appBarCover);
        }

        collapsingToolbar.setTitle(song.getName());
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolBarTitle);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolBarTitle);
        actionBarSubtitle.setText(getString(R.string.number) + " " + song.getId());
        int fontSize = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.font_size_pref), "20"));

        boolean withCords = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(getString(R.string.is_chords_show_pref), false);

        songTextTV.setText(HtmlCompat.fromHtml(SongUtils.getSongTextFormated(song, withCords),
                HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
        songTextTV.setTextSize(fontSize);

        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, songId);
        bundle.getString(FirebaseAnalytics.Param.ITEM_NAME, song.getName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.song_menu, menu);
        songViewModel.isFavorite(songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BlockingBaseSubscriber<FavoriteSong>() {
                    @Override
                    public void onNext(FavoriteSong favoriteSong) {
                        if (favoriteSong != null) {
                            MenuItem item = menu.findItem(R.id.favoriteBtn);
                            item.setChecked(true);
                            item.setIcon(R.drawable.ic_fav_checked);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favoriteBtn:
                if (item.isChecked()) {
                    item.setChecked(false);
                    item.setIcon(R.drawable.ic_favorite_border);
                    songViewModel.deleteFromFavorites(songId);

                } else {
                    item.setChecked(true);
                    item.setIcon(R.drawable.ic_fav_checked);
                    songViewModel.saveToFavorites(songId);
                    authorsSelectionRepository.addFavOnSong(songId);
                }
                break;

            case R.id.mistakeInTheText:
                new AboutScreenRouter(this).openEmail(AboutScreenRouter.DeveloperID.Nikita,
                        String.format(this.getString(R.string.mistake_message_body), songId));
                break;

            case R.id.shareSong:
                // TODO: 4/25/2019 Implement this feature
                Toast.makeText(this, getString(R.string.featureNotSupported),
                        Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
