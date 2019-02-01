package com.nlapin.youthsongs.ui.song;

import android.view.MenuItem;

import com.nlapin.youthsongs.BaseContract;

public interface SongContract extends BaseContract {

    interface View extends BaseView {
        void showFavorite();

        void hideFavorite();

        void setToolbar(String songInfo);

        void setSongText(String songText);

        void setSongTextSize(int songTextSize);

    }

    interface Presenter extends BasePresenter<View> {

        void favoriteClicked(boolean b);

        void loadSong(int songID);

        void setUpFavoriteBtn();

        void setTextSize(int textSize);
    }
}
