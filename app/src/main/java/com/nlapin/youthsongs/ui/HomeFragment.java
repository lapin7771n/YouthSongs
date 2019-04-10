package com.nlapin.youthsongs.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.data.AppDatabase;
import com.nlapin.youthsongs.di.AppDiComponent;
import com.nlapin.youthsongs.di.AppDiModule;
import com.nlapin.youthsongs.di.DaggerAppDiComponent;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

    /**
     * Adapter for all songs in MainScreen
     */
    private SongRVAdapter adapter;

    /**
     * For injecting SongDao
     */
    private AppDatabase appDatabase;

    public HomeFragment() {
        //Need an empty constructor because of implementing Fragment
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        songRV.setLayoutManager(new LinearLayoutManager(getContext()));
        AppDiModule appDiModule = new AppDiModule(getContext());
        AppDiComponent appDiComponent = DaggerAppDiComponent
                .builder()
                .appDiModule(appDiModule)
                .build();

        Log.d(TAG, "AppDIModule: " + appDiModule.toString());

        appDiComponent.getAppDatabase();
        appDiComponent.getSongRepository();

        RecyclerViewSkeletonScreen skeletonScreen = Skeleton.bind(songRV)
                .adapter(adapter)
                .load(R.layout.song_item)
                .duration(1200)
                .show();

        songRV.postDelayed(skeletonScreen::hide, 5000);
    }
}