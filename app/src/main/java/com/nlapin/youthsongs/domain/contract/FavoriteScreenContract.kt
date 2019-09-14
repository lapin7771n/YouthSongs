package com.nlapin.youthsongs.domain.contract

import androidx.lifecycle.LiveData
import com.nlapin.youthsongs.domain.BaseContract
import com.nlapin.youthsongs.models.FavoriteSong

internal interface FavoriteScreenContract {

    interface View : BaseContract.BaseView {
        fun showFavorites(favoriteSongs: LiveData<List<FavoriteSong>>)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadFavorites()
    }
}