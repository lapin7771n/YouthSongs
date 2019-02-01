package com.nlapin.youthsongs.ui.favsonglist;

import com.nlapin.youthsongs.BaseContract;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

public interface FavoritesContract extends BaseContract {

    interface View extends BaseView {

        void showFavoriteSongs(List<Song> favoriteSongs);

        void showEmpty();
    }

    interface Presenter extends BasePresenter<View> {

        void loadData();

        void onItemClick();
    }
}
