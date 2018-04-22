package com.artshell.mvp.ui.main;

import com.artshell.mvp.storage.server.HttpManager;
import com.artshell.mvp.storage.server.model.UserInfoResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus5.presenter.Presenter;

/**
 * @author artshell on 2018/4/22
 */
public class MainPresenter extends Presenter<MainActivity> {

    public void requestUser(Map<String, String> pairs) {
        HttpManager.get(UserInfoResponse.class, "user/profile", pairs)
                .subscribeOn(Schedulers.io())
                .onTerminateDetach()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getView().takeUntilEvent(ActivityEvent.PAUSE))
                .subscribe(response -> getView().onUserInfo(response.getData()), thr -> getView().onError(thr));
    }
}
