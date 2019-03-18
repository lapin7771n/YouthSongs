package com.nlapin.youthsongs.ui.songScreen;

import com.nlapin.youthsongs.BaseContract;
import com.nlapin.youthsongs.models.Song;

public interface SongContract extends BaseContract {

    interface View extends BaseView {
        void setSong(Song song);

        void setSongTextSize();

        void setIsFavorite(boolean isFavorite);

    }

    interface Presenter extends BasePresenter<View> {

        void favoriteClicked(boolean b);

        void loadSong(int songID);

        void setUpFavoriteBtn();

        void setTextSize(int textSize);
    }
}
