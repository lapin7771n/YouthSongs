package com.nlapin.youthsongs.ui.favsonglist;


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
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nlapin.youthsongs.PresenterManager;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.MainActivityRouter;
import com.nlapin.youthsongs.ui.home.SongRVAdapter;
import com.nlapin.youthsongs.ui.main.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment
        extends Fragment
        implements FavoritesContract.View {

    @BindView(R.id.emptyBoxAnim) LottieAnimationView emptyBoxAnim;
    @BindView(R.id.emptyLabel) TextView emptyLabel;
    @BindView(R.id.favoriteSongsRV) RecyclerView favoriteSongsRV;
    @BindView(R.id.progressBar) ProgressBar loadingPB;

    private FavoritesPresenter presenter;
    private MainActivityRouter router;

    private SongRVAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ButterKnife.bind(this, view);
        showProgressBar();

        if (savedInstanceState == null) {
            presenter = new FavoritesPresenter(getFavoriteSongsRepository());
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        presenter.attachView(this);
        favoriteSongsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        new LoadFavoriteSongsAsync().execute();

        router = ((MainActivity) getActivity()).getRouter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        emptyBoxAnim.playAnimation();
        presenter.loadData();
    }

    @Override
    public void showFavoriteSongs(List<Song> favoriteSongs) {
        favoriteSongsRV.setVisibility(View.VISIBLE);
        adapter = new SongRVAdapter(favoriteSongs, (v, favoriteSongID) -> {
            router.openSongScreen(favoriteSongID);
        });
    }

    @Override
    public void showEmpty() {
        favoriteSongsRV.setVisibility(View.INVISIBLE);
        emptyBoxAnim.setVisibility(View.VISIBLE);
        emptyLabel.setVisibility(View.VISIBLE);
        emptyBoxAnim.playAnimation();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.searchBtn);
        setUpSearch(item);
    }

    private void setUpSearch(MenuItem searchMenuItem) {
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search song");
        searchView.setOnQueryTextListener(new SongOnQueryTextListener());
        searchView.setIconified(false);
        searchView.clearFocus();
    }

    @Override
    public void showProgressBar() {
        loadingPB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        loadingPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    private FavoriteSongsRepository getFavoriteSongsRepository() {
        return ((YouthSongsApp) getActivity().getApplication())
                .getAppDI()
                .provideFavoriteSongRepository();
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

    @SuppressLint("StaticFieldLeak")
    private class LoadFavoriteSongsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            presenter.loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            favoriteSongsRV.setAdapter(adapter);
            hideProgressBar();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FilterAsync extends AsyncTask<Void, Void, Void> {

        private String newText;


        FilterAsync(String newText) {
            this.newText = newText;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            adapter.filter(newText);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            hideProgressBar();
        }
    }
}
