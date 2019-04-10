package com.nlapin.youthsongs.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nlapin.youthsongs.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment
        extends Fragment {

    @BindView(R.id.emptyBoxAnim)
    LottieAnimationView emptyBoxAnim;
    @BindView(R.id.emptyLabel)
    TextView emptyLabel;
    @BindView(R.id.favoriteSongsRV)
    RecyclerView favoriteSongsRV;
    @BindView(R.id.progressBar)
    ProgressBar loadingPB;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
