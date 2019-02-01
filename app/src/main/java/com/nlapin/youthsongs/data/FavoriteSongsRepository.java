package com.nlapin.youthsongs.data;

import com.nlapin.youthsongs.models.Song;

public interface FavoriteSongsRepository extends BaseSongsRepository<Song> {
    void addToFavorite(int songID);

    void deleteFromFavorite(int songID);
}
