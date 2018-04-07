package com.artshell.arch.ui.user;

import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.Status;
import com.artshell.arch.storage.server.model.UserInfoResponse;
import com.artshell.arch.storage.server.model.UserInfoResponse.UserInfo;
import com.artshell.arch.ui.DataBaseActivity;
import com.artshell.arch.view_model.ServerCommonModel;

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
        ServerCommonModel serverModel = createViewModel(ServerCommonModel.class);

        // 参数
        mPairs.clear();
        mPairs.put("user_id", "artshell");

        // 直接从服务器端获取
        serverModel.fetchByParameter(UserInfoResponse.class, "user/profile", mPairs)
                .observe(this, this::tackle);
    }

    private void tackle(Resource<UserInfoResponse> resource) {
        if (resource == null) return;
        @Status String state = resource.status;
        switch (state) {
            case Status.LOADING: /* 显示加载dialog */
                break;
            case Status.SUCCESS: /* 处理结果数据 */
                UserInfoResponse response = resource.data;
                if (response == null) return;
                UserInfo info = response.getData();
                // ...
                break;
            case Status.ERROR: /* 关闭dialog/显示错误信息 */
                break;
            case Status.COMPLETE: /* 关闭dialog */
                break;
        }
    }
}
