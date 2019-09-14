package com.nlapin.youthsongs.domain

open class BasePresenterImpl<V : BaseContract.BaseView> : BaseContract.BasePresenter<V> {

    private var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getView(): V? = view

    override fun isViewAttached(): Boolean = view != null

}