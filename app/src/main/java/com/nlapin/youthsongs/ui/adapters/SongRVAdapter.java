package com.nlapin.youthsongs.ui.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.CustomItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRVAdapter
        extends RecyclerView.Adapter<SongRVAdapter.SongViewHolder> {

    private static final String TAG = "SongRVAdapter";

    private List<Song> songList;
    private List<Song> copySongList;
    private CustomItemClickListener clickListener;
    private Random random;

    public SongRVAdapter(Collection<Song> songList,
                         CustomItemClickListener clickListener) {

        this.songList = new ArrayList<>(songList);
        this.copySongList = new ArrayList<>(songList);
        this.clickListener = clickListener;
        random = new Random();
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.song_item,
                        viewGroup,
                        false);

        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder songViewHolder, int position) {
        Song song = songList.get(position);
        long songNumber = song.getId();
        String songName = song.getName();

        songViewHolder.itemView.setOnClickListener(v ->
                clickListener.onItemClick(v, (int) songNumber));

        songViewHolder.songNameTV.setText(songName);
        songViewHolder.songNameTV.setBackground(new ColorDrawable(Color.TRANSPARENT));
        songViewHolder.songCover.setBackground(new ColorDrawable(Color.TRANSPARENT));
        songViewHolder.songGanreTV.setBackground(new ColorDrawable(Color.TRANSPARENT));

        String coverUrlSmall = song.getCoverUrlSmall();

        if (coverUrlSmall != null) {
            Glide.with(songViewHolder.itemView)
                    .load(coverUrlSmall)
                    .apply(RequestOptions.circleCropTransform())
                    .into(songViewHolder.songCover);
        }
    }

    public void filter(String filterText) {
        filterText = filterText.toLowerCase();
        songList.clear();
        if (filterText.isEmpty()) {
            songList.addAll(copySongList);
            return;
        }
        for (Song song : copySongList) {
            if (song.getName() == null)
                continue;

            //Search by name
            if (song.getName().toLowerCase().contains(filterText))
                songList.add(song);

            //Search by number
            if (String.valueOf(song.getId()).contains(filterText))
                songList.add(song);

            //Search by text
            boolean isNumber = false;
            try {
                Integer.parseInt(filterText);
                isNumber = true;
            } catch (NumberFormatException e) {
                // checking if number
            }
            if (!isNumber && !songList.contains(song)
                    && song.getText().toLowerCase().contains(filterText)
                    && song.getChorus().toLowerCase().contains(filterText))
                songList.add(song);
        }
    }

    class SongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.songNameTV)
        TextView songNameTV;
        @BindView(R.id.songCover)
        ImageView songCover;
        @BindView(R.id.songGanreTV)
        TextView songGanreTV;

        SongViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
