package com.artshell.mvp.ui;

import android.os.Bundle;

import com.artshell.mvp.presenter.MainPresenter;
import com.artshell.mvp.storage.server.model.UserInfoResponse.UserInfo;

import nucleus5.factory.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class HostMain extends BaseDataActivity<MainPresenter> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 请求id为artshell的个人信息
        pairs.put("id", "artshell");
        getPresenter().requestUser(pairs);
    }

    public void onUserInfo(UserInfo info) {

    }
}
