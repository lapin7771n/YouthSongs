package com.nlapin.youthsongs.ui.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.Song;
import com.nlapin.youthsongs.ui.AboutScreenRouter;
import com.nlapin.youthsongs.ui.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRVAdapter
        extends RecyclerView.Adapter<SongRVAdapter.SongViewHolder> {

    private static final String TAG = "SongRVAdapter";

    private List<Song> songList = new ArrayList<>();
    private List<Song> copySongList = new ArrayList<>();
    private CustomItemClickListener clickListener;
    private FragmentActivity activity;

    public SongRVAdapter(CustomItemClickListener clickListener, FragmentActivity activity) {
        this.clickListener = clickListener;
        this.activity = activity;
    }

    public void setSongList(List<Song> songList) {
        this.songList = new ArrayList<>(songList);
        this.copySongList = new ArrayList<>(songList);
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
        songViewHolder.songGenreTV.setBackground(new ColorDrawable(Color.TRANSPARENT));

        String coverUrlSmall = song.getCoverUrlSmall();

        songViewHolder.songMoreBtn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), songViewHolder.songMoreBtn);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.mistakeInTheText:
                        new AboutScreenRouter(activity).openEmail(AboutScreenRouter.DeveloperID.Nikita,
                                String.format(activity.getString(R.string.mistakeMessageBody), songNumber));
                        return true;

                    case R.id.shareSong:
                        // TODO: 4/25/2019 Implement this feature
                        Toast.makeText(activity, "Sorry, this feature is not supported yet",
                                Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            });

            popupMenu.show();
        });

        if (coverUrlSmall != null) {
            Glide.with(songViewHolder.itemView)
                    .load(coverUrlSmall)
                    .apply(RequestOptions.circleCropTransform())
                    .into(songViewHolder.songCover);
        }
    }

    public void filter(String filterText) {
        songList.clear();
        if (filterText.isEmpty()) {
            songList.addAll(copySongList);
            return;
        }

        for (Song song : copySongList) {

            //Search by name
            if (song.getName().toLowerCase().contains(filterText)) {
                songList.add(song);
            }

            //Search by number
            if (String.valueOf(song.getId()).contains(filterText)) {
                songList.add(song);
            }

            //Search by text
            boolean isNumber = false;
            try {
                Integer.parseInt(filterText);
                isNumber = true;
            } catch (NumberFormatException e) {
                // checking if text is number
            }
            if (!isNumber && !songList.contains(song)
                    && song.getChorus() != null
                    && song.getChorus().toLowerCase().contains(filterText)) {
                songList.add(song);
            }
            if (!isNumber && !songList.contains(song)
                    && song.getText().toLowerCase().contains(filterText)) {
                songList.add(song);
            }
        }

    }

    class SongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.songNameTV)
        TextView songNameTV;
        @BindView(R.id.songCover)
        ImageView songCover;
        @BindView(R.id.songGanreTV)
        TextView songGenreTV;
        @BindView(R.id.songMoreBtn)
        ImageView songMoreBtn;

        SongViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
