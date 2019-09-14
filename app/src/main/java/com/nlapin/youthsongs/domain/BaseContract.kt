package com.nlapin.youthsongs.domain

interface BaseContract {
    interface BaseView {
        fun onError()
    }

    interface BasePresenter<V : BaseView> {
        fun attachView(view: V)
        fun detachView()
        fun getView(): V?
        fun isViewAttached(): Boolean
    }
}
