package com.nlapin.youthsongs.ui.favsonglist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.nlapin.youthsongs.PresenterManager;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.FavoriteSongsRepository;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.home.SongRVAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            presenter = new FavoritesPresenter(getFavoriteSongsRepository());
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        presenter.attachView(this);

        presenter.loadData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        emptyBoxAnim.playAnimation();
    }

    @Override
    public void showFavoriteSongs(List<Song> favoriteSongs) {
        favoriteSongsRV.setVisibility(View.VISIBLE);
        SongRVAdapter adapter = new SongRVAdapter(favoriteSongs, (v, position) -> {
            Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        });
        favoriteSongsRV.setAdapter(adapter);
    }

    @Override
    public void showEmpty() {
        emptyBoxAnim.setVisibility(View.VISIBLE);
        emptyLabel.setVisibility(View.VISIBLE);
        emptyBoxAnim.playAnimation();
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
}
