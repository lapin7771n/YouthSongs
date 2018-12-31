package com.nlapin.youthsongs.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nlapin.youthsongs.CustomItemClickListener;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.model.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRVAdapter extends RecyclerView.Adapter<SongRVAdapter.ViewHolder> {

    private List<Song> songList;
    CustomItemClickListener clickListener;


    public SongRVAdapter(List<Song> songList, CustomItemClickListener clickListener) {
        this.songList = songList;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        try {
            itemView.setOnClickListener(v -> clickListener.onItemClick(v, viewHolder.getLayoutPosition()));
        }catch (NullPointerException e){
            Toast.makeText(viewGroup.getContext(), "This song unavailable", Toast.LENGTH_SHORT).show();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        long songNumber = songList.get(position).getId();
        String songName = songList.get(position).getName();

        viewHolder.songNumberTV.setText(String.valueOf(songNumber));
        viewHolder.songNameTV.setText(songName);
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
