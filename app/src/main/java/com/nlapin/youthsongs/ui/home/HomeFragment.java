package com.nlapin.youthsongs.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
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

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);
        router = new MainActivityRouter(((MainActivity) getActivity()));

        if (savedInstanceState == null) {
            presenter = new HomePresenter(getSongsRepository());
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        presenter.attachView(this);

        songRV.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.loadData();

        return view;
    }

    @Override
    public void showSongs(List<Song> songList) {
        SongRVAdapter songRVAdapter = new SongRVAdapter(songList, (v, position) -> {

            Song clickedSong = presenter.onItemClick(position);
            if (clickedSong.getName() != null) {
                router.openSongScreen(position);
            }
        });
        songRV.setAdapter(songRVAdapter);
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
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private SongsRepository getSongsRepository() {
        return ((YouthSongsApp) getActivity().getApplicationContext())
                .getAppDI()
                .provideSongsRepository();
    }
}
