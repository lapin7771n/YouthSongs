package com.nlapin.youthsongs.ui.homescreen;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.data.AppDatabase;
import com.nlapin.youthsongs.data.firebase.FirestoreHelper;
import com.nlapin.youthsongs.data.firebase.UICallback;
import com.nlapin.youthsongs.di.ApplicationModule;
import com.nlapin.youthsongs.di.DaggerMainComponent;
import com.nlapin.youthsongs.di.MainComponent;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.adapters.SongRVAdapter;

import java.util.ArrayList;
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

        MainComponent component = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(getActivity().getApplication()))
                .build();

        List<Song> localSongs = component.getAppDatabase().songDao().getAll().getValue();
        Log.d(TAG, "local songs - " + (localSongs != null));
        if (localSongs != null) {
            Log.d(TAG, "local songs - " + (localSongs.isEmpty()));
        }
        if (localSongs != null && !localSongs.isEmpty()) {
            Log.d(TAG, "Songs from local storage" + localSongs);
            adapter.setSongList(localSongs);
            adapter.notifyDataSetChanged();
            skeletonScreen.hide();
        } else {
            FirestoreHelper firestoreHelper = component.getFirestoreHelper();
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("Stand by.")
                    .setMessage("We are migrating localSongsLD song from our servers. Please wait...")
                    .create();

            alertDialog.show();

            firestoreHelper.migrateAllSongsFromFirestore(
                    component.getAppDatabase().songDao(),
                    new UICallback() {
                        @Override
                        public void renderUI(List<Song> songs) {
                            adapter.setSongList(songs);
                            adapter.notifyDataSetChanged();
                            alertDialog.hide();
                            skeletonScreen.hide();
                        }
                    });
        }

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
    }
}