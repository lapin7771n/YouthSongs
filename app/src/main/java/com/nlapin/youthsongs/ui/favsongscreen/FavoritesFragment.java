package com.nlapin.youthsongs.ui.favsongscreen;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment
        extends Fragment {

    private static final String TAG = "FavoritesFragment";
    private static final int FILTER_DELAY = 300;

    @BindView(R.id.emptyBoxAnim)
    LottieAnimationView emptyBoxAnim;
    @BindView(R.id.emptyLabel)
    TextView emptyLabel;
    @BindView(R.id.favoriteSongsRV)
    ShimmerRecyclerView favoriteSongsRV;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    private SongRVAdapter rvAdapter;
    private FavoritesViewModel viewModel;
    private CompositeDisposable disposables;
    private SearchView searchView;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteSongsRV.showShimmerAdapter();
        setUpRecyclerView();
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        disposables = new CompositeDisposable();

        toolBar.setTitle(getString(R.string.favoriteSongsLabel));

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolBar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBtn);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager != null
                    ? searchManager.getSearchableInfo(getActivity().getComponentName())
                    : null);

            disposables.add(RxSearchView.queryTextChanges(searchView)
                    .doOnEach(charSequenceNotification -> favoriteSongsRV.showShimmerAdapter())
                    .observeOn(Schedulers.io())
                    .debounce(FILTER_DELAY, TimeUnit.MILLISECONDS)
                    .map(CharSequence::toString)
                    .doOnNext(result -> rvAdapter.filter(result))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        RecyclerView.Adapter songRVAdapter = favoriteSongsRV.getAdapter();
                        if (songRVAdapter != null) {
                            songRVAdapter.notifyDataSetChanged();
                        }
                        favoriteSongsRV.hideShimmerAdapter();
                    }, throwable -> Log.e(TAG, "Error filtering data: ", throwable)));
        }
        super.onCreateOptionsMenu(menu, inflater);

        Log.d(TAG, "HomeFragment menu created.");
    }

    /**
     * Initializing recyclerView
     */
    private void setUpRecyclerView() {
        rvAdapter = new SongRVAdapter((v, position) ->
                startActivity(SongActivity.start(getContext(), position)), getActivity());

        favoriteSongsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteSongsRV.setAdapter(rvAdapter);
        favoriteSongsRV.hideShimmerAdapter();
    }

    /**
     * Unsubscribe from all observers
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    /**
     * Here we are setting up our RecyclerView and update it
     */
    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllFavoriteSongs().observe(this, favoriteSongs ->
                disposables.add(viewModel.getAllSong(favoriteSongs)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songs -> {

                            if (songs == null || songs.isEmpty()) {
                                emptyBoxAnim.setVisibility(View.VISIBLE);
                                emptyLabel.setVisibility(View.VISIBLE);
                                emptyBoxAnim.playAnimation();
                                return;
                            }

                            emptyLabel.setVisibility(View.INVISIBLE);
                            emptyBoxAnim.setVisibility(View.INVISIBLE);

                            Objects.requireNonNull(((AppCompatActivity)
                                    Objects.requireNonNull(getActivity()))
                                    .getSupportActionBar())
                                    .setSubtitle(String.format(getString(R.string.favoriteSongsCount),
                                            songs.size()));

                            rvAdapter.setSongList(songs);
                            rvAdapter.notifyDataSetChanged();

                        }, throwable -> Log.e(TAG, "Error while loading favorite songs!",
                                throwable))));
    }
}
