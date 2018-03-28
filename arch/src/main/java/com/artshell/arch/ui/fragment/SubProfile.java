package com.artshell.arch.ui.fragment;

import com.artshell.arch.storage.Status;
import com.artshell.arch.storage.entity.ProfileEntity;
import com.artshell.arch.view_model.CacheCommonModel;
import com.artshell.arch.view_model.ServerCommonModel;

/**
 * @author artshell on 28/03/2018
 */
public class SubProfile extends DataBaseFragment {
    private static final String TAG = "SubProfile";

    private ServerCommonModel serverModel;
    private CacheCommonModel cacheModel;

    @Override
    protected int applyLayoutId() {
        return 0;
    }

    @Override
    protected void setProperty() {

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
                            break;
                        case Status.SUCCESS:
                            break;
                        case Status.ERROR:
                            break;
                        case Status.COMPLETE:
                            break;
                    }
                });

        // 从缓存中获取(不存在时自动从服务器端获取)
        String key = "user/profile/artshell";
        cacheModel.getWithParameter(key, ProfileEntity.class, "user/profile", mPairs)
                .observe(this, result -> {
                    if (result == null) return;
                    switch (result.status) {
                        case Status.LOADING:
                            break;
                        case Status.SUCCESS:
                            break;
                        case Status.ERROR:
                            break;
                        case Status.COMPLETE:
                            break;
                    }
                });
    }
}
