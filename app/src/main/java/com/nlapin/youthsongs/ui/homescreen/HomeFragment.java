package com.nlapin.youthsongs.ui.homescreen;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.MainActivity;
import com.nlapin.youthsongs.ui.adapters.AuthorsSelectionPagerAdapter;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.ArrayList;
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
public class HomeFragment
        extends Fragment {

    private static final String TAG = "HomeFragment";
    private static final int VIEW_PAGER_PAGE_MARGIN = 10;
    private static final int VIEW_PAGER_LR_PADDING = 16;
    private static final int VIEW_PAGER_TB_PADDING = 0;

    @BindView(R.id.songRV)
    RecyclerView songRV;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.authorsSelectionsVP)
    ViewPager authorsSelectionsVP;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    /**
     * Adapter for all songs in MainScreen
     */
    private SongRVAdapter adapter;

    private CompositeDisposable disposables;
    private SearchView searchView;
    private RecyclerViewSkeletonScreen skeletonScreen;

    public HomeFragment() {
        //Need an empty constructor because of implementing Fragment
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        setupAuthorsSelectionRV();
        setUpToolbar();
        disposables = new CompositeDisposable();

        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);

        disposables.add(model.getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songs -> {
                    setUpSkeleton();
                    skeletonScreen.show();
                    adapter.setSongList(songs);
                    RecyclerView.Adapter songRVAdapter = songRV.getAdapter();

                    if (songRVAdapter != null) {
                        songRVAdapter.notifyDataSetChanged();
                    }

                    Log.d(TAG, "UI Song list updated!");
                    skeletonScreen.hide();
                }, throwable -> {
                    Toast.makeText(getContext(),
                            "An error occurred - " + throwable,
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error while loading songs:\n", throwable);
                }, () -> Log.i(TAG, "Songs loaded!")));

        setHasOptionsMenu(true);

        Log.d(TAG, "HomeFragment started!");
        return view;
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
                    .observeOn(Schedulers.io())
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .map(CharSequence::toString)
                    .doOnNext(result -> adapter.filter(result))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        RecyclerView.Adapter songRVAdapter = songRV.getAdapter();
                        if (songRVAdapter != null) {
                            songRVAdapter.notifyDataSetChanged();
                        }
                    }, throwable -> Log.e(TAG, "onCreateOptionsMenu: ", throwable)));
        }
        super.onCreateOptionsMenu(menu, inflater);

        Log.d(TAG, "HomeFragment menu created.");
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
    }

    /**
     * Disposing from all Observables
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
        Log.d(TAG, "HomeFragment destroyed");
    }

    /**
     * Setting up All songs UI
     */
    private void setUpRecyclerView() {
        adapter = new SongRVAdapter(new ArrayList<>(), (v, position) ->
                startActivity(SongActivity.start(getContext(), position)), getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        songRV.setLayoutManager(layoutManager);
        songRV.setAdapter(adapter);

        setUpSkeleton();
    }

    private void setUpSkeleton() {
        skeletonScreen = Skeleton.bind(songRV)
                .adapter(adapter)
                .load(R.layout.song_item)
                .show();
    }

    /**
     * Setting up authors selection UI
     */
    private void setupAuthorsSelectionRV() {
        PagerAdapter pagerAdapter = new AuthorsSelectionPagerAdapter(getChildFragmentManager(), 3);
        authorsSelectionsVP.setAdapter(pagerAdapter);
        authorsSelectionsVP.setPageMargin(dpToPx());
        authorsSelectionsVP.setPadding(VIEW_PAGER_LR_PADDING,
                VIEW_PAGER_TB_PADDING,
                VIEW_PAGER_LR_PADDING,
                VIEW_PAGER_TB_PADDING);
    }

    /**
     * Converting DP to screen pixels
     *
     * @return value in Pixels
     */
    private int dpToPx() {
        DisplayMetrics displayMetrics = Objects.requireNonNull(getActivity())
                .getResources()
                .getDisplayMetrics();

        return Math.round(VIEW_PAGER_PAGE_MARGIN * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}