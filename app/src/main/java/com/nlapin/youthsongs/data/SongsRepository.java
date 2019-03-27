package com.nlapin.youthsongs.data;

import com.nlapin.youthsongs.models.Song;

import java.util.Collection;

public interface SongsRepository extends BaseSongsRepository<Song> {

    void addSong(Song song);

    void addAll(Collection<Song> songs);
}
