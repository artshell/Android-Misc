package com.artshell.arch.ui.activity;

import com.artshell.arch.storage.Status;
import com.artshell.arch.storage.entity.ProfileEntity;
import com.artshell.arch.view_model.CacheCommonModel;
import com.artshell.arch.view_model.ServerCommonModel;

/**
 * 用户偏好
 * Created by artshell on 2018/3/16.
 */

public class HostProfile extends DataBaseActivity {
    private ServerCommonModel serverModel;
    private CacheCommonModel cacheModel;

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
        serverModel = createViewModel(ServerCommonModel.class);
        cacheModel = createViewModel(CacheCommonModel.class);

        // 参数
        mPairs.clear();
        mPairs.put("user_id", "artshell");

        // 直接从服务器端获取
        serverModel.fetchByParameter(ProfileEntity.class, "user/profile", mPairs)
                .observe(this, result -> {
                    if (result == null) return;
                    switch (result.status) {
                        case Status.LOADING:
                            // 显示加载dialog
                            break;
                        case Status.SUCCESS:
                            // 拿取结果数据
                            ProfileEntity entity = result.data;
                            // ...
                            break;
                        case Status.ERROR:
                            // 关闭dialog
                            break;
                        case Status.COMPLETE:
                            // 关闭dialog
                            break;
                    }
                });

        // 从缓存中获取(不存在时自动从服务器端获取)
        String cacheKey = "user/profile/artshell";
        cacheModel.getWithParameter(cacheKey, ProfileEntity.class, "user/profile", mPairs)
                .observe(this, result -> {
                    if (result == null) return;
                    switch (result.status) {
                        case Status.LOADING:
                            // 显示加载dialog
                            break;
                        case Status.SUCCESS:
                            // 拿取结果数据
                            ProfileEntity entity = result.data;
                            // ...
                            break;
                        case Status.ERROR:
                            // 关闭dialog
                            break;
                        case Status.COMPLETE:
                            // 关闭dialog
                            break;
                    }
                });

    }
}
