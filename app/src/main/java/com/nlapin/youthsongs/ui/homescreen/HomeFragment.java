package com.nlapin.youthsongs.ui.homescreen;


import android.os.Bundle;
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

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.AuthorsSelectionUI;
import com.nlapin.youthsongs.ui.adapters.AuthorsSelectionsRVAdapter;
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
    @BindView(R.id.authorsSelectionsRV)
    RecyclerView authorsSelectionsRV;

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
        setupAuthorsSelectionRV();

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

    /**
     * Setting up All songs UI
     */
    private void setUpRecyclerView() {
        adapter = new SongRVAdapter(new ArrayList<>(), (v, position) ->
                startActivity(SongActivity.start(getContext(), position)));

        songRV.setLayoutManager(new LinearLayoutManager(getContext()));

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
        final ArrayList<AuthorsSelectionUI> authorsSelections = new ArrayList<>();
        authorsSelections.add(new AuthorsSelectionUI("Test", new ArrayList<>()));
        authorsSelections.add(new AuthorsSelectionUI("Medium Text Test", new ArrayList<>()));
        authorsSelections.add(new AuthorsSelectionUI("Very very long name test", new ArrayList<>()));
        AuthorsSelectionsRVAdapter authorsSelectionsRVAdapter = new AuthorsSelectionsRVAdapter(
                authorsSelections,
                (v, position) -> {

                },
                getContext());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        authorsSelectionsRV.setLayoutManager(layoutManager);
        authorsSelectionsRV.setAdapter(authorsSelectionsRVAdapter);
        authorsSelectionsRV.setHasFixedSize(false);
    }

}