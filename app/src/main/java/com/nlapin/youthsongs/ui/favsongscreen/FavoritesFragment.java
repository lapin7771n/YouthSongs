package com.nlapin.youthsongs.ui.favsongscreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment
        extends Fragment {

    private static final String TAG = "FavoritesFragment";

    @BindView(R.id.emptyBoxAnim)
    LottieAnimationView emptyBoxAnim;
    @BindView(R.id.emptyLabel)
    TextView emptyLabel;
    @BindView(R.id.favoriteSongsRV)
    RecyclerView favoriteSongsRV;
    @BindView(R.id.progressBar)
    ProgressBar loadingPB;
    private SongRVAdapter rvAdapter;
    private DisposableObserver<LiveData<List<Song>>> favoriteSongsSubscriber;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);

        loadingPB.setVisibility(View.VISIBLE);

        setUpRecyclerView();

        FavoritesViewModel viewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        viewModel.getAllFavoriteSongs().observe(this, favoriteSongs -> {
            favoriteSongsSubscriber = viewModel.getAllSong(favoriteSongs)
                    .subscribeWith(new DisposableObserver<LiveData<List<Song>>>() {
                        @Override
                        public void onNext(LiveData<List<Song>> listLiveData) {
                            listLiveData.observe(FavoritesFragment.this, songs -> {
                                if (songs == null || songs.isEmpty()) {
                                    emptyBoxAnim.setVisibility(View.VISIBLE);
                                    emptyLabel.setVisibility(View.VISIBLE);
                                    emptyBoxAnim.playAnimation();
                                    return;
                                }
                                emptyLabel.setVisibility(View.INVISIBLE);
                                emptyBoxAnim.setVisibility(View.INVISIBLE);
                                Log.d(TAG, "SONGS ARRIVED");

                                rvAdapter.setSongList(songs);
                                rvAdapter.notifyDataSetChanged();
                            });
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(),
                                    "An error occurred - " + e.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });


        loadingPB.setVisibility(View.INVISIBLE);
        return view;
    }

    private void setUpRecyclerView() {
        rvAdapter = new SongRVAdapter(new ArrayList<>(), (v, position) -> {
            startActivity(SongActivity.start(getContext(), position));
        });

        favoriteSongsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteSongsRV.setAdapter(rvAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteSongsSubscriber.dispose();
    }
}
