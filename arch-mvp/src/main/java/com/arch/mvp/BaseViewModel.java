package com.arch.mvp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Created by Chatikyan on 20.05.2017.
 */

public final class BaseViewModel<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    private P presenter;

    void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
            presenter.onAppContext(getApplication());
        }
    }

    P getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        presenter.onPresenterDestroy();
        presenter = null;
    }
}
