package com.nlapin.youthsongs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nlapin.youthsongs.MainActivity;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.adapters.DataAdapter;
import com.nlapin.youthsongs.adapters.SongRVAdapter;
import com.nlapin.youthsongs.model.Song;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturesFragment extends Fragment {

    @BindView(R.id.emptyBoxAnim) LottieAnimationView emptyBoxAnim;
    @BindView(R.id.emptyLabel) TextView emptyLabel;
    @BindView(R.id.favoriteSongsRV) RecyclerView favoriteSongsRV;
    MainActivity parentActivity;


    public FeaturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        ButterKnife.bind(this, view);


        List<Song> favoriteSongs = DataAdapter.favoriteSongs;

        if (favoriteSongs.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            SongRVAdapter songRVAdapter = new SongRVAdapter(favoriteSongs, (v, position) -> {
                TextView viewById = v.findViewById(R.id.songNumberTV);

                position = Integer.parseInt((String) viewById.getText());

                Song clickedSong = DataAdapter.getByIdInCache(position);

                SongFragment songFragment = SongFragment.getSongFragment(clickedSong);

                parentActivity.setFragment(songFragment);
            });

            favoriteSongsRV.setAdapter(songRVAdapter);
            favoriteSongsRV.setLayoutManager(layoutManager);

        } else {
            emptyBoxAnim.setVisibility(View.VISIBLE);
            emptyLabel.setVisibility(View.VISIBLE);
            emptyBoxAnim.playAnimation();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        emptyBoxAnim.playAnimation();
    }


    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
