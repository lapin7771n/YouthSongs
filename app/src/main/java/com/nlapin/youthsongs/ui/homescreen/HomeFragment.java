package com.nlapin.youthsongs.ui.homescreen;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.adapters.AuthorsSelectionPagerAdapter;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;
import com.nlapin.youthsongs.ui.songscreen.SongActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    private RecyclerViewSkeletonScreen skeletonScreen;

    public HomeFragment() {
        //Need an empty constructor because of implementing Fragment
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView(container);
        setupAuthorsSelectionRV();

        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);

        model.getSongs().observe(this, songs -> {
            if (songs == null || songs.isEmpty())
                return;
            skeletonScreen.show();

            adapter.setSongList(songs);
            adapter.notifyDataSetChanged();
            skeletonScreen.hide();
            Log.d(TAG, "UI updated! |Song list|");
        });
        return view;
    }

    /**
     * Setting up All songs UI
     *
     * @param container
     */
    private void setUpRecyclerView(ViewGroup container) {
        adapter = new SongRVAdapter(new ArrayList<>(), (v, position) ->
                startActivity(SongActivity.start(getContext(), position)), getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        songRV.setLayoutManager(layoutManager);


        skeletonScreen = Skeleton.bind(songRV)
                .adapter(adapter)
                .load(R.layout.song_item)
                .duration(Integer.MAX_VALUE)
                .show();
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