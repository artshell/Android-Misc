package com.artshell.mvp.presenter;

import android.annotation.SuppressLint;

import com.artshell.mvp.storage.server.HttpManager;
import com.artshell.mvp.storage.server.model.UserInfoResponse;
import com.artshell.mvp.ui.HostMain;
import com.artshell.mvp.utils.RxSchedulers;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Map;

import nucleus5.presenter.Presenter;

/**
 * @author artshell on 2018/4/22
 */
public class MainPresenter extends Presenter<HostMain> {

    /**
     * @param pairs 参数
     */
    @SuppressLint("CheckResult")
    public void requestUser(Map<String, String> pairs) {
        HttpManager.get(UserInfoResponse.class, "user/profile", pairs)
                .compose(RxSchedulers.ioToMain())
                .onTerminateDetach()
                .compose(getView().takeUntilEvent(ActivityEvent.PAUSE))
                .doOnSubscribe(sub -> getView().onShow())
                .doFinally(getView()::onDismiss)
                .subscribe(response -> getView().onUserInfo(response.getData()), thr -> getView().onError(thr));
    }
}
