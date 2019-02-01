package com.nlapin.youthsongs;

import java.util.Collection;

public interface BaseContract {

    interface BaseView {

        void showProgressBar();

        void hideProgressBar();
    }

    interface BasePresenter<V extends BaseView> {

        void attachView(V v);

        void detachView();

        void showError();

        V getView();

        boolean isViewAttached();
    }
}
