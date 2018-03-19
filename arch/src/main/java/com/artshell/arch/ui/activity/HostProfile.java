package com.artshell.arch.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.artshell.arch.storage.Status;
import com.artshell.arch.storage.entity.ProfileEntity;
import com.artshell.arch.view_model.CacheCommonModel;
import com.artshell.arch.view_model.ServerCommonModel;

import java.util.Map;

/**
 * 用户偏好
 * Created by artshell on 2018/3/16.
 */

public class HostProfile extends AppCompatActivity {
    private Map<String, String> mPairs = new ArrayMap<>(1);

    @Override
    protected void onStart() {
        super.onStart();

        // 参数
        mPairs.put("user_id", "artshell");

        // 直接从服务器端获取
        ViewModelProviders.of(this)
                .get(ServerCommonModel.class)
                .fetchByParameter(ProfileEntity.class, "user/profile", mPairs)
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
        ViewModelProviders.of(this)
                .get(CacheCommonModel.class)
                .getWithParameter(key, ProfileEntity.class, "user/profile", mPairs)
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
