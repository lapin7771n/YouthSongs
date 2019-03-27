package com.nlapin.youthsongs.ui;

import android.view.View;

import com.nlapin.youthsongs.ui.home.SongRVAdapter;

import androidx.recyclerview.widget.RecyclerView;

public interface CustomItemClickListener {
    void onItemClick(View v, int position);
}
