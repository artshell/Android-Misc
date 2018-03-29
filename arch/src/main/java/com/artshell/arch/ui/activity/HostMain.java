package com.artshell.arch.ui.activity;

import com.artshell.arch.R;
import com.artshell.arch.view_model.CacheCommonModel;
import com.artshell.arch.view_model.ServerCommonModel;

public class HostMain extends DataBaseActivity {
    private static final String TAG = "HostMain";

    private ServerCommonModel serverModel;
    private CacheCommonModel cacheModel;

    @Override
    protected int applyLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setProperty() {

    }

    @Override
    protected void loadData() {
        serverModel = createViewModel(ServerCommonModel.class);
        cacheModel = createViewModel(CacheCommonModel.class);
    }
}
