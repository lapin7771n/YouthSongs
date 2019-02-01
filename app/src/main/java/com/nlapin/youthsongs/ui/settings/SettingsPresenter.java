package com.nlapin.youthsongs.ui.settings;

public class SettingsPresenter
        implements SettingsContract.Presenter {

    //private DataManager dataManager;
    private SettingsContract.View view;

    private static final int TEXT_SIZE_SMALL = 15;
    private static final int TEXT_SIZE_MED = 20;
    private static final int TEXT_SIZE_LARGE = 30;

    private static int TEXT_SIZE;

    public SettingsPresenter(SettingsContract.View view) {
        attachView(view);
        view.showProgressBar();
    }

    @Override
    public int defaultTextSize() {
        return TEXT_SIZE_MED;
    }

    @Override
    public int setSmallTextSize() {
        view.setTextSizeForDemoTV(TEXT_SIZE_SMALL);
        return TEXT_SIZE_SMALL;
    }

    @Override
    public int setMediumTextSize() {
        view.setTextSizeForDemoTV(TEXT_SIZE_MED);
        return defaultTextSize();
    }

    @Override
    public int setLargeTextSize() {
        view.setTextSizeForDemoTV(TEXT_SIZE_LARGE);
        return TEXT_SIZE_LARGE;
    }

    @Override
    public void setUpRadioBtn() {
        switch (TEXT_SIZE) {
            case TEXT_SIZE_SMALL:
                view.setRadioBtnChecked(0);
                break;
            case TEXT_SIZE_MED:
                view.setRadioBtnChecked(1);
                break;
            case TEXT_SIZE_LARGE:
                view.setRadioBtnChecked(2);
                break;
        }

        view.hideProgressBar();
    }

    @Override
    public void attachView(SettingsContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void showError() {

    }

    @Override
    public SettingsContract.View getView() {
        return view;
    }

    @Override
    public boolean isViewAttached() {
        return view == null;
    }

    public void setTextSize(int textSize) {
        TEXT_SIZE = textSize;
        view.setTextSizeForDemoTV(textSize);
    }
}
