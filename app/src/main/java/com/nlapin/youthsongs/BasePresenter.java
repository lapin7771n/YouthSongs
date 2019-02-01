package com.nlapin.youthsongs;

/**
 * @author nikita on 26,January,2019
 */
public abstract class BasePresenter<V extends BaseContract.BaseView>
        implements BaseContract.BasePresenter<V> {

    private V v;


    @Override
    public void attachView(V v) {
        this.v = v;
    }

    @Override
    public void detachView() {
        this.v = null;
    }

    @Override
    public V getView() {
        return v;
    }

    @Override
    public boolean isViewAttached() {
        return v != null;
    }
}
