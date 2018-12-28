package com.nlapin.youthsongs.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.adapters.SongRVAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.songRV) RecyclerView songRV;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        SongRVAdapter rvAdapter = new SongRVAdapter(getContext());

        songRV.setAdapter(rvAdapter);
        songRV.setLayoutManager(layoutManager);
        //TODO TExt of Song in next Fragment

        return view;
    }

}
