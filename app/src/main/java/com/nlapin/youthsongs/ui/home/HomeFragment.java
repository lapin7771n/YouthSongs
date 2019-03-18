package com.nlapin.youthsongs.ui.home;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nlapin.youthsongs.PresenterManager;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongsRepository;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.MainActivityRouter;
import com.nlapin.youthsongs.ui.main.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment
        extends Fragment
        implements HomeContract.View {

    @BindView(R.id.songRV) RecyclerView songRV;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private HomePresenter presenter;
    private MainActivityRouter router;
    private SongRVAdapter songRVAdapter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);
        showProgressBar();

        router = new MainActivityRouter(((MainActivity) getActivity()));

        if (savedInstanceState == null) {
            presenter = new HomePresenter(getSongsRepository());
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        presenter.attachView(this);

        songRV.setLayoutManager(new LinearLayoutManager(getContext()));
        new LoadSongs().execute();
        return view;
    }

    private void setUpSearch(MenuItem searchMenuItem) {
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search song");
        searchView.setOnQueryTextListener(new SongOnQueryTextListener());
        searchView.setIconified(false);
        searchView.clearFocus();
    }

    @Override
    public void showSongs(List<Song> songList) {
        songRVAdapter = new SongRVAdapter(songList, (v, clickedSongID) -> {

            Song clickedSong = presenter.onItemClick(clickedSongID);
            if (clickedSong.getName() != null) {
                router.openSongScreen(clickedSongID);
            }
        });
    }

    @Override
    public void showProgressBar() {
        songRV.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        songRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.searchBtn);
        setUpSearch(item);
    }

    private class SongOnQueryTextListener
            implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            new FilterAsync(newText).execute();
            return true;
        }
    }

    private SongsRepository getSongsRepository() {
        return ((YouthSongsApp) getActivity().getApplicationContext())
                .getAppDI()
                .provideSongsRepository();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadSongs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            presenter.loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            songRV.setAdapter(songRVAdapter);
            hideProgressBar();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FilterAsync extends AsyncTask<Void, Void, Void> {

        private String newText;


        public FilterAsync(String newText) {
            this.newText = newText;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            songRVAdapter.filter(newText);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            songRVAdapter.notifyDataSetChanged();
        }
    }
}
