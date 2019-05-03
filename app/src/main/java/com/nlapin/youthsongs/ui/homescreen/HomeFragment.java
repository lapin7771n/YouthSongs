package com.nlapin.youthsongs.ui.homescreen;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.adapters.AuthorsSelectionPagerAdapter;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private PagerAdapter pagerAdapter;

    /**
     * Adapter for all songs in MainScreen
     */
    private SongRVAdapter adapter;
    private Disposable disposable;

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

        return view;
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