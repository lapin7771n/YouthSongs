package com.nlapin.youthsongs.fragments;


import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.Objects;

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

    SharedPreferences settings;

    DataAdapter dataAdapter;

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

        dataAdapter = new DataAdapter(getContext());

        settings = Objects.requireNonNull(getContext()).getSharedPreferences(SettingsFragment.APP_PREFERENCES, Context.MODE_PRIVATE);
        float textSizeFromSettings = settings.getFloat(SettingsFragment.PREF_TEXT_SIZE, SettingsFragment.TEXT_SIZE_MED);

        String name = shownSong.getName();
        String songText = shownSong.getSongText();
        if (name.length() > 20) {
            name = shownSong.getName().substring(0, 19) + "...";
        }

        songTextTV.setText(Html.fromHtml(songText));
        songTextTV.setTextSize(textSizeFromSettings);
        songNameToolBarTV.setText(name);

        TextViewCompat.setAutoSizeTextTypeWithDefaults(songNameToolBarTV, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        if (shownSong.isFavorite()) {
            featuredBtn.setChecked(true);
        }
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
                dataAdapter.addToFavorite((int) shownSong.getId());

                featuredBtn.setBackgroundDrawable(null);
                featuredBtnAnimation.setSpeed(1f);
                featuredBtnAnimation.playAnimation();
            } else {
                dataAdapter.removeFromFavorite((int) shownSong.getId());

                featuredBtnAnimation.setSpeed(-2.5f);
                featuredBtnAnimation.playAnimation();
                featuredBtn.setBackgroundResource(R.drawable.button_favourite);
            }
        }
    }

    private void setShownSong(Song shownSong) {
        this.shownSong = shownSong;
    }

    static SongFragment getSongFragment(Song shownSong) {
        SongFragment songFragment = new SongFragment();
        songFragment.setShownSong(shownSong);
        return songFragment;
    }
}
