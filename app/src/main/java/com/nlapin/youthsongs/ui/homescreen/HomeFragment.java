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

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.MainActivity;
import com.nlapin.youthsongs.ui.adapters.AuthorsSelectionPagerAdapter;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment
        extends Fragment {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.songRV)
    RecyclerView songRV;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.authorsSelectionsVP)
    ViewPager authorsSelectionsVP;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    private PagerAdapter pagerAdapter;

    /**
     * Adapter for all songs in MainScreen
     */
    private SongRVAdapter adapter;
    private Disposable disposable;
    private SearchView searchView;
    private SearchView.OnQueryTextListener onQueryTextListener;

    public HomeFragment() {
        //Need an empty constructor because of implementing Fragment
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        setupAuthorsSelectionRV();
        setUpToolbar();

        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);

        disposable = model.getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songs -> {
                    ((SongRVAdapter) songRV.getAdapter()).setSongList(songs);
                    songRV.getAdapter().notifyDataSetChanged();
                    Log.d(TAG, "UI updated! |Song list| - ");
                }, throwable -> {
                    Toast.makeText(getContext(),
                            "An error occurred - " + throwable,
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error while loading songs:\n", throwable);
                }, () -> Log.i(TAG, "Songs loaded!"));

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBtn);
        SearchManager searchManager = (SearchManager) getActivity()
                .getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            onQueryTextListener = new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "onQueryTextSubmit: ");
                    Completable.fromAction(() -> adapter.filter(query))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> songRV.getAdapter().notifyDataSetChanged());
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(TAG, "onQueryTextChange: ");
                    Completable.fromAction(() -> adapter.filter(newText))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> songRV.getAdapter().notifyDataSetChanged());
                    return true;
                }
            };
            searchView.setOnQueryTextListener(onQueryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.all_songs));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        Log.d(TAG, "onDestroy: ");
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
    }

    /**
     * Setting up authors selection UI
     */
    private void setupAuthorsSelectionRV() {
        pagerAdapter = new AuthorsSelectionPagerAdapter(getChildFragmentManager(), 3);
        authorsSelectionsVP.setAdapter(pagerAdapter);
        authorsSelectionsVP.setPageMargin(dpToPx(10));
        authorsSelectionsVP.setPadding(16, 0, 16, 0);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}