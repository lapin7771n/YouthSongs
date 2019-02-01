package com.nlapin.youthsongs.ui.home;

import com.nlapin.youthsongs.BaseContract;
import com.nlapin.youthsongs.models.Song;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {
        void showSongs(List<Song> songList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadData();

        Song onItemClick(int position);
    }
}
