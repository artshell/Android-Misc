package com.artshell.arch.ui.user;

import com.artshell.arch.storage.server.model.UserResponse;
import com.artshell.arch.ui.DataBaseActivity;
import com.artshell.arch.ui.SimpleObserver;
import com.artshell.arch.view_model.HttpViewModel;

/**
 * 用户信息
 * Created by artshell on 2018/3/16.
 */

public class HostUserInfo extends DataBaseActivity {

    @Override
    protected int applyLayoutId() {
        return 0;
    }

    @Override
    protected void setProperty() {
        // View相关属性
    }

    @Override
    protected void loadData() {
        HttpViewModel serverModel = createViewModel(HttpViewModel.class);

        // 参数
        mPairs.clear();
        mPairs.put("user_id", "artshell");

        // 直接从服务器端获取
        serverModel.fetchByParameter(UserResponse.class, "user/profile", mPairs)
                .observe(this, new SimpleObserver<UserResponse>() {
                    @Override
                    public void onData(UserResponse data) {

                    }
                });
    }
}
