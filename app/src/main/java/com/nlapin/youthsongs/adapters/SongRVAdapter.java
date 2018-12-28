package com.nlapin.youthsongs.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.model.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRVAdapter extends RecyclerView.Adapter<SongRVAdapter.ViewHolder> {

    private List<Song> songList = DataAdapter.cachedSongs;
    private final Context context;

    public SongRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        long songNumber = songList.get(position).getId();
        String songName = songList.get(position).getName();

        viewHolder.songNumberTV.setText(String.valueOf(songNumber));
        viewHolder.songNameTV.setText(songName);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();

            }
        });
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
