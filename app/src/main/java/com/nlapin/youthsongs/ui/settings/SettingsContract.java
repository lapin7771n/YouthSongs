package com.nlapin.youthsongs.ui.settings;

import com.nlapin.youthsongs.ui.BaseContract;

public interface SettingsContract extends BaseContract {

    interface View extends BaseView {

        void setTextSizeForDemoTV(int textSize);

        void setRadioBtnChecked(int radioBtnNum);

    }

    interface Presenter extends BasePresenter<View> {

        int defaultTextSize();

        int setSmallTextSize();

        int setMediumTextSize();

        int setLargeTextSize();

        void setUpRadioBtn();
    }
}
