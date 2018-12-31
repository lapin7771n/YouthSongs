package com.nlapin.youthsongs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nlapin.youthsongs.MainActivity;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.adapters.DataAdapter;
import com.nlapin.youthsongs.adapters.SongRVAdapter;
import com.nlapin.youthsongs.model.Song;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.songRV) RecyclerView songRV;
    MainActivity parentActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        SongRVAdapter rvAdapter = new SongRVAdapter(DataAdapter.cachedSongs, (v, position) -> {

            Song clickedSong = DataAdapter.cachedSongs.get(position);
            if (clickedSong.getName() != null) {

                SongFragment songFragment = SongFragment.getSongFragment(clickedSong);

                parentActivity.setFragment(songFragment);
            } else {
                Toast.makeText(parentActivity, "This song hasn't been added yet.", Toast.LENGTH_SHORT).show();
            }
        });

        songRV.setAdapter(rvAdapter);
        songRV.setLayoutManager(layoutManager);

        return view;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
