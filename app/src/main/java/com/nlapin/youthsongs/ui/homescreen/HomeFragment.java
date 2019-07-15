package com.nlapin.youthsongs.ui.homescreen;


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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.MainActivity;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    @BindView(R.id.songRV)
    ShimmerRecyclerView songRV;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    private SearchView searchView;

    private static final String TAG = "HomeFragment";

    /**
     * Adapter for all songs in MainScreen
     */
    private SongRVAdapter adapter;
    private CompositeDisposable disposables;

    public HomeFragment() {
        //Need an empty constructor because of implementing Fragment
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        setUpToolbar();
        disposables = new CompositeDisposable();

        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        disposables.add(viewModel.getSongs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songs -> {
                    songRV.hideShimmerAdapter();
                    adapter.setSongList(songs);
                }, throwable -> {
                    Toast.makeText(getContext(),
                            "An error occurred - " + throwable,
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error while loading songs:\n", throwable);
                }, () -> Log.i(TAG, "Songs loaded.")));

        setHasOptionsMenu(true);
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
                    .doOnEach(charSequenceNotification -> songRV.showShimmerAdapter())
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .subscribe(result -> {
                                adapter.filter(result.toString());
                                songRV.hideShimmerAdapter();
                                adapter.notifyDataSetChanged();
                            },
                            throwable -> Log.e(TAG, "Error occurred due to SearchView: ", throwable)));
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Search songs toolbar setting up
     */
    private void setUpToolbar() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolBar);
        ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.all_songs));
        }
        toolBar.setOnClickListener(v -> nestedScrollView.smoothScrollTo(0, songRV.getTop()));
    }

    /**
     * Disposing from all Observables
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    /**
     * Setting up All songs UI
     */
    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        songRV.setLayoutManager(layoutManager);
        adapter = new SongRVAdapter((v, position) -> startActivity(SongActivity.start(getContext(), position)), getActivity());
        songRV.setAdapter(adapter);
        songRV.showShimmerAdapter();
    }
}