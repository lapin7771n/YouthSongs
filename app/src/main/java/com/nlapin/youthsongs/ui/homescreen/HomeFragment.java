package com.nlapin.youthsongs.ui.homescreen;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    @BindView(R.id.authorsChoisesFL)
    FrameLayout authorsChoisesFL;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

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
        setUpRecyclerView();

        getFragmentManager().beginTransaction()
                .replace(authorsChoisesFL.getId(), new AuthorsSelectionsFragment())
                .commit();

        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);

        model.getSongs().observe(this, songs -> {
            if (songs == null || songs.isEmpty())
                return;

            adapter.setSongList(songs);
            adapter.notifyDataSetChanged();
            skeletonScreen.hide();
            Log.d(TAG, "UI updated! |Song list|");
        });

        return view;
    }

    private void setUpRecyclerView() {
        adapter = new SongRVAdapter(new ArrayList<>(), (v, position) -> {
            throw new UnsupportedOperationException();
        });

        songRV.setLayoutManager(new LinearLayoutManager(getContext()));

        skeletonScreen = Skeleton.bind(songRV)
                .adapter(adapter)
                .load(R.layout.song_item)
                .duration(Integer.MAX_VALUE)
                .show();

        songRV.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

        });
    }

}