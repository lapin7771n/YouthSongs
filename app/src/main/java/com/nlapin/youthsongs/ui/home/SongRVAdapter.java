package com.nlapin.youthsongs.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nlapin.youthsongs.CustomItemClickListener;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.models.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRVAdapter
        extends RecyclerView.Adapter<SongRVAdapter.ViewHolder> {

    private List<Song> songList;
    private List<Song> copySongList;
    private CustomItemClickListener clickListener;


    public SongRVAdapter(Collection<Song> songList,
                         CustomItemClickListener clickListener) {

        this.songList = new ArrayList<>(songList);
        this.copySongList = new ArrayList<>(songList);
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.song_item,
                        viewGroup,
                        false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        long songNumber = songList.get(position).getId();
        String songName = songList.get(position).getName();

        viewHolder.itemView.setOnClickListener(v ->
                clickListener.onItemClick(v, (int) songNumber));

        viewHolder.songNumberTV.setText(String.valueOf(songNumber));
        viewHolder.songNameTV.setText(songName);
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
            if (song.getSongText().toLowerCase().contains(filterText) && !songList.contains(song))
                songList.add(song);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.songNameTV) TextView songNameTV;
        @BindView(R.id.songNumberTV) TextView songNumberTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
