package com.arch.mvp;

import android.util.Log;

/**
 * Created by Chatikyan on 07.07.2017.
 */

public interface BaseLoadingContract {

    interface View extends BaseContract.View {

        void showLoading();

        void hideLoading();

        default void showTips(String message) {
            if (BuildConfig.DEBUG) {
                Log.i("BaseLoadingContract", ".View#showTips(): " + message);
            }
        }

        default void handleError(Throwable throwable) {
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace();
            }
        }
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
