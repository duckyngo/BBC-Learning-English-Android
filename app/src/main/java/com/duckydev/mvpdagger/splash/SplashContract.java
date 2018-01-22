package com.duckydev.mvpdagger.splash;

import com.duckydev.mvpdagger.BasePresenter;
import com.duckydev.mvpdagger.BaseView;

/**
 * Created by duckyng on 12/26/2017.
 */

public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void hideLoading();

        void presentHome();

        void setLanguage();

        void showLoading();

    }

    interface Presenter extends BasePresenter<View>{


    }

}
