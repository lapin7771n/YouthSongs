package com.nlapin.youthsongs.domain.presenters

import com.nlapin.youthsongs.data.FavoritesRepository
import com.nlapin.youthsongs.domain.BasePresenterImpl
import com.nlapin.youthsongs.domain.contract.FavoriteScreenContract
import javax.inject.Inject

internal class FavoriteScreenPresenter @Inject constructor() :
        BasePresenterImpl<FavoriteScreenContract.View>(), FavoriteScreenContract.Presenter {

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    override fun loadFavorites() {
        favoritesRepository.all
    }
}