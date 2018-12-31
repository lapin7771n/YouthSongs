package com.nlapin.youthsongs.fragments;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.airbnb.lottie.LottieAnimationView;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.adapters.DataAdapter;
import com.nlapin.youthsongs.model.Song;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment {


    @BindView(R.id.songTextTV) TextView songTextTV;
    @BindView(R.id.songNameToolBarTV) TextView songNameToolBarTV;
    @BindView(R.id.songNumToolBarTV) TextView songNumToolBarTV;
    @BindView(R.id.featuredBtn) ToggleButton featuredBtn;
    @BindView(R.id.featuredBtnAnimation) LottieAnimationView featuredBtnAnimation;
    private Song shownSong;

    public SongFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        ButterKnife.bind(this, view);

        String songText = shownSong.getSongText();
        String name = shownSong.getName().substring(0, 19) + "...";

        songTextTV.setText(Html.fromHtml(songText));
        songNameToolBarTV.setText(name);

        TextViewCompat.setAutoSizeTextTypeWithDefaults(songNameToolBarTV, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        featuredBtn.setChecked(shownSong.isFavourite());
        featuredBtn.setOnCheckedChangeListener(new FeaturedOnClickListener());

        float textSize = songNameToolBarTV.getTextSize();
        songNumToolBarTV.setTextSize(textSize);
        songNumToolBarTV.setText(String.valueOf(shownSong.getId()));

        return view;
    }

    class FeaturedOnClickListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                shownSong.setFavourite(true);
                DataAdapter.favoriteSongs.add(shownSong);
                featuredBtn.setBackgroundDrawable(null);
                featuredBtnAnimation.setSpeed(1f);
                featuredBtnAnimation.playAnimation();
            } else {
                shownSong.setFavourite(false);
                DataAdapter.favoriteSongs.remove(shownSong);
                featuredBtnAnimation.setSpeed(-2.5f);
                featuredBtnAnimation.playAnimation();
                featuredBtn.setBackgroundResource(R.drawable.button_favourite);
            }
        }
    }

    private void setShownSong(Song shownSong) {
        this.shownSong = shownSong;
    }

    public static SongFragment getSongFragment(Song shownSong) {
        SongFragment songFragment = new SongFragment();
        songFragment.setShownSong(shownSong);
        return songFragment;
    }
}
