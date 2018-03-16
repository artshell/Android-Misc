package com.artshell.arch.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.artshell.arch.model.ServerViewModel;
import com.artshell.arch.storage.entity.ProfileEntity;

import java.util.Map;

/**
 * 偏好
 * Created by artshell on 2018/3/16.
 */

public class HostProfile extends AppCompatActivity {
    private Map<String, String> mPairs = new ArrayMap<>(1);

    @Override
    protected void onStart() {
        super.onStart();
        // ...

        // 参数
        mPairs.put("user_id", "artshell");

        ViewModelProviders.of(this)
                .get(ServerViewModel.class)
                .getWithParameter(ProfileEntity.class, "user/getProfile", mPairs)
                .observe(this, notification -> {
                    // 处理结果
                    if (notification.isOnNext()) {
                        ProfileEntity entity = notification.getValue();
                    }

                    if (notification.isOnError()) {
                        Throwable error = notification.getError();
                    }
                });
    }
}
