package com.artshell.mvp.ui.main;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;

import com.artshell.mvp.storage.server.model.UserInfoResponse;
import com.artshell.mvp.ui.BaseLifecycleActivity;

import java.util.Map;

import nucleus5.factory.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseLifecycleActivity<MainPresenter> {

    private Map<String, String> parameter = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        parameter.put("id", "artshell");
        getPresenter().requestUser(parameter);
    }

    public void onUserInfo(UserInfoResponse.UserInfo info) {

    }

    public void onError(Throwable thr) {

    }
}
