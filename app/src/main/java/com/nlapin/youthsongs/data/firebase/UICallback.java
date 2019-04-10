package com.nlapin.youthsongs.data.firebase;

import com.nlapin.youthsongs.models.Song;

import java.util.List;

public interface UICallback {

    default void renderUI(List<Song> songs) {

    }
}
